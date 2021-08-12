package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.models.Customer;
import com.example.demo.models.PhoneNumber;

@Service
public class Database {

	private Map<String, List<PhoneNumber>> customers;
	private Map<String, Boolean> numbers;

	public Database() {
		super();
		this.customers = new HashMap<>();
		this.addCustomer("tom");

		this.numbers = new HashMap<>();
		numbers.put("0412345678", false);
		numbers.put("0412345677", false);
		numbers.put("0412345676", false);
		numbers.put("0412345675", false);
		numbers.put("0412345674", false);
		numbers.put("0412345673", false);
		numbers.put("0412345672", false);
		numbers.put("0412345671", false);
		numbers.put("0412345670", false);
	}

	public List<PhoneNumber> getNumbers() {
		return numbers.entrySet().stream().map(entry -> new PhoneNumber(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}

	public List<Customer> getCustomers() {
		return customers.entrySet().stream().map(entry -> new Customer(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}
	
	public List<PhoneNumber> getCustomer(String name) {
		return customers.get(name);
		
	}

	public Customer addCustomer(String name) {
		List<PhoneNumber> customerExistingNumbers = customers.get(name);
		if (customerExistingNumbers == null) {
			Customer newCustomer = new Customer(name, new ArrayList<>());
			customers.put(name, new ArrayList<>());
			return newCustomer;
		} else {
			//returning null if user already exist
			return null;
		}

	}

	public String activateNumber(String customerName, String number) {
		List<PhoneNumber> customerExistingNumbers = customers.get(customerName);
		if (customerExistingNumbers == null) {
			return "Customer doesn't exist in DB";
		}
		Boolean numberStatus = numbers.get(number);
		if (numberStatus == null) {
			return "Number doesn't exist in DB";
		} else if (numberStatus == true) {
			return "Number already activated";
		} else {
			// Add the number to customer list
			customerExistingNumbers.add(new PhoneNumber(number, true));
			// Make the status of number to Active so that it can't be re-activated
			numbers.put(number, true);
			return "Number activated";
		}

	}

}
