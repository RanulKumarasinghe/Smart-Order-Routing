package com.ab.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ab.entities.Order;
import com.ab.services.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/placeOrder/")
	public int createOrder(double orderStockAmount, double orderTotalPrice,String orderType, int stockId,int userId) {
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
	public int updateOrder(double orderStockAmount, double orderTotalPrice,int userId) {
		return orderService.updateOrder(orderStockAmount, orderTotalPrice, userId);
	}
	
	
	//Cancel order
	@PostMapping("/cancelOrder")
	public int cancelOrder(int orderId) {
		return orderService.cancelOrderByOrderId(orderId);
	}
	
	//Update order to Fulfilled
	@PostMapping("/updateToFulfilled")
	public int updateToFulfilled(int orderId) {
		return orderService.updateOrderToFullfilled(orderId);
	}
	
	//Update order to Partially Fulfilled
	@PostMapping("/updateToPartiallyFulfilled")
	public int updateToPartiallyFulfilled(int orderId) {
		return orderService.updateOrderToPartiallyFullfilled(orderId);
	}

}
