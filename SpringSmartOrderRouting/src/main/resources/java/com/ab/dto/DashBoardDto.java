package com.ab.dto;

public class DashBoardDto {
	private int StockId;
	
	private String StockName;
	
	private String availableFirst;
	
	private String availableSecond;
	
	private String availableThird;

	public DashBoardDto() {}
	public DashBoardDto(int stockId, String stockName, String availableFirst, String availableSecond,
			String availableThird) {
		super();
		StockId = stockId;
		StockName = stockName;
		this.availableFirst = availableFirst;
		this.availableSecond = availableSecond;
		this.availableThird = availableThird;
	}

	public int getStockId() {
		return StockId;
	}

	public void setStockId(int stockId) {
		StockId = stockId;
	}

	public String getStockName() {
		return StockName;
	}

	public void setStockName(String stockName) {
		StockName = stockName;
	}

	public String getAvailableFirst() {
		return availableFirst;
	}

	public void setAvailableFirst(String availableFirst) {
		this.availableFirst = availableFirst;
	}

	public String getAvailableSecond() {
		return availableSecond;
	}

	public void setAvailableSecond(String availableSecond) {
		this.availableSecond = availableSecond;
	}

	public String getAvailableThird() {
		return availableThird;
	}

	public void setAvailableThird(String availableThird) {
		this.availableThird = availableThird;
	}
	
	
	
}
