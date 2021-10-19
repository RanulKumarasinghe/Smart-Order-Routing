package com.ab.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ab.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Modifying
    @Transactional
	@Query(value="INSERT INTO user(user_first_name,user_last_name,user_email,user_password,user_age,wallet_balance,wallet_id) VALUES(:user_first_name, :user_last_name, :user_email, :password, :user_age, :wallet_balance, :wallet_id)",nativeQuery=true)
	public int insertNewUser(@Param("user_first_name")String user_first_name, @Param("user_last_name")String user_last_name, @Param("user_email") String user_email, @Param("password") String password, @Param("user_age") int user_age, @Param("wallet_balance") double wallet_balance, @Param("wallet_id") int wallet_id);

}
