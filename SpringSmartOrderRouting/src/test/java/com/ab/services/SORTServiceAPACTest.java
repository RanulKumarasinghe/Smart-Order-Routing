package com.ab.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SORTServiceAPACTest {
	@Autowired
	private SORTServiceAPAC sortServiceAPAC;
	
	int stockId = 1;
	String region = "APAC";
	@Test
	public void testGetAPACExchanges() {
		assertNotNull(sortServiceAPAC.getAPACExchanges());
	}
	
	@Test
	public void testFindPendingSaleOrdersByRegion() {
		assertEquals(null,sortServiceAPAC.findPendingSaleOrdersByRegion(stockId,region));
	}
	
}
