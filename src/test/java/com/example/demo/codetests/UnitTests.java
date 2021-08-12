package com.example.demo.codetests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.demo.models.ActivateNumber;
import com.example.demo.models.AddCustomer;
import com.example.demo.models.Customer;
import com.example.demo.models.PhoneNumber;

public class UnitTests {
	
		
	@Test
	public void customerTests() {
		
		Customer customer = new Customer("tom", new ArrayList<>());
		assertTrue(customer.getName().equals("tom"));
		assertTrue(customer.getNumbers().size() == 0);
		customer.setName("cruise");
		assertTrue(customer.getName().equals("cruise"));
		List<PhoneNumber> numbers = new ArrayList<>();
		numbers.add(new PhoneNumber("0412345678", false));
		customer.setNumbers(numbers);
		assertTrue(customer.getNumbers().size() == 1);
	}
	
	@Test
	public void phoneNumberTests() {
		
		PhoneNumber number = new PhoneNumber("0412345678", false);
		assertTrue(number.getNumber().equals("0412345678"));
		assertTrue(number.getIsActive() == false);
		number.setNumber("0412345677");
		assertTrue(number.getNumber().equals("0412345677"));
		number.setIsActive(true);
		assertTrue(number.getIsActive() == true);
	}
	
	@Test
	public void addCustomerTests() {
		
		AddCustomer newCustomer = new AddCustomer();
		newCustomer.setName("Tom");
		assertTrue(newCustomer.getName().equals("Tom"));		
	}
	
	@Test
	public void activateNumberTests() {
		ActivateNumber input = new ActivateNumber();
		input.setName("Cruise");
		assertTrue(input.getName().equals("Cruise"));
		input.setNumber("0412345678");
		assertTrue(input.getNumber().equals("0412345678"));
	}

}
