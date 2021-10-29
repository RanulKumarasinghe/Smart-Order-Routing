package com.ab.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

import com.ab.dto.DashBoardDto;
import com.ab.entities.Exchange;
import com.ab.entities.Stock;
import com.ab.entities.User;
import com.ab.services.ExchangeService;
import com.ab.services.StockExchangeService;
import com.ab.services.StockService;
import com.ab.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@SessionAttributes({"loggedInUser","stocks"})
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private ExchangeService exchangeService;
	
	@Autowired
	private StockExchangeService stockExchangeService;
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
  
    
    @PostMapping("/addUser")
    public void insertNewUser(@RequestParam("user_first_name") String user_first_name, @RequestParam("user_last_name") String user_last_name, @RequestParam("user_email") String user_email, @RequestParam("password") String password, @RequestParam("user_age") int user_age, @RequestParam("user_balance") double user_balance, @RequestParam("userRegion") String userRegion) {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		int user_id = userService.findMaxUserId()+1;
    	userService.insertNewUser(user_id,user_first_name, user_last_name, user_email, hashedPassword, user_age, user_balance, userRegion);
    }
    
    @PostMapping("/editUser")
    public void updateUser(@RequestParam("userId") int userId, @RequestParam("user_first_name") String user_first_name, @RequestParam("user_last_name") String user_last_name, @RequestParam("user_email") String user_email, @RequestParam("password") String password, @RequestParam("user_age") int user_age, @RequestParam("wallet_balance") double wallet_balance, @RequestParam("userRegion") String userRegion) {
    	userService.updateUser(userId, user_first_name, user_last_name, user_email, password, user_age, wallet_balance, userRegion);
    }
    
    @PostMapping("/email_verification")
    public void verifyEmail(@RequestParam("userId") String email) {
    	 userService.verifyEmail(email);
    }
    
 
    @GetMapping("/login")
	public String login() {
		return "login";
	}
    
    
	@RequestMapping(method = RequestMethod.POST, value="/login")
	public ModelAndView userLogin(@RequestParam (value = "email") String email,
	@RequestParam (value = "password") String password) {

		ModelAndView mv = new ModelAndView();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		System.out.println(hashedPassword);
		User u = userService.verifyUser(email, hashedPassword);
		//user_email = u.getUserEmail();
		//System.out.println(user_email);
		
		if(Objects.isNull(u)) {
			mv.setViewName("errorPage");
			return mv;

		}
		
		else {
			User user = userService.findUserByUserEmail(user_email);
			List<Stock> getAllStocks = stockService.getAllStocks();
			List<Exchange> getAllExchanges = exchangeService.getAllExchanges();
			List<String> getFirstAvailabilty = new ArrayList<>();
			List<String> getSecondAvailabilty = new ArrayList<>();
			List<String> getThirdAvailabilty = new ArrayList<>();
			List<DashBoardDto> dashBoardStocks = new ArrayList<>();
			for (Stock stock : getAllStocks)  {
				String firstExchange = stockExchangeService.getAvailability(stock.getStockId(), 0);
				String secondExchange = stockExchangeService.getAvailability(stock.getStockId(), 1);
				String thirdExchange = stockExchangeService.getAvailability(stock.getStockId(), 2);
				DashBoardDto dashBoardStock = new DashBoardDto(stock.getStockId(),stock.getStockSymbol(),firstExchange,secondExchange,thirdExchange);
				dashBoardStocks.add(dashBoardStock);
			}
			

			mv.addObject("loggedInUser", user);
			mv.addObject("exchanges", getAllExchanges);
			mv.addObject("stocks", dashBoardStocks);
			mv.setViewName("dashboard");
			//load stuff on dash board here
			//mv.addObject("savingsAccountDetails",savingsAccount);
			return mv;
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/register")
    public String loadRegisterView() {
		System.out.println("...register");
		return "register";
    }
    
	@RequestMapping(method = RequestMethod.POST, value="/register")
    public ModelAndView registerUser(
    		@RequestParam(value="userFirstName") String userFirstName,
    		@RequestParam(value="userLastName") String userLastName,
    		@RequestParam(value="userEmail") String userEmail, 
    		@RequestParam(value="userAge") String userAge, 
    		@RequestParam(value="userPassword") String userPassword, 
    		@RequestParam(value="userRepassword") String userRepassword, 
    		@RequestParam(value="userBalance") double userBalance,
    		@RequestParam(value="userRegion") String userRegion) {
		
		int age = Integer.parseInt(userAge);
		int userId = userService.findMaxUserId()+1;
		
    	System.out.println("entered in register controller");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(userPassword);
		ModelAndView mv = new ModelAndView();
		System.out.println(userService.findUserByUserEmail(userEmail));
		if(userService.findUserByUserEmail(userEmail) == null ) {
			
			userService.insertNewUser(userId,userFirstName, userLastName, userEmail, hashedPassword, age, userBalance, userRegion);
			System.out.println("Registered");
			mv.setViewName("login");
		}
		else {
			mv.setViewName("register");
			System.out.println("Error!");
		}
		return mv;	 
    }
	
	
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
