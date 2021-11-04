package com.ab;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ab.controllers.OrderController;
import com.ab.controllers.StockController;
import com.ab.controllers.StockExchangeController;

import com.ab.controllers.ExchangeController;

@SpringBootApplication
public class SpringSmartOrderRoutingApplication implements CommandLineRunner {
	@Autowired
	public OrderController orderController;
	
	@Autowired
	public ExchangeController exchangeController;
	
	@Autowired
	public StockController stockController;
	
	@Autowired
	public StockExchangeController stockExchangeController;
	public static void main(String[] args) {
		SpringApplication.run(SpringSmartOrderRoutingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

}
