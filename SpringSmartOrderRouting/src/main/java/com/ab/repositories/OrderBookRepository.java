package com.ab.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ab.entities.OrderBook;
@Repository
public interface OrderBookRepository extends JpaRepository<OrderBook,Integer>{

	//Get orderbook of one stock
	@Query(value="SELECT * FROM orderbook ob INNER JOIN orders o ON ob.order_book_id = o.orderbook_id WHERE o.stock_id =:stock_id",nativeQuery=true)	
	 public List<OrderBook> getOrderBookByStock(@Param("stock_id")int stock_id);	
	}
