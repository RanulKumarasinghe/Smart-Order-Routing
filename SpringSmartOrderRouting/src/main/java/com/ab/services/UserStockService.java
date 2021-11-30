package com.ab.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.ab.entities.Stock;
import com.ab.entities.UserStock;
import com.ab.repositories.UserStockRepository;

@Service
public class UserStockService {
	
	@Autowired
	private UserStockRepository userStockRepository;

	public List<UserStock> findUserStocks(int userId){
		return userStockRepository.findUserStocksByUserId(userId);
	}
	public  int updateStockAmount(int userId, int stockId, double stockAmount){
		return userStockRepository.updateStockAmount(userId, stockId, stockAmount);
	}
	
	public UserStock findUserStock(int userId,int stockId) {
		return userStockRepository.findUserStock(userId, stockId);
	}
	
	public double getStockAmount(int userId, int stockId){
		 String amount = userStockRepository.getStockAmount(userId, stockId);
		 boolean numeric = isNumeric(amount);
		 double stockAmount = 0.0;
		if (!numeric) {
			stockAmount = 0.0;
		} else {
			stockAmount = Double.parseDouble(amount);
		}
		return stockAmount;
	}
	
	private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }
	
	public int addUserStock(int userId,int stockId, double stockAmount) {
		return userStockRepository.addUserStock(userId, stockId, stockAmount);
	}

}
