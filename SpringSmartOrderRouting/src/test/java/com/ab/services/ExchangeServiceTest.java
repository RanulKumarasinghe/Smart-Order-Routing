package com.ab.services;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;

public class ExchangeServiceTest {
	@Autowired
	private ExchangeService exchangeService;
	
	int exchangeId = 1;
	@Test
	public void testGetAllExchanges() {
		assertNotNull(exchangeService.getAllExchanges());
	}
	
	@Test
	public void testGetExchangeByExchangeId() {
		assertNotNull(exchangeService.getExchangeByExchangeId(exchangeId));
	}
	
	@Test
	public void testGetExchangeOrderBookId() {
		assertEquals(1,exchangeService.getExchangeOrderBookId(exchangeId));
	}
	
	
	@Test
	public void testGetLowestExchangeFees() {
		assertNotNull(exchangeService.getLowestExchangeFees());
	}
	
	@Test
	public void testGetExchangeIdByRegion() {
		String region = "APAC";
		assertEquals(3,exchangeService.getExchangeIdByRegion(region));
	}
	
	
	
	
}
