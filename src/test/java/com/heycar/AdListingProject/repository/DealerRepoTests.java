package com.heycar.AdListingProject.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.heycar.AdListingProject.model.Dealer;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DealerRepoTests {
	@Autowired
	private DealerRepo repository;

	@Test
	public void shouldFindDealerByUsername() {

		Optional<Dealer> result = repository.findByUsername("alex");

		assertTrue(result.isPresent());
		assertEquals(result.get().getUsername(), "alex");
	}

	@Test
	public void shouldReturnAllDealers() {

		List<Dealer> result = (List<Dealer>) repository.findAll();

		assertNotNull(result);
		assertEquals(2, result.size());
	}
}
