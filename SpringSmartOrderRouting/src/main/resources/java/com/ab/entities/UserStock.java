package com.ab.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class UserStock {


	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	int id;
	
	 	@ManyToOne
	    @JoinColumn(name = "user_id")
	    User user;

	    @ManyToOne
	    @JoinColumn(name = "stock_id")
	    Stock stock;
	    
		private double stockAmount;

		public UserStock(int id, User user, Stock stock, double stockAmount) {
			super();
			this.id = id;
			this.user = user;
			this.stock = stock;
			this.stockAmount = stockAmount;
		}
		
		public UserStock() {}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Stock getStock() {
			return stock;
		}

		public void setStock(Stock stock) {
			this.stock = stock;
		}

		public double getStockAmount() {
			return stockAmount;
		}

		public void setStockAmount(double stockAmount) {
			this.stockAmount = stockAmount;
		}
//
//		@Override
//		public String toString() {
//			return "UserStock [id=" + id + ", user=" + user + ", stock=" + stock + ", stockAmount=" + stockAmount + "]";
//		}
//		
		
		
}
