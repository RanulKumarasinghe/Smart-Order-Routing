package com.ab.controllers;

import java.security.MessageDigest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

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
import com.ab.entities.Order;
import com.ab.entities.Stock;
import com.ab.entities.User;
import com.ab.services.ExchangeService;
import com.ab.services.OrderService;
import com.ab.services.StockExchangeService;
import com.ab.services.StockService;
import com.ab.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@SessionAttributes({"loggedInUser","stocks","exchanges"})
@Controller
 public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private ExchangeService exchangeService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private StockExchangeService stockExchangeService;
	private String user_email;
	String hashedPassword;
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
	public ModelAndView userLogin(@RequestParam (value = "userEmail") String userEmail,
	@RequestParam (value = "userPassword") String userPassword) {

		ModelAndView mv = new ModelAndView();
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//String hashedPassword = passwordEncoder.encode(password);
		//System.out.println(hashedPassword);
		try {
			hashedPassword = encrypt(userPassword,userPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User u = userService.verifyUser(userEmail, hashedPassword);
		user_email = u.getUserEmail();
		if(Objects.isNull("User after login-->"+u)) {
			mv.setViewName("errorPage");
			return mv;

		}
		
		else {
			User user = userService.findUserByUserEmail(user_email);
			
			List<Stock> getAllStocks = stockService.getAllStocks();
			List<Exchange> getAllExchanges = exchangeService.getAllExchanges();
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
		
			return mv;
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/register")
    public String loadRegisterView() {
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
		
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//String hashedPassword = passwordEncoder.encode(userPassword);
		try {
			hashedPassword = encrypt(userPassword,userPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView();
		if(userService.findUserByUserEmail(userEmail) == null ) {
			
			userService.insertNewUser(userId,userFirstName, userLastName, userEmail, hashedPassword, age, userBalance, userRegion);
			mv.setViewName("login");
		}
		else {
			mv.setViewName("register");
		}
		return mv;	 
    }
	
	@RequestMapping(method = RequestMethod.GET, value="/dashboard")
    public String dashboard() {
		return "dashboard";
    }

	@RequestMapping(method = RequestMethod.GET, value="/history")
    public ModelAndView tradingHistory(@ModelAttribute("loggedInUser") User u) {
		List<Order> tradeHistory = orderService.getUserTradeHistory(u.getUserId());
		ModelAndView mv = new ModelAndView();
		mv.addObject("trades",tradeHistory);
		mv.setViewName("history");
		
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
	  
	  
	  public SecretKeySpec generateKey(String password) throws Exception {
	        //Using MessageDigest class to get instance of SHA-256 Algorithm to generate Key
	        final MessageDigest digest = MessageDigest.getInstance("MD5");
	        //Converting Password into Byte Array with UTF-8 encoding
	        byte[] bytes = password.getBytes("UTF-8");
	        //Updating digest using specified array of bytes starting with the specified offset
	        digest.update(bytes, 0, bytes.length);

	        byte[] key = digest.digest();
	        //Completing hash computation by performing final operations such as padding
	        //building secret key from the given byte array
	        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
	        //Returning secret key
	        return secretKeySpec;

	    }

	    public String encrypt(String data, String password) throws Exception {
	        SecretKeySpec key = generateKey(password);
	        //Creating Cipher instance from Cipher class with Advance Encryption Standard Algorithm
	        Cipher c = Cipher.getInstance("AES");
	        //Initializing the Cipher with
	        c.init(Cipher.ENCRYPT_MODE, key);
	        byte[] encVal = c.doFinal(data.getBytes());
	        String encryptedValue = Base64.getEncoder().encodeToString(encVal);
	        return encryptedValue;
	    }
	
    
}
