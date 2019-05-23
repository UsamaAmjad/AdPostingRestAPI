package com.heycar.AdListingProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.heycar.AdListingProject.model.AdListing;

@Repository
public interface AdListingRepo extends PagingAndSortingRepository<AdListing, Integer>, JpaSpecificationExecutor<AdListing>  {
	List<AdListing> findAllByOrderByPostedAtDesc();

	Optional<AdListing> findByCodeAndDealer_Id(String code, int dealerId);
}
