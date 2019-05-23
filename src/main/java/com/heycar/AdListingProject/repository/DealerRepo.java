package com.heycar.AdListingProject.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.heycar.AdListingProject.model.Dealer;

@Repository
public interface DealerRepo extends PagingAndSortingRepository<Dealer, Integer> {
	
	Optional<Dealer> findByUsername(String username);
}
