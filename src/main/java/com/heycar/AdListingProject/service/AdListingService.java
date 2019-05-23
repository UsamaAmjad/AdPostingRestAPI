package com.heycar.AdListingProject.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.heycar.AdListingProject.model.AdListing;

public interface AdListingService {

	AdListing findById(int listingId);
	
	AdListing save(AdListing adListing);

	AdListing update(AdListing adLisitng, int adListingId);

	List<AdListing> getAll(Specification<AdListing> spec);

}
