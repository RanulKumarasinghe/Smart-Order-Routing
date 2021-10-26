package com.ab.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ab.entities.Exchange;
import com.ab.repositories.ExchangeRepository;

@Service
public class SORTServiceAPAC {
	
	@Autowired
	private ExchangeRepository exchangeRepoistory;
	
	public List<Exchange> getAPACExchanges(){
		return exchangeRepoistory.getAPACExchanges();
	}
}
