package com.heycar.AdListingProject.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heycar.AdListingProject.model.Dealer;
import com.heycar.AdListingProject.repository.DealerRepo;

@Service
public class DealerServiceImpl implements DealerService {

	private static Logger LOG = LoggerFactory.getLogger(DealerServiceImpl.class);

	@Autowired
	private DealerRepo dealerRepo;

	@Override
	public Dealer findById(int dealerId) {
		Optional<Dealer> dealer = dealerRepo.findById(dealerId);
		if (!dealer.isPresent()) {
			LOG.debug("Dealer not found: {}", dealerId);
			throw new EntityNotFoundException("Dealer not found: " + dealerId);
		}
		return dealer.get();
	}

	@Override
	public Dealer findByUsername(String username) {
		Optional<Dealer> dealer = dealerRepo.findByUsername(username);
		if (!dealer.isPresent()) {
			LOG.debug("Dealer not found: {}", username);
			throw new EntityNotFoundException("Dealer not found: " + username);
		}

		return dealer.get();
	}

}
