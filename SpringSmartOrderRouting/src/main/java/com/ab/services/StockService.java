package com.ab.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.ab.entities.Stock;
import com.ab.repositories.StockRepository;

@Service
public class StockService {
	@Autowired
	private StockRepository stockRepository;
	
	public List<Stock> getAllStocks() {
		return stockRepository.findAll();
	}
	
	public Stock getStock(String stock_symbol){
		return stockRepository.findStocksByStockSymbol(stock_symbol);
	}
	
	public double getStockPrice(int stock_id) {
		return stockRepository.getStockPrice(stock_id);
	}
	
	public Stock getStockById(int stock_id){
		return stockRepository.getStockById(stock_id);
	}
	
	public int insertNewStock(double stock_price, String stock_symbol, double stock_total_shares) {
		return stockRepository.insertNewStock(stock_price, stock_symbol, stock_total_shares);	
	}
	
	public int updateStockPrice(int stock_id, double stock_price) {
		return stockRepository.updateStockPrice(stock_id, stock_price);
	}
	
	public int updateStockTotal(int stock_id, double stock_total_shares) {
		return stockRepository.updateStockTotal(stock_id, stock_total_shares);
	}
	
	public int updateStockSymbol(int stock_id, String stock_symbol) {
		return stockRepository.updateStockSymbol(stock_id, stock_symbol);
	}
	
	public List<Stock> getStockInExchange(int exchangeId) {
		
		return stockRepository.findStockInExchange(exchangeId);
		
	}
	

}
