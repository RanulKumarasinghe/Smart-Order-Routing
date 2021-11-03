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

		public String getAvailability(int stockId, int exchangeId) {
			List<StockExchange> getStockExchanges = stockExchangeRepository.findStockPriceOnExchange(stockId, exchangeId);
			String available = "available";
			if(getStockExchanges.isEmpty()) {
				available = "not available";
			} else {
				available = "available";
			}
			
			return available;
		}

		public List<StockExchange> getStockPriceInExchanges(int stockId) {
			return stockExchangeRepository.findStockPriceInExchanges(stockId);
		}
		
		public double findLowestStockPrice(int stockId) {
			return stockExchangeRepository.findLowestStockPrice(stockId);
		}
		
		public double findHighestStockPrice(int stockId) {
			return stockExchangeRepository.findHighestStockPrice(stockId);
		}
		
		public double findAverageStockPrice(int stockId) {
			return stockExchangeRepository.findAverageStockPrice(stockId);
		}
		
		public List<StockExchange> findLowsestStockPricedStock(int stockId, double buyAmount) {
			return stockExchangeRepository.findLowsestStockPricedStock(stockId, buyAmount);
		}

		public double findBestStockPriceOnExchange(int stockId, int exchangeId) {
			return stockExchangeRepository.findBestStockPriceOnExchange(stockId, exchangeId);
		}

		public int updateShares(int stockId, int exchangeId, double available_shares) {
			return stockExchangeRepository.updateShares(stockId, exchangeId, available_shares);
		}
		
		public StockExchange findStockOnExchange(int stockId, int exchangeId) {
			return stockExchangeRepository.findStockOnExchange(stockId, exchangeId);
		}

//		public StockExchange findMinPriceStock(List<StockExchange> stockExchanges) {
//			return stockExchangeRepository.findMinFromList(stockExchanges);
//		}

}
