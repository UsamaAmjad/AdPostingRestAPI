package com.heycar.AdListingProject.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.heycar.AdListingProject.AdListingProjectApplication;
import com.heycar.AdListingProject.model.AdListing;
import com.heycar.AdListingProject.model.Dealer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdListingProjectApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdListingControllerIntegrationTests {

	@LocalServerPort
	private int port;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mvc;
	private Dealer dealer;
	private AdListing adListing;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();

		dealer = new Dealer();
		dealer.setId(10000);
		dealer.setUsername("alex");
		
		adListing = new AdListing();
		adListing.setId(10000);
		adListing.setCode("ab12");
		adListing.setMake("Honda");
		adListing.setModel("Accord");
		adListing.setYear(2015);
		adListing.setColor("Red");
		adListing.setDealer(dealer);
	}
	
	@Test
	public void searchListing_shouldSucceedWith200() throws Exception {
		MvcResult mvcResult = mvc.perform(get(createURLWithPort("/search/")).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
		String content = mvcResult.getResponse().getContentAsString();
		CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class, AdListing.class);
		List<AdListing> messageList = objectMapper.readValue(content, typeReference);
		assertTrue(messageList.size() > 0);
	}
	
	@Test
	public void getSearchListingsWithParam_shouldSucceedWith200() throws Exception {
		MvcResult mvcResult = mvc.perform(get(createURLWithPort("/search?color=Red")).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
		String content = mvcResult.getResponse().getContentAsString();
		CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class, AdListing.class);
		List<AdListing> messageList = objectMapper.readValue(content, typeReference);
		assertTrue(messageList.size() > 0);
	}

	@Test
	public void getSingleAdListing_shouldSucceedWith404() throws Exception {
		mvc.perform(get(createURLWithPort("/vehicle_listings/1")).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void getSingleAdListing_shouldSucceedWith200() throws Exception {
		MvcResult mvcResult = mvc.perform(get(createURLWithPort("/vehicle_listings/10000")).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		String content = mvcResult.getResponse().getContentAsString();
		AdListing adListing = objectMapper.readValue(content, AdListing.class);
		assertTrue(adListing != null);
	}

	@Test
	public void postEmptyAdListing_shouldSucceedWith400() throws Exception {
		mvc.perform(post(createURLWithPort("/vehicle_listings/")).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void putEmptyAdListing_shouldSucceedWith400() throws Exception {
		mvc.perform(put(createURLWithPort("/vehicle_listings/10000/")).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void postAdListing_shouldSucceedWith201() throws Exception {
		adListing.setPostedAt(Instant.now());
		
		MvcResult mvcResult = mvc.perform(post(createURLWithPort("/vehicle_listings/")).content(objectMapper.writeValueAsString(adListing))
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		assertEquals(201, mvcResult.getResponse().getStatus());
		String content = mvcResult.getResponse().getContentAsString();
		AdListing newAdListing = objectMapper.readValue(content, AdListing.class);
		assertTrue(newAdListing != null);
		assertTrue(newAdListing.getId() > 0);
	}

	@Test
	public void putMessage_shouldSucceedWith200() throws Exception {
		MvcResult mvcResult = mvc.perform(put(createURLWithPort("/vehicle_listings/10000/")).content(objectMapper.writeValueAsString(adListing))
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		String content = mvcResult.getResponse().getContentAsString();
		AdListing newAdListing = objectMapper.readValue(content, AdListing.class);
		assertTrue(newAdListing != null);
	}

	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
