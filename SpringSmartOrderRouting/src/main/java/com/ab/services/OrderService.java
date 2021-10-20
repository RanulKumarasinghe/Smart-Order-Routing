package com.ab.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.ab.entities.Order;
import com.ab.repositories.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	public OrderRepository orderRepository;
	
	public int createOrder(double orderStockAmount, double orderTotalPrice, String orderType, int stockId,int userId) {
		return orderRepository.insertOrder(orderStockAmount,orderTotalPrice, orderType, stockId, userId);
	}
	
	public List<Order> getAllOrdersByUserId(int userId){
		return orderRepository.findAllOrdersByUserId(userId);
	}
	
	public List<Order> getAllOrdersByStockId(int userId){
		return orderRepository.findAllOrdersByStockId(userId);
	}
	
	
	public Order getOrderByOrderId(int userId){
		return orderRepository.findOrderByOrderId(userId);
	}
	
	public int updateOrder(double orderStockAmount, double orderTotalPrice, int orderId){
		return orderRepository.updateOrder(orderStockAmount, orderTotalPrice, orderId);

	}
	
	public int cancelOrderByOrderId(int userId){
		return orderRepository.cancelOrderByOrderId(userId);
	}
	
	public int updateOrderToFullfilled(int userId){
		return orderRepository.changeOrderToFullfilled(userId);
	}
	
	public int updateOrderToPartiallyFullfilled(int userId){
		return orderRepository.changeOrderToPartiallyFullfilled(userId);
	}
	

}
