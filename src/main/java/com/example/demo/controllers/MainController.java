package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ActivateNumber;
import com.example.demo.models.AddCustomer;
import com.example.demo.models.Customer;
import com.example.demo.models.PhoneNumber;
import com.example.demo.service.Database;

@RestController
@RequestMapping("")
public class MainController {

	@Autowired
	Database db;

	@GetMapping("/number")
	public List<PhoneNumber> numbers() {
		return db.getNumbers();
	}

	@GetMapping("/customer")
	public List<Customer> customers() {
		return db.getCustomers();
	}

	@GetMapping("/customer/{name}")
	public ResponseEntity<?> getCustomer(@PathVariable("name") String name) {
		List<PhoneNumber> numbers = db.getCustomer(name);
		if (numbers == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(new Customer(name, numbers), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "addcustomer", method = { RequestMethod.POST })
	public ResponseEntity<?> addCustomer(@RequestBody AddCustomer input) {
		if (input == null || input.getName() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name is missing");
		} else {
			Customer newCustomer = db.addCustomer(input.getName());
			if (newCustomer == null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer already exist");
			} else {
				return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
			}
		}
	}

	@RequestMapping(value = "activatenumber", method = { RequestMethod.PUT })
	public ResponseEntity<?> activateNumber(@RequestBody ActivateNumber input) {
		if (input == null || input.getName() == null || input.getNumber() == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			String status = db.activateNumber(input.getName(), input.getNumber());
			if (status.equals("Number activated")) {
				return ResponseEntity.status(HttpStatus.CREATED).body(new Customer(input.getName(), db.getCustomer(input.getName())));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(status);
			}
		}

	}

}
