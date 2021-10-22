package com.ab.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.ab.entities.StockExchange;
import com.ab.repositories.StockExchangeRepository;
import com.ab.repositories.StockRepository;

@Service
public class StockExchangeService {
	
	@Autowired
	private StockExchangeRepository stockExchangeRepository;

	
		public List<StockExchange> getStockPriceOnExchange(int stock, int exchangeId){
			return stockExchangeRepository.findStockPriceOnExchange(stock, exchangeId);
		}


		public List<StockExchange> getStockPriceInExchanges(int stockId) {
			return stockExchangeRepository.findStockPriceInExchanges(stockId);
		}
		

}
