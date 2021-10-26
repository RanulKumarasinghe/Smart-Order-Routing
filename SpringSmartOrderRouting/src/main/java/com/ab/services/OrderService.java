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
	
	public double getBuyStockAmount(int stockId, int userId) {
		return orderRepository.getBuyStockAmount(stockId, userId);
	}
	
	public double getSellStockAmount(int stockId, int userId) {
		String getNumber = orderRepository.getSellStockAmount(stockId, userId);
		boolean numeric = isNumeric(getNumber);
		double stockAmount = 0.0;
		if (!numeric) {
			stockAmount = 0.0;
		} else {
			stockAmount = (double) Integer.parseInt(getNumber);
		}
		return stockAmount;
	}
	
	private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }
	
	public List<Order> findAllOrdersByOrderBookId(int orderbookId){
		return orderRepository.findAllOrdersByOrderBookId(orderbookId);
	}
	
	public List<Order> getUserSellOrders(int userId){
		return orderRepository.findSellOrdersByUserId(userId);
	}
	public List<Order> getUserBuyOrders(int userId){
		return orderRepository.findBuyOrdersByUserId(userId);
	}
	public List<Order> getUserTradeHistory(int userId){
		return orderRepository.findTradeHistoryByUserId(userId);
	}

	public List<Order> getUserPendingOrders(int userId) {
		return orderRepository.findUserPendingOrders(userId);

	}
	
	public List<Order> findPendingSaleOrders(int stockId){
		return orderRepository.findPendingSaleOrders(stockId);
	}
	
	public List<Order> findPendingSaleOrdersByOrderBookId(int stockId, int orderBookId){
		return orderRepository.findPendingSaleOrdersByOrderBookId(stockId, orderBookId);
	}

	public Order findBestOrder(int stockId, int orderBookId,double buyAmount) {
		return orderRepository.findBestOrder(stockId, orderBookId, buyAmount);
	}

}
