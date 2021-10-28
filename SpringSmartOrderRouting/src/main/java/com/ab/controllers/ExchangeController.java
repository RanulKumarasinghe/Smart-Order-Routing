package com.ab.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ab.entities.Exchange;
import com.ab.entities.Order;
import com.ab.entities.OrderBook;
import com.ab.services.ExchangeService;
import com.ab.services.OrderBookService;
@Controller
public class ExchangeController {


	@Autowired
	private ExchangeService exchangeService;
	
	
	@GetMapping("/exchanges/{exchangeId}")
	public  Exchange getExchangeByExchangeId(@PathVariable("exchangeId") int exchangeId) {
		return exchangeService.getExchangeByExchangeId(exchangeId);
	}
	
	
	@GetMapping("/exchangeorderbook/{exchangeId}")
	public int getExchangeOrderBookId(@PathVariable("exchangeId") int exchangeId) {
		//System.out.println("From controller-->"+exchangeId);
		return exchangeService.getExchangeOrderBookId(exchangeId);
	}
	
	@GetMapping("/exchangewithlowestfees")
	public Exchange getLowestExchangeFees(){
		return exchangeService.getLowestExchangeFees();
	}
	
	public int getExchangeIdByRegion(String region){
		return exchangeService.getExchangeIdByRegion(region);
	}

	
}
