package com.ab.services;

import org.springframework.security.core.Authentication;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public int insertNewUser(String user_first_name, String user_last_name, String user_email, String password, int user_age, double wallet_balance, String userRegion) {
		return userRepository.insertNewUser(user_first_name, user_last_name, user_email, password, user_age, wallet_balance, userRegion);
	}
	
	public User verifyUser(String email, String password) {
		
		return userRepository.verifyUser(email, password);
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

	public Optional<User> findUserByUserEmail(String userEmail) {
		
		return userRepository.findUserByUserEmail(userEmail);
	}

}
