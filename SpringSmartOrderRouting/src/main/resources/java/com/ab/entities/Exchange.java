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
@Table(name="exchange")

public class Exchange {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int exchangeId;
	private String exchangeName;
	private double feeLadder;

	@JsonIgnore
	@OneToMany(targetEntity=OrderBook.class,
			fetch=FetchType.EAGER,
			mappedBy = "exchange",
			cascade = CascadeType.ALL)
	@Fetch(value= org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<OrderBook> orderBooks = new ArrayList<>();
	
	//@JsonIgnore
	//ManyToMany
//	@ManyToMany
//	private List<Stock> stocks = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(targetEntity=StockExchange.class,
	fetch=FetchType.EAGER,
	mappedBy = "exchange",
	cascade = CascadeType.ALL)
	@Fetch(value= org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<StockExchange> stockExchanges = new ArrayList<>();; 
	
	@Enumerated(EnumType.STRING)
	private Region region;
	
	public Exchange(int exchangeId, String exchangeName, double feeLadder, List<OrderBook> orderBooks,
			List<StockExchange> stockExchanges, Region region) {
		super();
		this.exchangeId = exchangeId;
		this.exchangeName = exchangeName;
		this.feeLadder = feeLadder;
		this.orderBooks = orderBooks;
		this.stockExchanges = stockExchanges;
		this.region = region;
	}

	public Exchange() {}

	

	public List<StockExchange> getStockExchanges() {
		return stockExchanges;
	}

	public void setStockExchanges(List<StockExchange> stockExchanges) {
		this.stockExchanges = stockExchanges;
	}

	public int getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(int exchangeId) {
		this.exchangeId = exchangeId;
	}
	

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public double getFeeLadder() {
		return feeLadder;
	}

	public void setFeeLadder(double feeLadder) {
		this.feeLadder = feeLadder;
	}

	public List<OrderBook> getOrderBooks() {
		return orderBooks;
	}

	public void setOrderBooks(List<OrderBook> orderBooks) {
		this.orderBooks = orderBooks;
	}
	
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}


	private double currentDayTotalTradedValue() {
		//Revenue generated by that exchange per day
		//sum of total price of completed orders on that exchange
		return 0.0;
	}

	



	
	

}