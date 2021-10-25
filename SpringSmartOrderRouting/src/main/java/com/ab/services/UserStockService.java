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
		return userStockRepository.findUserStocks(userId);

	}

	public  int updateStockAmount(int userId, int stockId, double stockAmount){
		return userStockRepository.updateStockAmount(userId, stockId, stockAmount);
	}
	
	public  double getStockAmount(int userId, int stockId){
		return userStockRepository.getStockAmount(userId, stockId);
	}
}
