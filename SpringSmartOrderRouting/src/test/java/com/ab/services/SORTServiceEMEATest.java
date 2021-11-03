package com.ab.services;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SORTServiceEMEATest {
	@Autowired
	private SORTServiceEMEA sortServiceEMEA;
	
	int stockId = 1;
	String region = "EMEA";
	@Test
	public void testGetEMEAExchanges() {
		assertNotNull(sortServiceEMEA.getEMEAExchanges());
	}
	
	@Test
	public void testFindPendingSaleOrdersByRegion() {
		assertNotNull(sortServiceEMEA.findPendingSaleOrdersByRegion(stockId,region));
	}
}
