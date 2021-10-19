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
	
	//wallet
	private int walletId;
	private double walletBalance;
	
	@OneToMany(targetEntity=Order.class,
			fetch=FetchType.EAGER,
			mappedBy = "user",
			cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<Order>();
	//Constructors
	public User(int userId, String userFirstName, String userLastName, int userAge, String userEmail,
			String userPassword, int walletId, double walletBalance) {
		super();
		this.userId = userId;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userAge = userAge;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.walletId = walletId;
		this.walletBalance = walletBalance;
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

	public int getWalletId() {
		return walletId;
	}

	public void setWalletId(int walletId) {
		this.walletId = walletId;
	}

	public double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userFirstName=" + userFirstName + ", userLastName=" + userLastName
				+ ", userAge=" + userAge + ", userEmail=" + userEmail + ", userPassword=" + userPassword + ", walletId="
				+ walletId + ", walletBalance=" + walletBalance + "]";
	}
	
	
	

	
	
}
