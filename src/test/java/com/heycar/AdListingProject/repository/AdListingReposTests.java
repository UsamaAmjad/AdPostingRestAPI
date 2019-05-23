package com.heycar.AdListingProject.repository;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.heycar.AdListingProject.model.AdListing;
import com.heycar.AdListingProject.search.AdListingSpecification;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AdListingReposTests {

	@Autowired
	private AdListingRepo repository;

	@Test
	public void shouldReturnResultOnSearch() {

		AdListing filter = new AdListing();
		filter.setMake("Honda");
		filter.setModel("Accord");
		filter.setColor("Red");
		filter.setYear(2015);

		Specification<AdListing> spec = new AdListingSpecification(filter);

		List<AdListing> result = repository.findAll(spec);

		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.size());
	}

	@Test
	public void shouldReturnAllResults() {

		List<AdListing> result = repository.findAllByOrderByPostedAtDesc();

		Assert.assertNotNull(result);
		Assert.assertEquals(4, result.size());
	}
	
	@Test
	public void shouldFindByCodeAndDealerId() {

		Optional<AdListing> result = repository.findByCodeAndDealer_Id("ab12", 10000);

		Assert.assertTrue(result.isPresent());
	}

}