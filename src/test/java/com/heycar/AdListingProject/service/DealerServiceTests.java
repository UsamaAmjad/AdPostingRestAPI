package com.heycar.AdListingProject.service;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.heycar.AdListingProject.model.Dealer;
import com.heycar.AdListingProject.repository.DealerRepo;

@RunWith(SpringRunner.class)
public class DealerServiceTests {

	@Autowired
	private DealerService dealerService;

	@MockBean
	private DealerRepo dealerRepo;

	@TestConfiguration
	static class DealerServiceImplTestContextConfiguration {

		@Bean
		public DealerService dealerService() {
			return new DealerServiceImpl();
		}
	}

	@Before
	public void setUp() {
		Dealer alex = new Dealer();
		alex.setId(10000);
		alex.setUsername("alex");

		Mockito.when(dealerRepo.findByUsername(alex.getUsername())).thenReturn(Optional.of(alex));
		Mockito.when(dealerRepo.findById(alex.getId())).thenReturn(Optional.of(alex));
	}

	@Test
	public void whenValidUsername_thenDealerShouldBeFound() {
		String username = "alex";
		Dealer found = dealerService.findByUsername(username);

		assertEquals(found.getUsername(), username);
	}
	
	@Test
	public void whenValidId_thenDealerShouldBeFound() {
		int id = 10000;
		Dealer found = dealerService.findById(id);

		assertNotNull(found);
	}

}
