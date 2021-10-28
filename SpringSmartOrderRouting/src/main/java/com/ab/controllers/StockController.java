package com.ab.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ab.entities.Stock;
import com.ab.services.ExchangeService;
import com.ab.services.StockService;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
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
	public void insertNewStock(@RequestParam("stock_price") double stock_price, @RequestParam("stock_symbol") String stock_symbol, @RequestParam("stock_total_shares") double stock_total_shares) {
		stockService.insertNewStock(stock_price, stock_symbol, stock_total_shares);
	}
	
	@PostMapping("/update_stock_price")
	public void updateStockPrice(@RequestParam("stock_id") int stock_id, @RequestParam("stock_price") double stock_price) {
		stockService.updateStockPrice(stock_id, stock_price);
	}
	
	@PostMapping("/update_stock_total")
	public void updateStockTotal(@RequestParam("stock_id") int stock_id, @RequestParam("stock_total_shares") double stock_total_shares) {
		stockService.updateStockTotal(stock_id, stock_total_shares);
	}
	
	@PostMapping("/update_stock_symbol")
	public void updateStockSymbol(@RequestParam("stock_id") int stock_id, @RequestParam("stock_symbol") String stock_symbol) {
		stockService.updateStockSymbol(stock_id, stock_symbol);
	}
	

	@GetMapping("/stockinexchange/{exchangeId}")
	public  List<Stock> getStockInExchange(@PathVariable("exchangeId") int exchangeId) {
		return stockService.getStockInExchange(exchangeId);
	}
	

	
	
	
}
