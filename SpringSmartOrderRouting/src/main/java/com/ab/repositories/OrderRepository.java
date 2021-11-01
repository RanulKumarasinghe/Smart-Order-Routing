package com.ab.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import com.ab.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{
	
	//Create Order
	@Transactional
	@Modifying
	@Query(value ="INSERT INTO orders(order_status, order_stock_amount, order_total_price, order_type, orderbook_id, stock_id, user_id) VALUES('Pending',:orderStockAmount,:orderTotalPrice,:orderType,:orderbookId,:stockId,:userId)", nativeQuery=true)
	public int insertOrder(@Param("orderStockAmount") double orderStockAmount,@Param("orderTotalPrice") double orderTotalPrice, @Param("orderType") String orderType, @Param("orderbookId") int orderbookId, @Param("stockId") int stockId, @Param("userId") int userId);
	
	
	//Get All user Orders
	@Query(value ="SELECT * FROM orders WHERE user_id=:userId", nativeQuery=true)
	public List<Order> findAllOrdersByUserId(@Param("userId") int userId);
	
	//Get Order by Id
	public Order findOrderByOrderId(int orderId);
	
	//Get Orders By StockId
	@Query(value ="SELECT * FROM orders WHERE stock_id=:stockId", nativeQuery=true)
	public List<Order> findAllOrdersByStockId( @Param("stockId") int stockId);
	
	@Query(value ="SELECT * FROM orders WHERE orderbook_id =:orderbookId", nativeQuery=true)
	public List<Order> findAllOrdersByOrderBookId( @Param("orderbookId") int orderbookId); 
	
    @Query(value ="SELECT SUM(order_total_price) FROM orders WHERE stock_id=:stockId AND user_id=:userId AND order_type = 'Buy' AND order_status = 'Fulfilled' ", nativeQuery=true)
	public double getBuyStockAmount( @Param("stockId") int stockId, @Param("userId") int userId);
	
    @Query(value ="SELECT SUM(order_total_price) FROM orders WHERE stock_id=:stockId AND user_id=:userId AND order_type = 'Sell' AND order_status = 'Fulfilled' ", nativeQuery=true)
	public String getSellStockAmount( @Param("stockId") int stockId, @Param("userId") int userId);
    //Update Order 
	@Transactional
	@Modifying
	@Query(value = "UPDATE orders o SET o.order_stock_amount=:orderStockAmount, o.order_total_price=:orderTotalPrice WHERE o.order_id=:orderId", nativeQuery=true)	
	public int updateOrder(@Param("orderStockAmount") double orderStockAmount,@Param("orderTotalPrice") double orderTotalPrice, @Param("orderId") int orderId);
	
	//Cancel Order 
	@Transactional
	@Modifying
	@Query(value = "UPDATE orders o SET o.order_status = 'Cancelled' WHERE o.order_id=:orderId", nativeQuery=true)	
	public int cancelOrderByOrderId(@Param("orderId") int orderId);
	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE orders o SET o.order_status = 'Fulfilled' WHERE o.order_id=:orderId", nativeQuery=true)	
	public int changeOrderToFullfilled(@Param("orderId") int orderId);
	

	@Transactional
	@Modifying
	@Query(value = "UPDATE orders o SET o.order_status = 'Partially_Fulfilled' WHERE o.order_id=:orderId", nativeQuery=true)	
	public int changeOrderToPartiallyFullfilled(@Param("orderId") int orderId);
	
	//user's sell orders 
	@Query(value ="SELECT * FROM orders WHERE user_id=:userId AND order_type='Sell'", nativeQuery=true)
	public List<Order> findSellOrdersByUserId(@Param("userId") int userId); 
	

	//user's buy orders 
	@Query(value ="SELECT * FROM orders WHERE user_id=:userId AND order_type='Buy'", nativeQuery=true)
	public List<Order> findBuyOrdersByUserId(@Param("userId") int userId); 
	
	//user's trade history 
	@Query(value ="SELECT * FROM orders WHERE user_id=:userId AND order_status='Fulfilled'", nativeQuery=true)
	public List<Order> findTradeHistoryByUserId(@Param("userId") int userId);

	//user's pending orders
	@Query(value ="SELECT * FROM orders WHERE user_id=:userId AND order_status='Pending'", nativeQuery=true)
	public List<Order> findUserPendingOrders(@Param("userId") int userId); 
	
	//get sell orders for one stock that are pending
	@Query(value ="SELECT * FROM orders WHERE stock_id=:stockId AND order_status='Pending' AND order_type='Sell'", nativeQuery=true)
	public List<Order> findPendingSaleOrders(@Param("stockId") int stockId); 
	
	@Query(value ="SELECT * FROM orders WHERE user_id=:userId AND order_status='Pending' AND order_type='Buy'", nativeQuery=true)
	public List<Order> findPendingBuyOrders(@Param("userId") int userId);
	
	@Query(value ="SELECT * FROM orders WHERE stock_id=:stockId AND orderbook_id=:orderBookId AND order_status='Pending' AND order_type='Sell'", nativeQuery=true)
	public List<Order> findPendingSaleOrdersByOrderBookId(@Param("stockId") int stockId, @Param("orderBookId") int orderBookId); 
	
	@Query(value ="SELECT * FROM orders o INNER JOIN stock_exchange se ON o.stock_id = se.stock_id WHERE o.stock_id=:stockId AND o.orderbook_id=:orderBookId AND o.order_status='Pending' AND o.order_type='Sell' AND o.order_stock_amount =:buyAmount AND se.stock_price=(SELECT MIN(stock_price) FROM stock_exchange)", nativeQuery=true)
	public Order findBestOrder(@Param("stockId") int stockId, @Param("orderBookId") int orderBookId, @Param("buyAmount") double buyAmount);
	//get sum of all pending sell orders for one orderbook and one stock
	
	@Query(value ="SELECT * FROM orders WHERE stock_id=:stockId AND orderbook_id=:orderBookId AND order_stock_amount=:buyAmount AND order_status='Pending' AND order_type='Sell'", nativeQuery=true)
	public Order findPendingSaleOrdersByOrderBookIdAndBuyAmount(@Param("stockId") int stockId, @Param("orderBookId") int orderBookId, @Param("buyAmount") double buyAmount); 

	@Query(value ="SELECT * FROM orders WHERE stock_id=:stockId AND orderbook_id=:orderBookId AND order_stock_amount=:buyAmount AND order_status='Pending' AND order_type='Buy'", nativeQuery=true)
	public Order findPendingBuyOrdersByOrderBookIdAndBuyAmount(@Param("stockId") int stockId, @Param("orderBookId") int orderBookId, @Param("buyAmount") double buyAmount); 

	@Query(value ="SELECT * FROM orders o INNER JOIN exchange e ON o.orderbook_id = e.exchange_id WHERE o.stock_id=:stockId AND o.order_status='Pending' AND o.order_type='Sell' AND e.region=:region", nativeQuery=true)
	public List<Order> findPendingSaleOrdersByRegion(@Param("stockId") int stockId, @Param("region") String region);
	
	
}
