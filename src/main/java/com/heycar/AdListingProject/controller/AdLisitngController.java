package com.heycar.AdListingProject.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.heycar.AdListingProject.service.AdListingService;

@RestController
public class AdLisitngController {

	@Autowired
	private AdListingService adListingService;

	@GetMapping("/vehicle_listings/{listing_id}")
	public AdListing getAdLisitng(@PathVariable int listing_id) {
		return adListingService.findById(listing_id);
	}

	@PostMapping("/vehicle_listings")
	public ResponseEntity<AdListing> postJsonListing(@Valid @RequestBody AdListing listing) {
		System.out.println(listing.toString());
		AdListing savedLisitng = adListingService.save(listing);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{lisitng_id}")
				.buildAndExpand(savedLisitng.getId()).toUri();

		return ResponseEntity.created(location).body(savedLisitng);
	}

	@PutMapping("/vehicle_listings/{dealer_id}")
	public AdListing updateMessage(@Valid @RequestBody AdListing adListing, @PathVariable int dealer_id,
			Principal principal) {
		return adListingService.update(adListing, dealer_id);
	}

	@PostMapping("/upload_csv/{dealer_id}")
	public List<AdListing> postCsvListing(@PathVariable int dealer_id, @RequestParam("file") MultipartFile csvFile) {
		System.out.println("Uploading CSV: " + dealer_id);
		adListingService.readFile(csvFile);
		if (csvFile.isEmpty()) {
			System.out.println("Empty file");
			return null;
		}
		InputStream is;
		try {
			is = csvFile.getInputStream();
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@GetMapping("/search")
	public List<AdListing> getLisitngs(@RequestParam(value = "make", required = false) String make,
			@RequestParam(value = "model", required = false) String model,
			@RequestParam(value = "year", required = false) String year,
			@RequestParam(value = "color", required = false) String color) {
		return adListingService.getAll();
	}
}
