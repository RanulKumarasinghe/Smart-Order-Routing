package com.ab.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ab.entities.Stock;
import com.ab.entities.UserStock;

@Repository
public interface UserStockRepository extends JpaRepository<UserStock, Integer> {
	//get User Stocks
		@Query(value ="SELECT * FROM user_stock us WHERE us.user_id=:userId", nativeQuery=true)
		public List<UserStock> findUserStocks(@Param("userId") int userId);
		
	//update user stock amount on base of buy & sell
	@Transactional
	@Modifying
	@Query(value = "UPDATE user_stock us SET us.stock_amount=:stockAmount WHERE us.user_id=:userId AND us.stock_id=:stockId", nativeQuery=true)
	public int updateStockAmount(@Param("userId") int userId, @Param("stockId") int stockId, @Param("stockAmount") double stockAmount);
	
	@Query(value ="SELECT stock_amount FROM user_stock WHERE stock_id=:stockId AND user_id=:userId", nativeQuery=true)
	public double getStockAmount(@Param("userId") int userId, @Param("stockId") int stockId);
}
