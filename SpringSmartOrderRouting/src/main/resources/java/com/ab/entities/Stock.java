package com.ab.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="stock")
public class Stock {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int stockId;
	private String stockSymbol;
	private double stockTotalShares;
	
	@JsonIgnore
	@OneToMany(targetEntity=Order.class,
			fetch=FetchType.EAGER,
			mappedBy = "stock",
			cascade = CascadeType.ALL)
	@Fetch(value= org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<Order> orders = new ArrayList<Order>();
	
	@JsonIgnore
	@OneToMany(targetEntity=StockExchange.class,
			fetch=FetchType.EAGER,
			mappedBy = "stock",
			cascade = CascadeType.ALL)
	@Fetch(value= org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<StockExchange> stockExchanges = new ArrayList<StockExchange>();
	
	
	@JsonIgnore
	@OneToMany(targetEntity=UserStock.class,
			fetch=FetchType.EAGER,
			mappedBy = "stock",
			cascade = CascadeType.ALL)
	@Fetch(value= org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<UserStock> userStocks = new ArrayList<UserStock>(); 
	
	
	public Stock(int stockId, String stockSymbol, double stockTotalShares, List<Order> orders, List<StockExchange> stockExchanges, List<UserStock> userStocks) {
		super();
		this.stockId = stockId;
		this.stockSymbol = stockSymbol;
		this.stockTotalShares = stockTotalShares;
		this.orders = orders;
		this.stockExchanges = stockExchanges;
		this.userStocks = userStocks;
		
	}
	
	
	public Stock() {}


	public void setStockId(int stockId) {
		this.stockId = stockId;
	}


	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}


	public void setStockTotalShares(double stockTotalShares) {
		this.stockTotalShares = stockTotalShares;
	}


	public int getStockId() {
		return stockId;
	}


	public String getStockSymbol() {
		return stockSymbol;
	}


	public double getStockTotalShares() {
		return stockTotalShares;
	}


	public List<Order> getOrders() {
		return orders;
	}


	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}


	public List<StockExchange> getStockExchanges() {
		return stockExchanges;
	}


	public void setStockExchanges(List<StockExchange> stockExchanges) {
		this.stockExchanges = stockExchanges;
	}


	public List<UserStock> getUserStocks() {
		return userStocks;
	}


	public void setUserStocks(List<UserStock> userStocks) {
		this.userStocks = userStocks;
	}
	
	

//
//	@Override
//	public String toString() {
//		return "Stock [stockId=" + stockId + ", stockSymbol=" + stockSymbol + ", stockTotalShares=" + stockTotalShares
//				+ ", orders=" + orders + ", stockExchanges=" + stockExchanges + "]";
//	}
//	
	

	
}
