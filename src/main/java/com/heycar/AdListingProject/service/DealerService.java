package com.heycar.AdListingProject.service;

import com.heycar.AdListingProject.model.Dealer;

public interface DealerService {
	
	Dealer findById(int dealerId);

	Dealer findByUsername(String username);
}
