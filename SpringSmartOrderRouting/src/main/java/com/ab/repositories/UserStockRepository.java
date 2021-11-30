package com.ab.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ab.entities.UserStock;

@Repository
public interface UserStockRepository extends JpaRepository<UserStock, Integer> {
	//get User Stocks
	@Query(value = "SELECT * FROM user_stock us WHERE us.user_id=:user_Id", nativeQuery=true)
	public List<UserStock> findUserStocksByUserId(@Param("user_Id") int userId);
		
	//update user stock amount on base of buy & sell
	@Transactional
	@Modifying
	@Query(value = "UPDATE user_stock us SET us.stock_amount=:stockAmount WHERE us.user_id=:userId AND us.stock_id=:stockId", nativeQuery=true)
	public int updateStockAmount(@Param("userId") int userId, @Param("stockId") int stockId, @Param("stockAmount") double stockAmount);
	
	@Query(value ="SELECT stock_amount FROM user_stock WHERE user_id=:userId AND stock_id=:stockId", nativeQuery=true)
	public String getStockAmount(@Param("userId") int userId, @Param("stockId") int stockId);
	
	@Transactional
	@Modifying
	@Query(value="INSERT INTO user_stock(stock_amount,stock_id,user_id) VALUES(:stockAmount, :stockId, :userId)", nativeQuery=true)
	public int addUserStock(@Param("userId") int userId, @Param("stockId") int stockId, @Param("stockAmount") double stockAmount);
	
	//get user stock by userid and stock id 
	@Query(value ="SELECT * FROM user_stock us WHERE us.user_id=:userId AND  us.stock_id=:stockId", nativeQuery=true)
	public UserStock findUserStock(@Param("userId") int userId,@Param("stockId") int stockId);

}
