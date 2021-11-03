package com.ab.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ab.entities.Order;

public class OrderServiceTest {
	@Autowired
	private OrderService orderService;
	
	List<Order> buyOrders = new ArrayList<>();
	List<Order> sellOrders = new ArrayList<>();
	int orderId = 7;
	double orderStockAmount = 3.0;
	double orderTotalPrice = 120;
	String orderType = "Sell";
	int orderbookId = 1;
	int stockId = 1;
	int userId = 5;
	double buyAmount = 120.0;
	@Test
	public void testGetOrderBookByStock() {
		assertEquals(1,orderService.createOrder(orderStockAmount, orderTotalPrice, orderType, orderbookId, stockId, userId));
	}
	
	@Test
	public void testGetAllOrdersByUserId() {
		assertNotNull(orderService.getAllOrdersByUserId(userId));
	}
	
	@Test
	public void testGetAllOrdersByStockId() {
		assertNotNull(orderService.getAllOrdersByStockId(stockId));
	}
	
	@Test
	public void testGetOrderByOrderId() {
		assertNotNull(orderService.getOrderByOrderId(orderId));
	}
	
	@Test
	public void testUpdateOrder() {
		assertEquals(1,orderService.updateOrder(orderStockAmount,orderTotalPrice,orderId));
	}
	
	@Test
	public void testCancelOrderByOrderId() {
		assertEquals(1,orderService.cancelOrderByOrderId(orderId));
	}
	
	@Test
	public void testUpdateOrderToFullfilled() {
		assertEquals(1,orderService.updateOrderToFullfilled(orderId));
	}
	
	@Test
	public void testUpdateOrderToPartiallyFullfilled() {
		assertEquals(1,orderService.updateOrderToPartiallyFullfilled(orderId));
	}
	
	@Test
	public void testUpdateOrdersToFullfilled() {
		assertEquals(false,orderService.updateOrdersToFullfilled(buyOrders,sellOrders));
	}
	
	@Test
	public void testGetBuyStockAmount() {
		assertNotNull(orderService.getBuyStockAmount(stockId,userId));
	}
	
	@Test
	public void testGetSellStockAmount() {
		assertNotNull(orderService.getSellStockAmount(stockId,userId));
	}
	
	@Test
	public void testFindAllOrdersByOrderBookId() {
		assertNotNull(orderService.findAllOrdersByOrderBookId(orderbookId));
	}
	
	@Test
	public void testGetUserSellOrders() {
		assertNotNull(orderService.getUserSellOrders(userId));
	}
	
	@Test
	public void testGetUserBuyOrders() {
		assertNotNull(orderService.getUserBuyOrders(userId));
	}
	
	@Test
	public void testGetUserTradeHistory() {
		assertNotNull(orderService.getUserTradeHistory(userId));
	}
	
	@Test
	public void testGetUserPendingOrders() {
		assertNotNull(orderService.getUserPendingOrders(userId));
	}
	
	@Test
	public void testFindPendingSaleOrders() {
		assertNotNull(orderService.findPendingSaleOrders());
	}
	
	@Test
	public void testFindPendingBuyOrders() {
		assertNotNull(orderService.findPendingBuyOrders(userId));
	}
	
	@Test
	public void testFindPendingSaleOrdersByOrderBookId() {
		assertNotNull(orderService.findPendingSaleOrdersByOrderBookId(stockId,orderbookId));
	}
	
	@Test
	public void testFindBestOrder() {
		assertNotNull(orderService.findBestOrder(stockId,orderbookId,buyAmount));
	}
	
	@Test
	public void testFindPendingSaleOrdersByOrderBookIdAndBuyAmount() {
		assertNotNull(orderService.findPendingSaleOrdersByOrderBookIdAndBuyAmount(stockId,orderbookId,buyAmount));
	}
	
	@Test
	public void testFindPendingBuyOrdersByOrderBookIdAndBuyAmount() {
		assertNotNull(orderService.findPendingBuyOrdersByOrderBookIdAndBuyAmount(stockId,orderbookId,buyAmount));
	}
	
	
	
	
}
