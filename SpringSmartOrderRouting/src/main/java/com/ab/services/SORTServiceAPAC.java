package com.ab.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ab.entities.Exchange;
import com.ab.entities.Order;
import com.ab.repositories.ExchangeRepository;
import com.ab.repositories.OrderRepository;

@Service
public class SORTServiceAPAC {
	
	@Autowired
	private ExchangeRepository exchangeRepoistory;
	@Autowired
	private OrderRepository orderRepository;
	
	public List<Exchange> getAPACExchanges(){
		return exchangeRepoistory.getAPACExchanges();
	}
	
	public List<Order> findPendingSaleOrdersByRegion(int stockId, String region){
		return orderRepository.findPendingSaleOrdersByRegion(stockId,region);
	}
}
