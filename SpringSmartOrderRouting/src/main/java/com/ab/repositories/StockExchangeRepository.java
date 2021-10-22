package com.ab.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ab.entities.StockExchange;

public interface StockExchangeRepository extends JpaRepository<StockExchange,Integer>{
	
	//get stock price with exchange & stock id 
		@Query(value="SELECT * FROM stock_exchange WHERE stock_id=:stockId AND exchange_id=:exchangeId", nativeQuery=true)
		public List<StockExchange> findStockPriceOnExchange(@Param("stockId") int stockId, @Param("exchangeId") int exchangeId);
		
		
		@Query(value="SELECT * FROM stock_exchange WHERE stock_id=:stockId", nativeQuery=true)
		public List<StockExchange> findStockPriceInExchanges(int stockId);
		
}
