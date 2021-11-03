package com.ab.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StockExchangeServiceTest {
	@Autowired
	private StockExchangeService stockExchangeService;
	
	int stockId = 1;
	int exchangeId = 1;
	double buyAmount = 15;
	
	@Test
	public void testGetStockPriceOnExchange() {
		assertNotNull(stockExchangeService.getStockPriceOnExchange(stockId,exchangeId));
	}
	
	@Test
	public void testGetAvailability() {
		assertEquals("available",stockExchangeService.getAvailability(stockId,exchangeId));
	}
	
	@Test
	public void testGetStockPriceInExchanges() {
		assertNotNull(stockExchangeService.getStockPriceInExchanges(stockId));
	}
	
	@Test
	public void testFindLowestStockPrice() {
		assertNotNull(stockExchangeService.findLowestStockPrice(stockId));
	}
	
	@Test
	public void testFindHighestStockPrice() {
		assertNotNull(stockExchangeService.findHighestStockPrice(stockId));
	}
	
	@Test
	public void testFindAverageStockPrice() {
		assertNotNull(stockExchangeService.findAverageStockPrice(stockId));
	}
	
	@Test
	public void testFindLowsestStockPricedStock() {
		assertNotNull(stockExchangeService.findLowsestStockPricedStock(stockId,buyAmount));
	}
	@Test
	public void testfindBestStockPriceOnExchange() {
		assertNotNull(stockExchangeService.findBestStockPriceOnExchange(stockId,exchangeId));
	}
}
