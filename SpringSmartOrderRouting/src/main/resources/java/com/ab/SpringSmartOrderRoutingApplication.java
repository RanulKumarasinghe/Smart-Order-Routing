package com.ab;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ab.controllers.OrderController;
import com.ab.controllers.StockController;
import com.ab.controllers.StockExchangeController;
import com.ab.entities.Stock;
import com.ab.entities.StockExchange;
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
		// TODO Auto-generated method stub
		
		
				//insert order
				//System.out.println(orderController.createOrder(3.0, 360.0, "Buy", 1, 1));
		//System.out.println(orderController.createOrder(1.0, 120.0, "Buy", 1, 1));

				//System.out.println(orderController.getAllUserOrders(1));
//				int update = orderController.updateOrder(4,480, 1);
//				System.out.println(update);
//				
				//System.out.println(orderController.cancelOrder(1));
				
				//get user exchange id
				
//				List<Integer> stockList = exchangeController.getStockInExchange(1);
//				System.out.println(stockList);
//				
//				List<Stock> sList = new ArrayList<>();
//				
//				for(int i=0;i<stockList.size();i++) {
//					
//					Stock s = stockController.getStockById(i);
//					sList.add(s);
//				}
				
				//System.out.println(sList);
//		double price = 0.0;
//		
//		List<StockExchange> stockExchanges = stockExchangeController.findLowsestStockPricedStock(1, 20);
//		
//		StockExchange se = stockExchanges.stream()
//				.min(Comparator.comparingDouble(StockExchange::getStockPrice)).get();
				//stockExchangeController.findMinStock(stockExchanges);
//		for(int i = 1; i <= stockExchanges.size(); i++) {
//			price = stockExchanges.get(i).getStockPrice();	
//			if(price >= stockExchanges.get(i-1).getStockPrice()) {
//				se = stockExchanges.get(i);
//			}
//	
//		
//	System.out.println(se);
		
		
	}

}
