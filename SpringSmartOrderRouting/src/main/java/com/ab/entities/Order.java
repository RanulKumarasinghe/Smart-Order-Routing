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

@Entity
@Table(name="order")
public class Order {

	//Attributes
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int orderId;
	private String orderType;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	private LocalDateTime orderDate;
	private double orderAmount;
	@ManyToOne(targetEntity= Stock.class)
	@JoinColumn(name="stock_id")
	private Stock stock;
	
	@ManyToOne(targetEntity= User.class)
	@JoinColumn(name="user_id")
	private User user;
	
	
	//Constructors
	public Order(int orderId, String orderType, OrderStatus orderStatus, LocalDateTime orderDate, double orderAmount,
		 Stock stock, User user) {
		super();
		this.orderId = orderId;
		this.orderType = orderType;
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
		this.orderAmount = orderAmount;
		this.stock = stock;
		this.user = user;
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

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
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

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderType=" + orderType + ", orderStatus=" + orderStatus
				+ ", orderDate=" + orderDate + ", orderAmount=" + orderAmount + ", stock=" + stock +", user=" + user
				+ "]";
	}
	
	
	
	
	
	
	
	
	
}
