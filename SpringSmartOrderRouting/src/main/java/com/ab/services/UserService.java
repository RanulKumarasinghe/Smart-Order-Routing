package com.ab.services;

import org.springframework.security.core.Authentication;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;


import com.ab.entities.User;
import com.ab.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public User findUserByUserId(int userId) {
		return userRepository.findUserByUserId(userId);
	}
	
	public int insertNewUser(int user_id, String user_first_name, String user_last_name, String user_email, String password, int user_age, double wallet_balance, String userRegion) {
		return userRepository.insertNewUser(user_id,user_first_name, user_last_name, user_email, password, user_age, wallet_balance, userRegion);
	}
	
	public User verifyUser(String  user_email, String password) {
		
		return userRepository.verifyUser( user_email, password);
	}
	

	public int validateEmail(String  user_email) {
		return userRepository.getUserByUserEmail(user_email);
	}
	
	public int getUserId() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getUserId();
   
    }
	
	public String getUserEmail() {
	       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        User user = (User) authentication.getPrincipal();
	        return user.getUserEmail();
	        
	    }
	
	public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }
	
	
	public int updateUser(int userId, String user_first_name, String user_last_name, String user_email, String password, int user_age, double wallet_balance, String userRegion) {
		return userRepository.updateUser(userId, user_first_name, user_last_name, user_email, password, user_age, wallet_balance, userRegion);
	}
	
	public String verifyEmail(String email) {
		return userRepository.verifyEmail(email);
	}

	public User findUserByUserEmail(String userEmail) {
		
		return userRepository.findUserByUserEmail(userEmail);
	}
	
	public int findMaxUserId() {
		return userRepository.findMaxUserId();
	}
	
	public double findUserBalance(int userId) {
		return userRepository.findUserBalance(userId);
	}

}
