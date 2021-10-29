package com.ab.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import com.ab.entities.User;
import com.ab.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@SessionAttributes("loggedInUser")
public class UserController {

	@Autowired
	private UserService userService;
	
	private String user_email;
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
    
	@RequestMapping(method = RequestMethod.GET, value="/register")
    public String loadRegisterView() {
		System.out.println("...register");
		return "register";
    }
    
    @PostMapping("/@{/register}")
    public boolean registerUser(@RequestParam("userFirstName") String userFirstName,@RequestParam("userLastName") String userLastName,@RequestParam("userEmail") String userEmail, @RequestParam("userAge") int userAge, @RequestParam("password") String password, @RequestParam("repassword") String repassword,  @RequestParam("walletBalance") double walletBalance, @RequestParam("userRegion") String userRegion) {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		
		if(userService.validateEmail(userEmail)!=-1) {
			userService.insertNewUser(userFirstName, userLastName, userEmail, password, userAge, walletBalance, userRegion);
			return true;
		}
		else {
			return false;
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
    
   
    
    
    @GetMapping("/dashboard")
    public String authenticated(Model model) {
    	model.addAttribute("user", getPrincipal());
		return "/dashboard";
    }
    
   
    @GetMapping("/login")
	public String login() {
		User user = getPrincipal();
		
		if(user != null) {
			System.out.println("...dashboard");
			return "dashboard";
		}
		
		System.out.println("...login");
		return "login";
	}
    
    private User getPrincipal() {
    	User user = null;
    	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
    		user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	}
    	return user;
    }
    
    
	@RequestMapping(method = RequestMethod.POST, value="/login")
	public ModelAndView userLogin(@RequestParam (value = "email") String email,
	@RequestParam (value = "password") String password) {

		ModelAndView mv = new ModelAndView();
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//String hashedPassword = passwordEncoder.encode(password);
		User u = userService.verifyUser(email, password);
		user_email = u.getUserEmail();
		System.out.println(user_email);
		if(u == null){
			mv.setViewName("errorPage");
			return mv;

		}
		else {
			User user = userService.findUserByUserEmail(user_email);
			System.out.println(user);
			mv.addObject("loggedInUser", user);
			mv.setViewName("dashboard");
			//load stuff on dash board here
			//mv.addObject("savingsAccountDetails",savingsAccount);
			return mv;
		}
	}
	
//	@Controller
//	public class SecurityController {
//
//	    @RequestMapping(value = "/email", method = RequestMethod.GET)
//	    @ResponseBody
//	    public String currentUserName(Principal principal) {
//	        return principal.getName();
//	    }
//	}
	
	@RequestMapping(method = RequestMethod.GET, value="/logout")
	  public String logout(SessionStatus status) {
	    status.setComplete();
	    return "login";
	  }
	  
	  @ModelAttribute("loggedInUser")
	  public User user(){
	    return new User();
	  } 
	
    
}
