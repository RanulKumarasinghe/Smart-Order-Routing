package com.ab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ab.entities.User;
import com.ab.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	
    @GetMapping("/users")
    public User findUserByUserId(int userId) {
		return userService.findUserByUserId(userId);
    }
    @PostMapping("/verifyUser")
    public void verifyUser(String email, String password) {
    	userService.verifyUser(email, password);
    }
    
    
    @PostMapping("/addUser")
    public void insertNewUser(String user_first_name, String user_last_name, String user_email, String password, int user_age, double wallet_balance, int wallet_id) {
    	userService.insertNewUser(user_first_name, user_last_name, user_email, password, user_age, wallet_balance, wallet_id);
    }
    
    @PostMapping("/editUser")
    public void updateUser(int userId, String user_first_name, String user_last_name, String user_email, String password, int user_age, double wallet_balance, int wallet_id) {
    	userService.updateUser(userId, user_first_name, user_last_name, user_email, password, user_age, wallet_balance, wallet_id);
    }
    
    @PostMapping("/email_verification")
    public void verifyEmail(String email) {
    	 userService.verifyEmail(email);
    }
	
    
}
