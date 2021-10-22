package com.ab.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ab.entities.Exchange;
import com.ab.entities.Stock;
public interface ExchangeRepository extends JpaRepository<Exchange, Integer>{

	//get exchange details 
	@Query(value ="SELECT exchange_id, fee_ladder FROM exchange WHERE exchange_id=:exchangeId", nativeQuery=true)
	public Exchange findExchangeByExchangeId(@Param("exchangeId") int exchangeId);
	
	//get stocks presents in the exchange --> check bridge table
	//@Query(value ="SELECT stocks_stock_id FROM exchange_stocks WHERE exchange_exchange_id=:exchangeId", nativeQuery=true)
	@Query(value ="SELECT * FROM stock s INNER JOIN exchange_stocks es ON s.stock_id=es.stocks_stock_id AND es.exchange_exchange_id=:exchangeId", nativeQuery=true)
	public List<Integer> findStockInExchange(@Param("exchangeId") int exchangeId);
	
	//get exchange order books 
	
}
