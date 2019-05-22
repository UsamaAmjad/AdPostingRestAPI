package com.heycar.AdListingProject.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.heycar.AdListingProject.model.Lisitng;

@RestController
public class LisitngController {

	
	@PostMapping("/vehicle_listings")
	public List<Lisitng> postJsonListing(@Valid @RequestBody Lisitng listing) {
		System.out.println(listing.toString());
		return null;
	}

	@PostMapping("/upload_csv/{dealer_id}")
	public List<Lisitng> postCsvListing(@PathVariable int dealer_id, @RequestParam("file") MultipartFile csvFile) {
		System.out.println("Uploading CSV: "+dealer_id);
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
	public List<Lisitng> getLisitngs(@RequestParam(value = "make", required = false) String make,
			@RequestParam(value = "model", required = false) String model,
			@RequestParam(value = "year", required = false) String year,
			@RequestParam(value = "color", required = false) String color) {
		return null;
	}
}
