package com.ab.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.ab.entities.OrderBook;
import com.ab.repositories.OrderBookRepository;

@Service
public class OrderBookService {
	@Autowired
	private OrderBookRepository orderBookRepository;
	
	public List<OrderBook> getOrderBookByStock(int stock_id){
		return orderBookRepository.getOrderBookByStock(stock_id);
	}
	
	public List<OrderBook> getOrderBooks(){
		return orderBookRepository.findAll();
	}
}
