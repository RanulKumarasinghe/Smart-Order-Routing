package com.ab.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ab.entities.User;
import com.ab.entities.UserStock;
import com.ab.services.UserStockService;
@SessionAttributes({"loggedInUser","stocks","exchanges","userStocks"})
@Controller
public class UserStockController {

	@Autowired
	private UserStockService userStockService;
	
	int userId;
	int stockId;
	double stockAmount;
	double amount;

	@GetMapping("/userstocks/{userId}")
	public List<UserStock> getUserStocks(@PathVariable("userId") int userId){
		
		return userStockService.findUserStocks(userId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/wallet")
	public ModelAndView loadWallet(@ModelAttribute("loggedInUser") User u) {
		userId = u.getUserId();
		List<UserStock> userStocks = getUserStocks(userId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("userStocks",userStocks);
		System.out.println(userStocks);
		mv.setViewName("wallet");
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/sellOrder/{userStockId}/userStock")
	public ModelAndView getUserStock(@PathVariable ("userStockId") int userStockId, @ModelAttribute("loggedInUser") User u) {
		userId = u.getUserId();
		UserStock userStock = userStockService.findUserStock(userId, userStockId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("userStock",userStock);
		mv.setViewName("sellOrder");
		System.out.println("...sellorder");
		return mv;
		
	} 
	
	
	@GetMapping("/userstocksinwallet/{userId}/{stockId}")
	public  double getStockAmount(@PathVariable("userId") int userId, @PathVariable("stockId") int stockId){
		return userStockService.getStockAmount(userId, stockId);
	}
	
	@PostMapping("/updatestockamountonbuy")
	public int updateStockAmountOnBuy(@RequestParam("userId") int userId, @RequestParam("stockId") int stockId,  @RequestParam("buyAmount") double buyAmount) {
		stockAmount = getStockAmount(userId,stockId);
		amount = stockAmount+buyAmount;
		return userStockService.updateStockAmount(userId,stockId,amount);
	}
	@PostMapping("/updatestockamountonsell")
	public int updateStockAmountOnSell(@RequestParam("userId") int userId, @RequestParam("stockId") int stockId,  @RequestParam("sellAmount") double sellAmount) {
		stockAmount = getStockAmount(userId,stockId);
		amount = stockAmount-sellAmount;
		return userStockService.updateStockAmount(userId,stockId,amount);
	}
	
	@PostMapping("/updatewalletonbuy")
	public int updateUserWalletOnBuy(@RequestParam("userId") int userId, @RequestParam("stockId") int stockId, @RequestParam("stockAmount") double stockAmount) {
		double amount = userStockService.getStockAmount(userId, stockId);
		
		System.out.println(amount);
			if(amount==0.0) {
				return userStockService.addUserStock(userId, stockId, stockAmount);
				
			}else{
				return userStockService.updateStockAmount(userId, stockId, amount + stockAmount);

			}	
	}
	
	@PostMapping("/updatewalletonsell")
	public int updateUserWalletOnSell(@RequestParam("userId") int userId, @RequestParam("stockId") int stockId, @RequestParam("stockAmount") double stockAmount) {
		double amount = userStockService.getStockAmount(userId, stockId);
		//if stock not present in wallet then return -1
		if(amount <=0) {
			return -1;
		}
		//update wallet
		return userStockService.updateStockAmount(userId, stockId, amount - stockAmount);

	}
	
}
