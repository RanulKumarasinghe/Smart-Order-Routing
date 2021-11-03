package com.ab.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ab.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query(value="SELECT * FROM user WHERE user_email=:userEmail", nativeQuery=true)
	User findUserByUserEmail(@Param("userEmail") String userEmail);

	@Query(value="SELECT * FROM user WHERE user_email=:userEmail", nativeQuery=true)
	public int getUserByUserEmail(@Param("userEmail") String userEmail);

	public User findUserByUserId(int userId);
	
	@Query(value="SELECT MAX(user_id) FROM user", nativeQuery=true)
	public String findMaxUserId();
	
	@Query(value="SELECT user_balance FROM user WHERE user_id =:userId", nativeQuery=true)
	public double findUserBalance(@Param("userId")int userId);
	
	
	@Modifying
    @Transactional
	//@Query(value="INSERT INTO user(user_first_name,user_last_name,user_email,user_password,user_age,user_balance,user_region) VALUES(:user_first_name, :user_last_name, :user_email, :password, :user_age, :user_balance, :user_region)",nativeQuery=true)
	@Query(value="INSERT INTO user(user_id,user_age, user_email,user_first_name,user_last_name,user_password,user_balance,user_region) VALUES(:user_id,:user_age,:user_email,:user_first_name, :user_last_name, :password, :user_balance, :user_region)",nativeQuery=true)
    public int insertNewUser(@Param("user_id")int user_id,@Param("user_first_name")String user_first_name, @Param("user_last_name")String user_last_name, @Param("user_email") String user_email, @Param("password") String password, @Param("user_age") int user_age, @Param("user_balance") double user_balance, @Param("user_region") String user_region);

	@Query(value="SELECT * from user WHERE user_email =:email AND user_password =:password",nativeQuery=true)
	public User verifyUser(@Param("email")String email, @Param("password") String password);
	
	@Modifying
    @Transactional
	@Query(value= "Update user SET user_first_name =:user_first_name, user_last_name =:user_last_name, user_email =:user_email, password =:password, user_age =:user_age, user_balance =:user_balance, user_region =:user_region  WHERE user_id =:userId",nativeQuery=true)
	public int updateUser(@Param("userId")int userId, @Param("user_first_name")String user_first_name, @Param("user_last_name")String user_last_name, @Param("user_email") String user_email, @Param("password") String password, @Param("user_age") int user_age, @Param("user_balance") double user_balance, @Param("user_region") String user_region);

	@Modifying
    @Transactional
    @Query(value= "Update user SET user_balance =:user_balance WHERE user_id =:userId", nativeQuery=true)
	public int updateUserBalance(@Param("userId")int userId, @Param("user_balance") double user_balance);
	
	@Query(value="SELECT user_email from user WHERE user_email =:email",nativeQuery=true)
	public String verifyEmail(@Param("email")String email);
	
}
