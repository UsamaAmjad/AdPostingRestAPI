package com.heycar.AdListingProject.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.heycar.AdListingProject.model.AdListing;
import com.heycar.AdListingProject.model.Dealer;
import com.heycar.AdListingProject.repository.AdListingRepo;

@Service
public class AdListingServiceImpl implements AdListingService {

	private static Logger LOG = LoggerFactory.getLogger(AdListingServiceImpl.class);

	@Autowired
	private DealerService dealerService;

	@Autowired
	private AdListingRepo adListingRepo;

	@Override
	public AdListing findById(int listingId) {
		Optional<AdListing> ad = adListingRepo.findById(listingId);
		if (!ad.isPresent()) {
			LOG.debug("AdListing not found: {}", listingId);
			throw new EntityNotFoundException("AdListing not found: " + listingId);
		}

		return ad.get();
	}

	/**
	 * Create new AdListing. In case if there is existing AdLisitng with the same
	 * Code from the same Dealer then the old AdListing is updated
	 */
	@Override
	public AdListing save(AdListing adListing) {
		if (adListing.getDealer().getId() == null) {
			LOG.debug("Dealer ID NULL");
			throw new EntityNotFoundException("Dealer not found, ID null");
		}
		/**
		 * Checking if there any existing Ad with the same code from same Dealer
		 */
		Optional<AdListing> sameCodeAd = adListingRepo.findByCodeAndDealer_Id(adListing.getCode(),
				adListing.getDealer().getId());

		if (sameCodeAd.isPresent()) {
			adListing.setId(sameCodeAd.get().getId());
			adListing.setPostedAt(sameCodeAd.get().getPostedAt());
			adListing.setDealer(sameCodeAd.get().getDealer());
		} else {
			adListing.setPostedAt(Instant.now());
			Dealer dealer = dealerService.findById(adListing.getDealer().getId());
			adListing.setDealer(dealer);
		}

		return adListingRepo.save(adListing);
	}

	/**
	 * I throw the `Not Found` exception but it depends on business requirement that
	 * you want to throw exception or insert in DB
	 */
	@Override
	public AdListing update(AdListing adListing, int adListingId) {
		Optional<AdListing> oldAdListing = adListingRepo.findById(adListingId);

		if (!oldAdListing.isPresent()) {
			LOG.debug("AdListing not found: {}", adListingId);
			throw new EntityNotFoundException("AdListing not found: " + adListingId);
		}
		adListing.setId(adListingId);
		adListing.setPostedAt(oldAdListing.get().getPostedAt());
		adListing.setDealer(oldAdListing.get().getDealer());

		return adListingRepo.save(adListing);
	}

	@Override
	public List<AdListing> getAll() {
		return adListingRepo.findAllByOrderByPostedAtDesc();
	}

	@Override
	public String readFile(MultipartFile csvFile) {
		return "";
	}

}
