package com.ab.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ab.entities.Stock;
import com.ab.services.StockService;

@RestController
public class StockController {
	@Autowired
	private StockService stockService;
	
	
	@GetMapping("/stocks")
	public List<Stock> getAllStocks(){
		return stockService.getAllStocks();		
	}
	@GetMapping("/stock/{name}")
	public Stock getStocks(@PathVariable("name") String stock_symbol){
		return stockService.getStock(stock_symbol);		
	}
	@GetMapping("/stockPrice")
	public double getStockPrice(int stock_id){
		return stockService.getStockPrice(stock_id);		
	}
	
	@PostMapping("/addStock")
	public void insertNewStock(double stock_price, String stock_symbol, double stock_total_shares) {
		stockService.insertNewStock(stock_price, stock_symbol, stock_total_shares);
	}
	
	@PostMapping("/update_stock_price")
	public void updateStockPrice(int stock_id, double stock_price) {
		stockService.updateStockPrice(stock_id, stock_price);
	}
	
	@PostMapping("/update_stock_total")
	public void updateStockTotal(int stock_id, double stock_total_shares) {
		stockService.updateStockTotal(stock_id, stock_total_shares);
	}
	
	@PostMapping("/update_stock_symbol")
	public void updateStockSymbol(int stock_id, String stock_symbol) {
		stockService.updateStockSymbol(stock_id, stock_symbol);
	}
}
