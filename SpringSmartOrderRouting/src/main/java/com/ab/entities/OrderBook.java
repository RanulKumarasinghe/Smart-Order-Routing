package com.ab.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="orderbook")
public class OrderBook {
	
	//Attributes
		@Id
		@GeneratedValue(strategy= GenerationType.AUTO)
		private int orderBookId;
	
		@JsonIgnore
		@OneToMany(targetEntity=Order.class,
				fetch=FetchType.EAGER,
				mappedBy = "orderbook",
				cascade = CascadeType.ALL)
		public List<Order> orders = new ArrayList<Order>();
		
		
		@ManyToOne(targetEntity= Exchange.class)
		@JoinColumn(name="exchange_id")
		private Exchange exchange;
		
	
		
		//Constructors
		public OrderBook(int orderBookId, List<Order> orders) {
			super();
			this.orderBookId = orderBookId;
			this.orders = orders;
		}
		
		public OrderBook() {}

		public int getOrderBookId() {
			return orderBookId;
		}

		public void setOrderBookId(int orderBookId) {
			this.orderBookId = orderBookId;
		}

		public List<Order> getOrders() {
			return orders;
		}

		public void setOrders(List<Order> orders) {
			this.orders = orders;
		}
//
//		@Override
//		public String toString() {
//			return "OrderBook [orderBookId=" + orderBookId + ", orders=" + orders + "]";
//		}
//		
		
		
		
		

}
