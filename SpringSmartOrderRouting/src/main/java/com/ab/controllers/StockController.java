package com.ab.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ab.entities.Stock;
import com.ab.services.ExchangeService;
import com.ab.services.StockService;
import org.springframework.web.bind.annotation.SessionAttributes;

@RestController
@SessionAttributes("loggedInUser")
public class StockController {
	@Autowired
	private StockService stockService;
	
	@Autowired
	private ExchangeService exchangeService;
	
	
	@GetMapping("/stocks")
	public List<Stock> getAllStocks(){
		return stockService.getAllStocks();		
	}
	@GetMapping("/stock/{name}")
	public Stock getStocks(@PathVariable("name") String stock_symbol){
		return stockService.getStock(stock_symbol);		
	}
	@GetMapping("/stockPrice/{id}")
	public double getStockPrice(@PathVariable("id") int stock_id){
		return stockService.getStockPrice(stock_id);		
	}
	
	@GetMapping("/stocks/{stock_id}")
	public Stock getStockById(@PathVariable("stock_id")int stock_id){
		return stockService.getStockById(stock_id);
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
	
	
	@GetMapping("/stockpresentinexchange/{exchangeId}")
	public List<Stock> getStocksPresentInExchange(@PathVariable("exchangeId") int exchangeId){
		
		
		List<Integer> stockList = exchangeService.getStockInExchange(exchangeId);
		System.out.println(stockList);
		
		List<Stock> sList = new ArrayList<>();
		
		for(int i=0;i<stockList.size();i++) {
			
			Stock s = stockService.getStockById(i);
			sList.add(s);
		}
		
		return sList;
	}
	
	
	
}
