package com.ab.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.ab.entities.Exchange;
import com.ab.repositories.ExchangeRepository;
import com.ab.repositories.OrderBookRepository;
@Service
public class ExchangeService {

	@Autowired
	private ExchangeRepository exchangeRepository;
	
	public int getUserLinkedExchangeId(int userId) {
		
		return exchangeRepository.findUserLinkedExchangeId(userId);
		
	}
	
	public Exchange getExchangeByExchangeId(int exchangeId) {
		
		return exchangeRepository.findExchangeByExchangeId(exchangeId);
		
	}
	
	public List<Integer> getStockInExchange(int exchangeId) {
		
		return exchangeRepository.findStockInExchange(exchangeId);
		
	}
	
	
	
	
}
