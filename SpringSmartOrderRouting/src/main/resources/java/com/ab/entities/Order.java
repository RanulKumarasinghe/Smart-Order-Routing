package com.ab.entities;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="orders")
public class Order {

	//Attributes
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int orderId;
	private String orderType;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	private LocalDateTime orderDate;
	private double orderStockAmount;
	private double orderTotalPrice;
	
	//FK1
	@JsonIgnore
	@ManyToOne(targetEntity= Stock.class)
	@JoinColumn(name="stock_id")
	private Stock stock;
	
	//FK2
	@JsonIgnore
	@ManyToOne(targetEntity= User.class)
	@JoinColumn(name="user_id")
	private User user;
	//FK3
	@ManyToOne(targetEntity= OrderBook.class)
	@JoinColumn(name="orderbook_id")
	private OrderBook orderbook;
	
	
	
	//Constructors
	public Order(int orderId, String orderType, OrderStatus orderStatus, LocalDateTime orderDate, double orderStockAmount, double orderTotalPrice,
		 Stock stock, User user,OrderBook orderbook) {
		super();
		this.orderId = orderId;
		this.orderType = orderType;
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
		this.orderStockAmount = orderStockAmount;
		this.orderTotalPrice = orderTotalPrice;
		this.stock = stock;
		this.user = user;
		this.orderbook = orderbook;
	}
	
	public Order() {}

	//Methods
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}


	public double getOrderStockAmount() {
		return orderStockAmount;
	}

	public void setOrderStockAmount(double orderStockAmount) {
		this.orderStockAmount = orderStockAmount;
	}

	public double getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(double orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OrderBook getOrderbook() {
		return orderbook;
	}

	public void setOrderbook(OrderBook orderbook) {
		this.orderbook = orderbook;
	}
	
	
//
//	@Override
//	public String toString() {
//		return "Order [orderId=" + orderId + ", orderType=" + orderType + ", orderStatus=" + orderStatus
//				+ ", orderDate=" + orderDate + ", orderStockAmount=" + orderStockAmount + ", orderTotalPrice="
//				+ orderTotalPrice + ", stock=" + stock + ", user=" + user + "]";
//	}

	
	
	
	
	
	
	
	
}
