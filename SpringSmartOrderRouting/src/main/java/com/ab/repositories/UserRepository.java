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
	
	public User findUserByUserId(int userId);
	
	
	@Modifying
    @Transactional
	@Query(value="INSERT INTO user(user_first_name,user_last_name,user_email,user_password,user_age,wallet_balance,wallet_id) VALUES(:user_first_name, :user_last_name, :user_email, :password, :user_age, :wallet_balance, :wallet_id)",nativeQuery=true)
	public int insertNewUser(@Param("user_first_name")String user_first_name, @Param("user_last_name")String user_last_name, @Param("user_email") String user_email, @Param("password") String password, @Param("user_age") int user_age, @Param("wallet_balance") double wallet_balance, @Param("wallet_id") int wallet_id);

	@Query(value="SELECT * from user WHERE user_email =:email AND user_password =:password",nativeQuery=true)
	public User verifyUser(@Param("email")String email, @Param("password") String password);
	
	@Modifying
    @Transactional
	@Query(value= "Update user SET user_first_name =:user_first_name, user_last_name =:user_last_name, user_email =:user_email, password =:password, user_age =:user_age, wallet_balance =:wallet_balance, wallet_id =:wallet_id  WHERE user_id =:userId",nativeQuery=true)
	public int updateUser(@Param("userId")int userId, @Param("user_first_name")String user_first_name, @Param("user_last_name")String user_last_name, @Param("user_email") String user_email, @Param("password") String password, @Param("user_age") int user_age, @Param("wallet_balance") double wallet_balance, @Param("wallet_id") int wallet_id);

	@Query(value="SELECT user_email from user WHERE user_email =:email",nativeQuery=true)
	public String verifyEmail(@Param("email")String email);
}
