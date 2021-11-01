package com.ab.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user")
public class User {

	//Attributes
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int userId;
	private String userFirstName;
	private String userLastName;
	private int userAge;
	private String userEmail;
	private String userPassword;
	
	@Enumerated(EnumType.STRING)
	private Region userRegion;
	
	//wallet
	private double userBalance;
	
	@JsonIgnore
	@OneToMany(targetEntity=Order.class,
			fetch=FetchType.EAGER,
			mappedBy = "user",
			cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<Order>();
	
	@JsonIgnore
	@OneToMany(targetEntity=UserStock.class,
			fetch=FetchType.EAGER,
			mappedBy = "user",
			cascade = CascadeType.ALL)
	@Fetch(value= org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<UserStock> userStocks = new ArrayList<UserStock>(); 
	
	
//	@JsonIgnore
//	//ManyToMany
//	@ManyToMany
//	private List<Stock> stocks = new ArrayList<>();
	
	
	//Constructors
	public User(int userId, String userFirstName, String userLastName, int userAge, String userEmail,
			String userPassword, double userBalance, List<UserStock> userStocks,Region userRegion) {
		super();
		this.userId = userId;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userAge = userAge;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userBalance = userBalance;
		this.userStocks = userStocks;
		this.userRegion = userRegion;
	}
	
	public User() {}
	
	//Methods
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public double getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(double userBalance) {
		this.userBalance = userBalance;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
	public List<UserStock> getUserStocks() {
		return userStocks;
	}

	public void setUserStocks(List<UserStock> userStocks) {
		this.userStocks = userStocks;
	}
	

	public Region getUserRegion() {
		return userRegion;
	}

	public void setUserRegion(Region userRegion) {
		this.userRegion = userRegion;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userFirstName=" + userFirstName + ", userLastName=" + userLastName
				+ ", userAge=" + userAge + ", userEmail=" + userEmail + ", userPassword=" + userPassword
				+ ", userRegion=" + userRegion + ", userBalance=" + userBalance + ", orders=" + orders + ", userStocks="
				+ userStocks + "]";
	}

	

	

	
	
	
	

	
	
}
