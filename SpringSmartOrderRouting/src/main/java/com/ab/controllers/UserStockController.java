package com.ab.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ab.entities.UserStock;
import com.ab.services.UserStockService;

@RestController
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
	
	public  double getStockAmount(int userId, int stockId){
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
}
