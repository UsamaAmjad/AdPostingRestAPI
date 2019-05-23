package com.heycar.AdListingProject.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.heycar.AdListingProject.model.AdListing;

public interface AdListingService {

	AdListing findById(int listingId);
	
	AdListing save(AdListing adListing);

	AdListing update(AdListing adLisitng, int adListingId);

	List<AdListing> getAll();

	String readFile(MultipartFile csvFile);

}
