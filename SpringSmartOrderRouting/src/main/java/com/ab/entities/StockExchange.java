package com.ab.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StockExchange {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	int id;
	
	 	@ManyToOne
	    @JoinColumn(name = "stock_id")
	    Stock stock;

	    @ManyToOne
	    @JoinColumn(name = "exchange_id")
	    Exchange exchange;
	    
		private double totalPrice;
		//keep record of available shares
		private double availableShares; //sell orders available on that exchange for 

		public StockExchange(int id, Stock stock, Exchange exchange, double totalPrice, double availableShares) {
			super();
			this.id = id;
			this.stock = stock;
			this.exchange = exchange;
			this.totalPrice = totalPrice;
			this.availableShares = availableShares;
			
		}

		public StockExchange() {}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Stock getStock() {
			return stock;
		}

		public void setStock(Stock stock) {
			this.stock = stock;
		}

		public Exchange getExchange() {
			return exchange;
		}

		public void setExchange(Exchange exchange) {
			this.exchange = exchange;
		}

		

		public double getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(double totalPrice) {
			this.totalPrice = totalPrice;
		}

		public double getAvailableShares() {
			return availableShares;
		}

		public void setAvailableShares(double availableShares) {
			this.availableShares = availableShares;
		}

		@Override
		public String toString() {
			return "StockExchange [id=" + id + ", stock=" + stock + ", exchange=" + exchange + ", totalPrice="
					+ totalPrice + ", availableShares=" + availableShares + "]";
		}

		
		
		
		
		
	

}
