package com.ab.entities;

public class Stock {
	
	private int stockId;
	private String stockSymbol;
	private double stockPrice;
	private double stockTotalShares;
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
