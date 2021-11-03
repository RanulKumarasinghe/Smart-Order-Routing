package com.ab.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserStockServiceTest {

	@Autowired
	private UserStockService userStockService;
	
	int userId = 1;
	int stockId = 1;
	double stockAmount = 40;
	
	@Test
	public void testFindUserStocks() {
		assertNotNull(userStockService.findUserStocks(userId));
	}
	
	@Test
	public void testUpdateStockAmount() {
		assertEquals(1,userStockService.updateStockAmount(userId,stockId,stockAmount));
	}
	
	@Test
	public void testFindUserStock() {
		assertNotNull(userStockService.findUserStock(userId,stockId));
	}
	
	@Test
	public void testGetStockAmount() {
		assertNotNull(userStockService.getStockAmount(userId,stockId));
	}
	
	@Test
	public void testAddStockAmount() {
		userId = 5;
		stockId = 2;
		assertEquals(1,userStockService.addUserStock(userId,stockId,stockAmount));
	}
}
