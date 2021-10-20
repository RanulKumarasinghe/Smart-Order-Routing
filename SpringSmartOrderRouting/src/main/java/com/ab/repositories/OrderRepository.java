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
	@Query(value ="INSERT INTO orders(order_status, order_stock_amount, order_total_price, order_type, stock_id, user_id) VALUES('Pending',:orderStockAmount,:orderTotalPrice,:orderType,:stockId,:userId)", nativeQuery=true)
	public int insertOrder(@Param("orderStockAmount") double orderStockAmount,@Param("orderTotalPrice") double orderTotalPrice, @Param("orderType") String orderType, @Param("stockId") int stockId, @Param("userId") int userId);
	
	
	//Get All user Orders
	@Query(value ="SELECT * FROM orders WHERE user_id=:userId", nativeQuery=true)
	public List<Order> findAllOrdersByUserId(@Param("userId") int userId);
	
	//Get Order by Id
	public Order findOrderByOrderId(int orderId);
	
	//Get Orders By StockId
	@Query(value ="SELECT * FROM orders WHERE stock_id=:stockId", nativeQuery=true)
	public List<Order> findAllOrdersByStockId( @Param("stockId") int stockId);
	
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
	@Query(value = "UPDATE orders o SET o.order_status = 'Fullfilled' WHERE o.order_id=:orderId", nativeQuery=true)	
	public int changeOrderToFullfilled(@Param("orderId") int orderId);
	
	

	@Transactional
	@Modifying
	@Query(value = "UPDATE orders o SET o.order_status = 'Partially Fullfilled' WHERE o.order_id=:orderId", nativeQuery=true)	
	public int changeOrderToPartiallyFullfilled(@Param("orderId") int orderId);
	
	
	
	
}
