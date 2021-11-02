package com.ab.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ab.entities.Stock;


@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
	
	
	
	public Stock findStocksByStockSymbol(String stock_symbol);
	
	
	@Query(value="SELECT stock_price FROM stock WHERE stock_id =:stock_id",nativeQuery=true)
	public double getStockPrice(@Param("stock_id")int stock_id); 
	
	@Query(value="SELECT * FROM stock WHERE stock_id =:stock_id",nativeQuery=true)
	public Stock getStockById(@Param("stock_id")int stock_id); 
	
	@Query(value="SELECT stock_symbol FROM stock WHERE stock_id =:stock_id",nativeQuery=true)
	public String getStockNameById(@Param("stock_id")int stock_id); 
	
	@Modifying
    @Transactional
	@Query(value="INSERT INTO stock(stock_price,stock_symbol,stock_total_shares) VALUES(:stock_price,:stock_symbol,:stock_total_shares)",nativeQuery=true)
	public int insertNewStock(@Param("stock_price")double stock_price, @Param("stock_symbol")String stock_symbol, @Param("stock_total_shares") double stock_total_shares);


	@Modifying
    @Transactional
	@Query(value= "Update stock SET stock_price =:stock_price WHERE stock_id =:stock_id",nativeQuery=true)
	public int updateStockPrice(@Param("stock_id")int stock_id,@Param("stock_price")double stock_price);
	
	@Modifying
    @Transactional
	@Query(value= "Update stock SET stock_total_shares =:stock_total_shares WHERE stock_id =:stock_id",nativeQuery=true)
	public int updateStockTotal(@Param("stock_id")int stock_id,@Param("stock_total_shares")double stock_total_shares);


	@Modifying
    @Transactional
	@Query(value= "Update stock SET stock_symbol =:stock_symbol WHERE stock_id =:stock_id",nativeQuery=true)
	public int updateStockSymbol(@Param("stock_id")int stock_id,@Param("stock_symbol")String stock_symbol);


	//get stocks presents in the exchange --> check bridge table
	@Query(value ="SELECT * FROM stock s INNER JOIN stock_exchange se ON s.stock_id=se.stock_id AND se.exchange_id=:exchangeId", nativeQuery=true)
	public List<Stock> findStockInExchange(@Param("exchangeId") int exchangeId);
	
	
	
}
