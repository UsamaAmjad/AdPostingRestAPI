package com.heycar.AdListingProject.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.heycar.AdListingProject.model.AdListing;
import com.heycar.AdListingProject.model.Dealer;
import com.heycar.AdListingProject.repository.AdListingRepo;
import com.heycar.AdListingProject.repository.DealerRepo;
import com.heycar.AdListingProject.search.AdListingSpecification;

@RunWith(SpringRunner.class)
public class AdListingServiceTests {

	@Autowired
	private AdListingService adListingService;

	@MockBean
	private DealerService dealerService;

	@MockBean
	private AdListingRepo adListingRepo;

	@MockBean
	private DealerRepo dealerRepo;

	AdListing adListing, adListing2;
	Dealer alex, mike;

	@TestConfiguration
	static class DealerServiceImplTestContextConfiguration {

		@Bean
		public AdListingService adListingService() {
			return new AdListingServiceImpl();
		}
	}

	@Before
	public void setUp() {
		alex = new Dealer();
		alex.setId(10000);
		alex.setUsername("alex");

		mike = new Dealer();
		mike.setId(10001);
		mike.setUsername("mike");

		adListing = new AdListing();
		adListing.setId(10000);
		adListing.setCode("ab12");
		adListing.setMake("Honda");
		adListing.setModel("Accord");
		adListing.setYear(2015);
		adListing.setColor("Red");
		adListing.setDealer(alex);

		adListing2 = new AdListing();
		adListing2.setId(10001);
		adListing2.setCode("ab12");
		adListing2.setMake("Honda");
		adListing2.setModel("Accord");
		adListing2.setYear(2015);
		adListing2.setColor("Red");
		adListing2.setDealer(mike);

		List<AdListing> adListings = new ArrayList<>();
		adListings.add(adListing);
		adListings.add(adListing2);

		AdListing filter = new AdListing();
		filter.setMake("Honda");
		filter.setModel("Accord");
		Specification<AdListing> spec = new AdListingSpecification(filter);

		Mockito.when(dealerRepo.findByUsername(alex.getUsername())).thenReturn(Optional.of(alex));
		Mockito.when(dealerRepo.findById(alex.getId())).thenReturn(Optional.of(alex));

		Mockito.when(dealerService.findById(1000)).thenReturn(alex);

		Mockito.when(adListingRepo.findById(10000)).thenReturn(Optional.of(adListing));
		Mockito.when(adListingRepo.findByCodeAndDealer_Id("ab12", 10000)).thenReturn(Optional.of(adListing));
		Mockito.when(adListingRepo.save(adListing2)).thenReturn(adListing2);
		Mockito.when(adListingRepo.findAll()).thenReturn(adListings);
		Mockito.when(adListingRepo.findAll(spec)).thenReturn(adListings);
		Mockito.when(adListingRepo.findAllByOrderByPostedAtDesc()).thenReturn(adListings);

	}

	@Test
	public void whenValidId_thenAdListingShouldBeFound() {
		int id = 10000;
		AdListing found = adListingService.findById(id);
		assertNotNull(found);

	}

	@Test(expected = EntityNotFoundException.class)
	public void whenInvalidId_thenEntityNotFoundShouldBeThrown() {
		int id = 100;
		adListingService.findById(id);
	}

	@Test
	public void whenNewAdListingAdded_thenAdListingShouldBeSaved() {
		AdListing found = adListingService.save(adListing2);
		assertNotNull(found);
		assertEquals(found.getCode(), "ab12");
	}

	@Test
	public void whenGetAll_thenListShouldBeReturned() {
		List<AdListing> found = adListingService.getAll(null);
		assertNotNull(found);
		assertEquals(found.size(), 2);
	}

	@Test
	public void whenGetAllWithSearch_thenListShouldBeReturned() {
		AdListing adListing = new AdListing();
		adListing.setMake("Honda");
		adListing.setModel("Accord");

		Specification<AdListing> spec = new AdListingSpecification(adListing);

		List<AdListing> found = adListingService.getAll(spec);
		assertNotNull(found);
	}

}
