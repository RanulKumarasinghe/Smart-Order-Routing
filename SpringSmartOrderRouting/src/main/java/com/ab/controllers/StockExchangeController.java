package com.ab.controllers;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ab.entities.Order;
import com.ab.entities.StockExchange;
import com.ab.services.OrderService;
import com.ab.services.StockExchangeService;

@RestController
public class StockExchangeController {
	
	@Autowired
	private StockExchangeService stockExchangeService;
	
	@Autowired
	private OrderService orderService;
	
	private StockExchange se;
	@GetMapping("/stockpriceinexchange/{stockId}/{exchangeId}")
	public  List<StockExchange> getStockInExchange(@PathVariable("stockId") int stockId,@PathVariable("exchangeId") int exchangeId) {
		return stockExchangeService.getStockPriceOnExchange(stockId, exchangeId);
	}
	

	@GetMapping("/stockpriceinexchanges/{stockId}")
	public  List<StockExchange> getStockInExchange(@PathVariable("stockId") int stockId) {
		return stockExchangeService.getStockPriceInExchanges(stockId);
	}
	
	@GetMapping("/LowestStockPrice/{stockId}")
	public double findLowestStockPrice(@PathVariable("stockId") int stockId) {
		return stockExchangeService.findLowestStockPrice(stockId);
	}
	
	@GetMapping("/HighestStockPrice/{stockId}")
	public double findHighestStockPrice(@PathVariable("stockId") int stockId) {
		return stockExchangeService.findHighestStockPrice(stockId);
	}
	
	@GetMapping("/AverageStockPrice/{stockId}")
	public double findAverageStockPrice(@PathVariable("stockId") int stockId) {
		return stockExchangeService.findAverageStockPrice(stockId);
	}
	
	@GetMapping("/LowestStock/{stockId}/{buyAmount}")
	public StockExchange findLowsestStockPricedStock(@PathVariable("stockId") int stockId, @PathVariable("buyAmount") double buyAmount) {
		List<StockExchange> stockExchanges = stockExchangeService.findLowsestStockPricedStock(stockId, buyAmount);
		se = stockExchanges.stream().min(Comparator.comparingDouble(StockExchange::getStockPrice)).get();
		return se;
	}
	
	@PostMapping("pendingSaleOrder")
	public Order findPendingSaleOrdersByOrderBookId(@RequestParam("stockId") int stockId, @RequestParam("orderBookId") int orderBookId, @RequestParam("buyAmount") double buyAmount){
		Order order = orderService.findPendingSaleOrdersByOrderBookIdAndBuyAmount(stockId, orderBookId, buyAmount);
	
		return order;
	} 
	
	@PostMapping("pendingBuyOrder")
	public Order findPendingBuyOrdersByOrderBookId(@RequestParam("stockId") int stockId, @RequestParam("orderBookId") int orderBookId, @RequestParam("buyAmount") double buyAmount){
		
		Order order = orderService.findPendingBuyOrdersByOrderBookIdAndBuyAmount(stockId, orderBookId, buyAmount);
		return order;
	} 
	
	@PostMapping("updateSaleOrder")
	public void updatePendingSaleOrdersByOrderBookId(@RequestParam("stockId") int stockId, @RequestParam("orderBookId") int orderBookId, @RequestParam("buyAmount") double buyAmount){
		
		Order order = orderService.findPendingSaleOrdersByOrderBookIdAndBuyAmount(stockId, orderBookId, buyAmount);
		
		orderService.updateOrderToFullfilled(order.getOrderId());
		
	}
	
	@PostMapping("updateBuyOrder")
	public void updatePendingBuyOrdersByOrderBookId(@RequestParam("stockId") int stockId, @RequestParam("orderBookId") int orderBookId, @RequestParam("buyAmount") double buyAmount){
//		int orderBookId = se.getExchange().getExchangeId();
//		int stockId = se.getStock().getStockId();
		
		Order order = orderService.findPendingBuyOrdersByOrderBookIdAndBuyAmount(stockId, orderBookId, buyAmount);
		
		orderService.updateOrderToFullfilled(order.getOrderId());
		//		double buy_amount = buyAmount;
//	
//			
		//order with sell amount = buy amount --> update to fulfill
		
	}

	
	


	

}
