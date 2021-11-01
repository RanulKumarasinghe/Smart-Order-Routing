package com.ab.controllers;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ab.entities.Order;
import com.ab.entities.StockExchange;
import com.ab.services.OrderService;
import com.ab.services.SORTServiceAPAC;
import com.ab.services.SORTServiceEMEA;
import com.ab.services.SORTServiceNA;
import com.ab.services.StockExchangeService;

@Controller
public class StockExchangeController {
	
	@Autowired
	private StockExchangeService stockExchangeService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SORTServiceAPAC sortServiceAPAC;
	
	@Autowired
	private SORTServiceEMEA sortServiceEMEA;
	
	@Autowired
	private SORTServiceNA sortServiceNA;
	
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
	public void updatePendingSaleOrderByOrderBookId(@RequestParam("stockId") int stockId, @RequestParam("orderBookId") int orderBookId, @RequestParam("buyAmount") double buyAmount){
		
		Order order = orderService.findPendingSaleOrdersByOrderBookIdAndBuyAmount(stockId, orderBookId, buyAmount);
		
		orderService.updateOrderToFullfilled(order.getOrderId());
		
	}
	
	@PostMapping("updateBuyOrder")
	public void updatePendingBuyOrderByOrderBookId(@RequestParam("stockId") int stockId, @RequestParam("orderBookId") int orderBookId, @RequestParam("buyAmount") double buyAmount){		
		Order order = orderService.findPendingBuyOrdersByOrderBookIdAndBuyAmount(stockId, orderBookId, buyAmount);
		
		orderService.updateOrderToFullfilled(order.getOrderId());
		
	}
	
	@PostMapping("updateTradeOrders")
	public void updateTradeOrders(@RequestParam("stockId") int stockId, @RequestParam("orderBookId") int orderBookId, @RequestParam("buyAmount") double buyAmount){
		Order buyOrder = orderService.findPendingBuyOrdersByOrderBookIdAndBuyAmount(stockId, orderBookId, buyAmount);
		
		orderService.updateOrderToFullfilled(buyOrder.getOrderId());
		
        Order sellOrder = orderService.findPendingSaleOrdersByOrderBookIdAndBuyAmount(stockId, orderBookId, buyAmount);
		
		orderService.updateOrderToFullfilled(sellOrder.getOrderId());
	}
	
	//@GetMapping("/saleOrderStocks/{stockId}/{region}")
	public List<Order> saleOrdersPerRegion(@PathVariable("stockId") int stockId, @PathVariable("region") String region){
		if(region.equals("APAC")) {
			return sortServiceAPAC.findPendingSaleOrdersByRegion(stockId, region);
		} else if(region.equals("EMEA")) {
			return sortServiceEMEA.findPendingSaleOrdersByRegion(stockId, region);
		} else if(region.equals("NA")) {
			return sortServiceNA.findPendingSaleOrdersByRegion(stockId, region);
		} else {
			return null;
		}
	}
	
	//@GetMapping("/getmatchedorder/{stockId}/{region}/{buyAmount}")
	public Order getMatchedSaleOrder(@PathVariable("stockId") int stockId, @PathVariable("region") String region, @PathVariable("buyAmount") double buyAmount) {
		Order order = null;
		
		List<Order> orders = saleOrdersPerRegion(stockId,region);
		for(int i=0; i<orders.size();i++) {
			
			if(orders.get(i).getOrderStockAmount() == buyAmount) {
				order = orders.get(i);
			}
		}
		return order;
	}
	
	@PostMapping("/updateMatchedOrder")
	public void updateMatchedOrder(@RequestParam("userId") int userId, @RequestParam("stockId") int stockId, @RequestParam("region") String region, @RequestParam("buyAmount") double buyAmount) {
		Order sellOrder = getMatchedSaleOrder(stockId,region,buyAmount);
		double buyamount = buyAmount; 
		orderService.updateOrderToFullfilled(sellOrder.getOrderId());
		int orderBookId = sellOrder.getOrderbook().getOrderBookId();
		
		Order buyOrder = orderService.findPendingBuyOrdersByOrderBookIdAndBuyAmount(stockId, orderBookId, buyamount);
//		for(int i=0; i<buyOrders.size();i++) {
//		
//		if(buyOrders.get(i).getOrderStockAmount() == buyamount) {
//			buyOrder = buyOrders.get(i);
//		}
//	}
		orderService.updateOrderToFullfilled(buyOrder.getOrderId());
		}
			
	
	

}
