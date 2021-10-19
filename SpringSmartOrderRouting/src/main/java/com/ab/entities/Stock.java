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

@Entity
@Table(name="stock")
public class Stock {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int stockId;
	private String stockSymbol;
	private double stockPrice;
	private double stockTotalShares;
	
	@OneToMany(targetEntity=Order.class,
			fetch=FetchType.EAGER,
			mappedBy = "stock",
			cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<Order>();
	
	public Stock(int stockId, String stockSymbol, double stockPrice, double stockTotalShares) {
		super();
		this.stockId = stockId;
		this.stockSymbol = stockSymbol;
		this.stockPrice = stockPrice;
		this.stockTotalShares = stockTotalShares;
	}
	
	
	public Stock() {}


	public void setStockId(int stockId) {
		this.stockId = stockId;
	}


	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}


	public void setStockPrice(double stockPrice) {
		this.stockPrice = stockPrice;
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


	public double getStockPrice() {
		return stockPrice;
	}


	public double getStockTotalShares() {
		return stockTotalShares;
	}


	@Override
	public String toString() {
		return "Stock [stockId=" + stockId + ", stockSymbol=" + stockSymbol + ", stockPrice=" + stockPrice
				+ ", stockTotalShares=" + stockTotalShares + "]";
	}
	
	
}
