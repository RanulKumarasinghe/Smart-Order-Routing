package com.ab.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ab.entities.StockExchange;
import com.ab.services.StockExchangeService;

@RestController
public class StockExchangeController {
	
	@Autowired
	private StockExchangeService stockExchangeService;
	
	
	@GetMapping("/stockpriceinexchange/{stockId}/{exchangeId}")
	public  List<StockExchange> getStockInExchange(@PathVariable("stockId") int stockId,@PathVariable("exchangeId") int exchangeId) {
		return stockExchangeService.getStockPriceOnExchange(stockId, exchangeId);
	}
	

	@GetMapping("/stockpriceinexchanges/{stockId}")
	public  List<StockExchange> getStockInExchange(@PathVariable("stockId") int stockId) {
		return stockExchangeService.getStockPriceInExchanges(stockId);
	}

}
