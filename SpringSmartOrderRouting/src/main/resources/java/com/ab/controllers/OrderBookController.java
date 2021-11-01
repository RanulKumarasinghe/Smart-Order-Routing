package com.ab.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ab.entities.OrderBook;
import com.ab.services.OrderBookService;
import com.ab.services.OrderService;

@Controller
public class OrderBookController {
	
	@Autowired
	private OrderBookService orderBookService;
	
	@GetMapping("/orderbooks/{stockId}")
	public List<OrderBook> getOrderBookByStock(@PathVariable("stockId")int stock_id){
		return orderBookService.getOrderBookByStock(stock_id);
	}
}
