package com.example.demo.models;

import java.util.List;

public class Customer {
	
	private String name;
	private List<PhoneNumber> numbers;
	public Customer(String name, List<PhoneNumber> numbers) {
		super();
		this.name = name;
		this.numbers = numbers;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<PhoneNumber> getNumbers() {
		return numbers;
	}
	public void setNumbers(List<PhoneNumber> numbers) {
		this.numbers = numbers;
	}	

}
