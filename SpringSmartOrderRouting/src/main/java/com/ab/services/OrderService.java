package com.ab.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.ab.entities.Order;
import com.ab.entities.StockExchange;
import com.ab.entities.UserStock;
import com.ab.repositories.OrderRepository;
import com.ab.repositories.StockExchangeRepository;
import com.ab.repositories.UserRepository;
import com.ab.repositories.UserStockRepository;

@Service
public class OrderService {
	
	@Autowired
	public OrderRepository orderRepository;
	@Autowired
    public UserRepository userRepository;
	@Autowired
	public UserStockRepository userStockRepository;
	@Autowired
	private StockExchangeRepository stockExchangeRepository;
	
	
	public int createOrder(double orderStockAmount, double orderTotalPrice, String orderType, int orderbookId, int stockId,int userId) {
		return orderRepository.insertOrder(orderStockAmount,orderTotalPrice, orderType, orderbookId, stockId, userId);
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
	
	public int updateOrderToFullfilled(int orderId){
		return orderRepository.changeOrderToFullfilled(orderId);
	}
	
	public int updateOrderToPartiallyFullfilled(int userId){
		return orderRepository.changeOrderToPartiallyFullfilled(userId);
	}
	
	public boolean updateOrdersToFullfilled(List<Order> buyOrders, List<Order> sellOrders) {
		boolean fulfilled = false;
		for(Order buyOrder : buyOrders){
			for(Order sellOrder: sellOrders) {
				double balance = userRepository.findUserBalance(buyOrder.getUser().getUserId());
				if(buyOrder.getOrderStockAmount() == sellOrder.getOrderStockAmount() && 
						buyOrder.getStock().getStockId() == sellOrder.getStock().getStockId() && 
						buyOrder.getUser().getUserId() != sellOrder.getUser().getUserId() && !fulfilled && balance > 0){
						orderRepository.changeOrderToFullfilled(buyOrder.getOrderId());
						orderRepository.changeOrderToFullfilled(sellOrder.getOrderId());
						double user_balance = userRepository.findUserBalance(buyOrder.getUser().getUserId());
						double seller_balance = userRepository.findUserBalance(sellOrder.getUser().getUserId());
						
						userRepository.updateUserBalance(buyOrder.getUser().getUserId(), user_balance - buyOrder.getOrderTotalPrice());
						userRepository.updateUserBalance(sellOrder.getUser().getUserId(), seller_balance + sellOrder.getOrderTotalPrice());
						
						List<UserStock> findUserStocks = userStockRepository.findUserStocks(buyOrder.getUser().getUserId());
						if(findUserStocks.isEmpty()) {
							userStockRepository.addUserStock(buyOrder.getUser().getUserId(), buyOrder.getStock().getStockId(), buyOrder.getOrderStockAmount());
						} else {
							double userAmount = Double.parseDouble(userStockRepository.getStockAmount(buyOrder.getUser().getUserId(),buyOrder.getStock().getStockId()));
							double newUserAmount = userAmount + buyOrder.getOrderStockAmount();
							userStockRepository.updateStockAmount(buyOrder.getUser().getUserId(),buyOrder.getStock().getStockId(), newUserAmount);
						}
						
						
						double sellerAmount = Double.parseDouble(userStockRepository.getStockAmount(sellOrder.getUser().getUserId(),sellOrder.getStock().getStockId()));
						
						double newSellerAmount = sellerAmount - buyOrder.getOrderStockAmount();
//						userStockRepository.updateStockAmount(sellOrder.getUser().getUserId(),sellOrder.getStock().getStockId(), newSellerAmount);
						StockExchange stockExchange = stockExchangeRepository.findStockOnExchange(sellOrder.getStock().getStockId(), sellOrder.getOrderbook().getOrderBookId());
						double stockAmount = stockExchange.getAvailableShares() - buyOrder.getOrderStockAmount();
						stockExchangeRepository.updateShares(sellOrder.getStock().getStockId(), sellOrder.getOrderbook().getOrderBookId(), stockAmount);
						
						fulfilled = true;
				}
			}
			}
		return fulfilled;
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
			stockAmount = Double.parseDouble(getNumber);
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
	
	public List<Order> findPendingSaleOrders(){
		return orderRepository.findPendingSaleOrders();
	}
	
	public List<Order> findPendingBuyOrders(int userId){
		return orderRepository.findPendingBuyOrders(userId);
	}
	
	public List<Order> findPendingSaleOrdersByOrderBookId(int stockId, int orderBookId){
		return orderRepository.findPendingSaleOrdersByOrderBookId(stockId, orderBookId);
	}

	public Order findBestOrder(int stockId, int orderBookId,double buyAmount) {
		return orderRepository.findBestOrder(stockId, orderBookId, buyAmount);
	}
	
	public Order findPendingSaleOrdersByOrderBookIdAndBuyAmount(int stockId, int orderBookId, double buyAmount){
		return orderRepository.findPendingSaleOrdersByOrderBookIdAndBuyAmount(stockId, orderBookId, buyAmount);
	} 
	public Order findPendingBuyOrdersByOrderBookIdAndBuyAmount(int stockId, int orderBookId, double buyAmount) {
		return orderRepository.findPendingBuyOrdersByOrderBookIdAndBuyAmount(stockId, orderBookId, buyAmount);
	} 


}
