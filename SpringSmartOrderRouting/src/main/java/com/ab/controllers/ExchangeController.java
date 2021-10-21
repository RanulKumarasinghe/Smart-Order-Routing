package com.ab.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ab.entities.Exchange;
import com.ab.entities.Order;
import com.ab.services.ExchangeService;
import com.ab.services.OrderBookService;
@RestController
public class ExchangeController {


	@Autowired
	private ExchangeService exchangeService;
	
	@GetMapping("/userexchange/{userId}")
	public  int getUserLinkedExchangeId(@PathVariable("userId") int userId) {
		return exchangeService.getUserLinkedExchangeId(userId);
	
	}
	
	@GetMapping("/exchanges/{exchangeId}")
	public  Exchange getExchangeByExchangeId(@PathVariable("exchangeId") int exchangeId) {
		return exchangeService.getExchangeByExchangeId(exchangeId);
	}
	
	@GetMapping("/stockinexchange/{exchangeId}")
	public  List<Integer> getStockInExchange(@PathVariable("exchangeId") int exchangeId) {
		return exchangeService.getStockInExchange(exchangeId);
	}
	
	
}
