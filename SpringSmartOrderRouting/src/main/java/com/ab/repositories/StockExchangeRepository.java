package com.ab.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ab.entities.StockExchange;

public interface StockExchangeRepository extends JpaRepository<StockExchange,Integer>{
	
	//get stock price with exchange & stock id 
		@Query(value="SELECT * FROM stock_exchange WHERE stock_id=:stockId AND exchange_id=:exchangeId", nativeQuery=true)
		public List<StockExchange> findStockPriceOnExchange(@Param("stockId") int stockId, @Param("exchangeId") int exchangeId);	
	//get stock price across all exchanges	
		
		@Query(value="SELECT * FROM stock_exchange WHERE stock_id=:stockId", nativeQuery=true)
		public List<StockExchange> findStockPriceInExchanges(int stockId);
		
		@Query(value="SELECT MIN(stock_price) FROM stock_exchange WHERE stock_id=:stockId", nativeQuery=true)
		public double findLowestStockPrice(int stockId);
		
		@Query(value="SELECT MAX(stock_price) FROM stock_exchange WHERE stock_id=:stockId", nativeQuery=true)
		public double findHighestStockPrice(int stockId);
		
		@Query(value="SELECT AVG(stock_price) FROM stock_exchange WHERE stock_id=:stockId", nativeQuery=true)
		public double findAverageStockPrice(int stockId);
		
		@Query(value="SELECT * FROM stock_exchange WHERE stock_id=:stockId AND available_shares >=:buyAmount", nativeQuery=true)
		public List<StockExchange> findLowsestStockPricedStock(@Param("stockId") int stockId, @Param("buyAmount") double buyAmount);
		//Update available shares 
//		@Modifying
//	    @Transactional
//	    @Query(value="UPDATE stock_exchange")
		
//		@Query(value="SELECT * FROM stockExchanges WHERE stock_price=(SELECT MIN(stock_price) FROM stockExchanges)", nativeQuery=true)
//		public StockExchange findMinFromList(List<StockExchange> stockExchanges);
		
		//Get stock exchange by minimum stock_price and stock_amount is not less than or equal to the required order amount
}
