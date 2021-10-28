package com.ab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ab.entities.User;
import com.ab.services.UserService;

@Controller
@SessionAttributes("loggedInUser")
public class UserController {

	@Autowired
	private UserService userService;
	
	
    @GetMapping("/users/{id}")
    public User findUserByUserId(@PathVariable("id") int userId) {
		return userService.findUserByUserId(userId);
    }
    
    
    @PostMapping("/verifyUser")
    public boolean verifyUser(@RequestParam("email") String email, @RequestParam("password") String password) {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		Model model = null;
		User user = userService.verifyUser(email, hashedPassword);
		model.addAttribute("loggedInUser",user);
		if(user==null) {
			return false;
		} else {
			return true;
		}
		
    }
    
    
    @PostMapping("/addUser")
    public void insertNewUser(@RequestParam("user_first_name") String user_first_name, @RequestParam("user_last_name") String user_last_name, @RequestParam("user_email") String user_email, @RequestParam("password") String password, @RequestParam("user_age") int user_age, @RequestParam("user_balance") double user_balance, @RequestParam("userRegion") String userRegion) {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
    	userService.insertNewUser(user_first_name, user_last_name, user_email, hashedPassword, user_age, user_balance, userRegion);
    }
    
    @PostMapping("/editUser")
    public void updateUser(@RequestParam("userId") int userId, @RequestParam("user_first_name") String user_first_name, @RequestParam("user_last_name") String user_last_name, @RequestParam("user_email") String user_email, @RequestParam("password") String password, @RequestParam("user_age") int user_age, @RequestParam("wallet_balance") double wallet_balance, @RequestParam("userRegion") String userRegion) {
    	userService.updateUser(userId, user_first_name, user_last_name, user_email, password, user_age, wallet_balance, userRegion);
    }
    
    @PostMapping("/email_verification")
    public void verifyEmail(@RequestParam("userId") String email) {
    	 userService.verifyEmail(email);
    }
	
    
}
