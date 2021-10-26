package com.ab.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ab.entities.Order;
import com.ab.services.ExchangeService;
import com.ab.services.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ExchangeService exchangeService;
	
	
	
	@PostMapping("/placeOrder/")
	public int createOrder(@RequestParam("orderStockAmount") double orderStockAmount, @RequestParam("orderTotalPrice") double orderTotalPrice, @RequestParam("orderType") String orderType, @RequestParam("stockId") int stockId, @RequestParam("userId") int userId) {
		return orderService.createOrder(orderStockAmount, orderTotalPrice, orderType, stockId, userId);
	}
	
	@GetMapping("/userorders/{userId}")
	public  List<Order> getAllUserOrders(@PathVariable("userId") int userId) {
		return orderService.getAllOrdersByUserId(userId);
	}
	
	@GetMapping("/stockorders/{stockId}")
	public  List<Order> getAllOrdersByStockId(@PathVariable("stockId") int stockId) {
		return orderService.getAllOrdersByStockId(stockId);
	}
	
	@GetMapping("/orders/{orderId}")
	public Order getOrderByOrderId(@PathVariable("orderId") int orderId) {
		return orderService.getOrderByOrderId(orderId);
	}
	
	@GetMapping("/stockAmount/{userId}/{stockId}")
	public double getUserStockAmount(@PathVariable("userId") int userId, @PathVariable("stockId") int stockId) {
		double buyAmount = orderService.getBuyStockAmount(stockId, userId);
		double sellAmount = orderService.getSellStockAmount(stockId, userId);
		double amount = buyAmount - sellAmount;
		return amount;
	}
	
	//Calculate the stock total price on base of amount
	@PostMapping("/updateOrder")
	public int updateOrder(@RequestParam("orderStockAmount") double orderStockAmount, @RequestParam("orderTotalPrice") double orderTotalPrice, @RequestParam("userId") int userId) {
		return orderService.updateOrder(orderStockAmount, orderTotalPrice, userId);
	}
	
	//Cancel order
	@PostMapping("/cancelOrder")
	public int cancelOrder(@RequestParam("orderId") int orderId) {
		return orderService.cancelOrderByOrderId(orderId);
	}
	
	//Update order to Fulfilled
	@PostMapping("/updateToFulfilled")
	public int updateToFulfilled(@RequestParam("orderId") int orderId) {
		return orderService.updateOrderToFullfilled(orderId);
	}
	
	//Update order to Partially Fulfilled
	@PostMapping("/updateToPartiallyFulfilled")
	public int updateToPartiallyFulfilled(@RequestParam("orderId") int orderId) {
		return orderService.updateOrderToPartiallyFullfilled(orderId);
	}
	
	@GetMapping("/orderbook/orders/{orderbookId}")
	public List<Order> getAllOrdersByOrderBookId(@PathVariable("orderbookId") int orderbookId){
		int exchangeId =1;
		orderbookId = exchangeService.getExchangeOrderBookId(exchangeId);
		return orderService.findAllOrdersByOrderBookId(orderbookId);
	}
	@GetMapping("sellorders/{userId}")
	public List<Order> getUserSellOrders(@PathVariable("userId") int userId){
		return orderService.getUserSellOrders(userId);
	}
	@GetMapping("buyorders/{userId}")
	public List<Order> getUserBuyOrders(@PathVariable("userId") int userId){
		return orderService.getUserBuyOrders(userId);
	}
	@GetMapping("tradehistory/{userId}")
	public List<Order> getUserTradeHistory(@PathVariable("userId") int userId){
		return orderService.getUserTradeHistory(userId);
	}
	@GetMapping("pendingorders/{userId}")
	public List<Order> getUserPendingOrders(@PathVariable("userId") int userId){
		return orderService.getUserPendingOrders(userId);
	}
	@GetMapping("pendingsaleorders/{stockId}")
	public List<Order> findPendingSaleOrders(@PathVariable("stockId") int stockId){
		return orderService.findPendingSaleOrders(stockId);
	}
	@GetMapping("pendingsaleorders/{stockId}/{orderBookId}")
	public List<Order> findPendingSaleOrdersByOrderBookId(@PathVariable("stockId") int stockId, @PathVariable("orderBookId") int orderBookId){
		return orderService.findPendingSaleOrdersByOrderBookId(stockId, orderBookId);
	}
//	@GetMapping("pendingsaleorders/{stockId}/{orderBookId}/{buyAmount}")
//	public Order findBestOrder(@PathVariable("stockId") int stockId, @PathVariable("orderBookId") int orderBookId,@PathVariable("buyAmount") double buyAmount) {
//		return orderService.findBestOrder(stockId, orderBookId, buyAmount);
//	}
	

}
