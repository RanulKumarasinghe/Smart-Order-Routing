package com.ab.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.ab.entities.Exchange;
import com.ab.entities.OrderBook;
import com.ab.repositories.ExchangeRepository;
import com.ab.repositories.OrderBookRepository;
@Service
public class ExchangeService {

	@Autowired
	private ExchangeRepository exchangeRepository;
	

	public Exchange getExchangeByExchangeId(int exchangeId) {
		
		return exchangeRepository.findExchangeByExchangeId(exchangeId);
		
	}
	
	public int getExchangeOrderBookId(int exchangeId) {
		return exchangeRepository.findExchangeOrderBookId(exchangeId);
	}
	public Exchange getLowestExchangeFees(){
		return exchangeRepository.findLowestExchangeFees();
	}
	
	
	public int getExchangeIdByRegion(String region){
		return exchangeRepository.getExchangeIdByRegion(region);
	}

	
	
	
}
