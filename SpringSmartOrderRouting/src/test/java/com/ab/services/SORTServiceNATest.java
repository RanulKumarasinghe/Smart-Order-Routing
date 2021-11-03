package com.ab.services;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SORTServiceNATest {
	@Autowired
	private SORTServiceNA sortServiceNA;
	
	int stockId = 1;
	String region = "NA";
	@Test
	public void testGetNAExchanges() {
		assertNotNull(sortServiceNA.getNAExchanges());
	}
	
	@Test
	public void testFindPendingSaleOrdersByRegion() {
		assertNotNull(sortServiceNA.findPendingSaleOrdersByRegion(stockId,region));
	}
}
