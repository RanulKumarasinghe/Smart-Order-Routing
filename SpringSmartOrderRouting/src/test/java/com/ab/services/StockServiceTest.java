package com.ab.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StockServiceTest {
	@Autowired
	private StockService stockService;
	
	String stock_symbol = "ENRON";
	int stock_id = 2;
	double stock_total_shares = 20;
	double stock_price = 100;
	
	@Test
	public void testGetStockPriceOnExchange() {
		assertNotNull(stockService.getAllStocks());
	}
	
	@Test
	public void testGetStock() {
		assertNotNull(stockService.getStock(stock_symbol));
	}
	
	@Test
	public void testGetStockPrice() {
		assertNotNull(stockService.getStockPrice(stock_id));
	}
	
	@Test
	public void testGetStockById() {
		assertNotNull(stockService.getStockById(stock_id));
	}
	
	@Test
	public void testGetStockNameById() {
		assertEquals("ENRON",stockService.getStockNameById(stock_id));
	}
	
	@Test
	public void testInsertNewStock() {
		stock_symbol = "Tesla";
		assertEquals(1,stockService.insertNewStock(stock_price,stock_symbol,stock_total_shares));
	}
	
	@Test
	public void testUpdateStockPrice() {
		assertEquals(1,stockService.updateStockPrice(stock_id,stock_price));
	}
	
	@Test
	public void testUpdateStockTotal() {
		assertEquals(1,stockService.updateStockPrice(stock_id,stock_total_shares));
	}
	
	@Test
	public void testUpdateStockSymbol() {
		stock_symbol = "GM";
		assertEquals(1,stockService.updateStockSymbol(stock_id,stock_symbol));
	}
	
	@Test
	public void testGetStockInExchange() {
		int exchange_id = 2;
		assertNotNull(stockService.getStockInExchange(exchange_id));
	}
	
	
}
