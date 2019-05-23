package com.heycar.AdListingProject.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.heycar.AdListingProject.model.AdListing;
import com.heycar.AdListingProject.search.AdListingSpecification;
import com.heycar.AdListingProject.service.AdListingService;

@RestController
public class AdLisitngController {

	private static Logger LOG = LoggerFactory.getLogger(AdLisitngController.class);

	@Autowired
	private AdListingService adListingService;

	/**
	 * Return Single AdListing by ID
	 * 
	 * @param listing_id
	 * @return
	 */
	@GetMapping("/vehicle_listings/{listing_id}")
	public AdListing getAdLisitng(@PathVariable int listing_id) {
		return adListingService.findById(listing_id);
	}

	/**
	 * Save AdListing in DB
	 * 
	 * @param listing
	 * @return
	 */
	@PostMapping("/vehicle_listings")
	public ResponseEntity<AdListing> postJsonListing(@Valid @RequestBody AdListing listing) {
		System.out.println(listing.toString());
		AdListing savedLisitng = adListingService.save(listing);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{lisitng_id}")
				.buildAndExpand(savedLisitng.getId()).toUri();

		return ResponseEntity.created(location).body(savedLisitng);
	}

	/**
	 * Update existing records, throws not found in case of invalid ID
	 * 
	 * @param adListing
	 * @param listing_id
	 * @return
	 */
	@PutMapping("/vehicle_listings/{listing_id}")
	public AdListing updateMessage(@Valid @RequestBody AdListing adListing, @PathVariable int listing_id) {
		return adListingService.update(adListing, listing_id);
	}

	/**
	 * Upload CSV file for batch data
	 * 
	 * @param dealer_id
	 * @param csvFile
	 * @return
	 */
	@PostMapping("/upload_csv/{dealer_id}")
	public List<AdListing> postCsvListing(@PathVariable int dealer_id, @RequestParam("file") MultipartFile csvFile) {
		System.out.println("Uploading CSV: " + dealer_id);

		if (csvFile.isEmpty()) {
			return null;
		}
		InputStream is;
		try {
			is = csvFile.getInputStream();
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				LOG.trace(line); // Here we will persist the data into DB
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Search accepts optional params otherwise return all records
	 * 
	 * @param make
	 * @param model
	 * @param year
	 * @param color
	 * @return
	 */
	@GetMapping("/search")
	public List<AdListing> getLisitngs(@RequestParam(value = "make", required = false) String make,
			@RequestParam(value = "model", required = false) String model,
			@RequestParam(value = "year", required = false) String year,
			@RequestParam(value = "color", required = false) String color) {

		if (make == null && model == null && color == null && year == null)
			return adListingService.getAll(null);

		AdListing searchAd = new AdListing();
		searchAd.setMake(make == null ? "" : make.trim());
		searchAd.setModel(model == null ? "" : model.trim());
		searchAd.setColor(color == null ? "" : color.trim());
		searchAd.setYear(year == null ? 0 : Integer.parseInt(year));

		Specification<AdListing> spec = new AdListingSpecification(searchAd);

		return adListingService.getAll(spec);
	}

}
