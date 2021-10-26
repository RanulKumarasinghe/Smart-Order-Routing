package com.ab.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ab.entities.Exchange;
import com.ab.entities.OrderBook;
import com.ab.entities.Stock;
import com.ab.entities.StockExchange;
public interface ExchangeRepository extends JpaRepository<Exchange, Integer>{

	//get exchange details 
	@Query(value ="SELECT exchange_id, fee_ladder,exchange_name FROM exchange WHERE exchange_id=:exchangeId", nativeQuery=true)
	public Exchange findExchangeByExchangeId(@Param("exchangeId") int exchangeId);
	
	
	
	//get exchange order books 
	//get exchange details 
	@Query(value ="SELECT order_book_id FROM orderbook WHERE exchange_id=:exchangeId", nativeQuery=true)
	public int findExchangeOrderBookId(@Param("exchangeId") int exchangeId);
		
	//get the best lowest fees 
		@Query(value="SELECT * FROM exchange WHERE fee_ladder=(SELECT MIN(fee_ladder) FROM exchange)", nativeQuery=true)
		public Exchange findLowestExchangeFees();
	
	//get exchanges in EMEA
	@Query(value = "SELECT * FROM exchange WHERE region='EMEA'", nativeQuery=true)
	public List<Exchange> getEMEAExchanges();
	
	//get exchanges in NA
	@Query(value = "SELECT * FROM exchange WHERE region='NA'", nativeQuery=true)
	public List<Exchange> getNAExchanges();
	
	//get exchanges in APAC
	@Query(value = "SELECT * FROM exchange WHERE region='APAC'", nativeQuery=true) 
	public List<Exchange> getAPACExchanges();
}
