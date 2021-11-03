package com.ab.services;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderBookServiceTest {

	@Autowired
	private OrderBookService orderBookService;
	
	int stock_id = 1;
	@Test
	public void testGetOrderBookByStock() {
		assertNotNull(orderBookService.getOrderBookByStock(stock_id));
	}
	
	@Test
	public void testGetOrderBooks() {
		assertNotNull(orderBookService.getOrderBooks());
	}
	
}
