package com.example.demo.models;

public class PhoneNumber {
	
	private String number;
	private Boolean isActive;
	
	public PhoneNumber(String number, Boolean isActive) {
		super();
		this.number = number;
		this.isActive = isActive;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
