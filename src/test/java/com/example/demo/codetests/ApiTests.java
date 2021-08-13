package com.example.demo.codetests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.entry;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.controllers.MainController;
import com.example.demo.models.ActivateNumber;
import com.example.demo.models.AddCustomer;
import com.example.demo.models.Customer;
import com.example.demo.models.PhoneNumber;
import com.example.demo.service.Database;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MainController.class)
public class ApiTests {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	@MockBean
	Database db;

	@Test
	public void numbers() throws Exception {
		Map<String, Boolean> numbers = new HashMap<>();
		numbers.put("0412345678", false);
		numbers.put("0412345677", false);
		numbers.put("0412345676", false);
		numbers.put("0412345675", false);
		numbers.put("0412345674", false);
		numbers.put("0412345673", false);
		numbers.put("0412345672", false);
		numbers.put("0412345671", false);
		numbers.put("0412345670", false);

		Mockito.when(db.getNumbers()).thenReturn(numbers.entrySet().stream()
				.map(entry -> new PhoneNumber(entry.getKey(), entry.getValue())).collect(Collectors.toList()));

		mockMvc.perform(MockMvcRequestBuilders.get("/number").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(9)))
				.andExpect(jsonPath("$[2].isActive", is(false)));
	}

	@Test
	public void customer() throws Exception {
		Map<String, List<PhoneNumber>> customers = new HashMap<>();
		customers.put("tom", new ArrayList<>());

		Mockito.when(db.getCustomers()).thenReturn(customers.entrySet().stream()
				.map(entry -> new Customer(entry.getKey(), entry.getValue())).collect(Collectors.toList()));

		mockMvc.perform(MockMvcRequestBuilders.get("/customer").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is("tom")));

	}

	@Test
	public void customerWithName() throws Exception {
		Map<String, List<PhoneNumber>> customers = new HashMap<>();
		customers.put("tom", new ArrayList<>());

		Mockito.when(db.getCustomer("tom")).thenReturn(customers.get("tom"));

		mockMvc.perform(MockMvcRequestBuilders.get("/customer/tom").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name", is("tom")));

	}

	@Test
	public void addCustomer_new() throws Exception {
		Customer customer = new Customer("tom", new ArrayList<>());
		AddCustomer newCustomer = new AddCustomer();
		newCustomer.setName("tom");
		Mockito.when(db.addCustomer("tom")).thenReturn(customer);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/addcustomer")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(newCustomer));

		mockMvc.perform(mockRequest).andExpect(status().isCreated()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("tom")));
	}

	@Test
	public void addCustomer_existing() throws Exception {
		AddCustomer newCustomer = new AddCustomer();
		newCustomer.setName("tom");
		Mockito.when(db.addCustomer("tom")).thenReturn(null);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/addcustomer")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(newCustomer));
		mockMvc.perform(mockRequest).andExpect(status().isConflict());
	}
	
	@Test
	public void addCustomer_null() throws Exception {
		AddCustomer newCustomer = new AddCustomer();
		Mockito.when(db.addCustomer("tom")).thenReturn(null);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/addcustomer")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(newCustomer));
		mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
	}
	
	@Test
	public void activate_valid() throws Exception {
		ActivateNumber input = new ActivateNumber();
		input.setName("tom");
		input.setNumber("0412345678");
		Mockito.when(db.activateNumber("tom", "0412345678")).thenReturn("Number activated");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/activatenumber")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(input));

		mockMvc.perform(mockRequest).andExpect(status().isCreated())
		.andExpect(jsonPath("$", notNullValue()));
	}
	
	@Test
	public void activate_noCustomer() throws Exception {
		ActivateNumber input = new ActivateNumber();
		input.setName("tom");
		input.setNumber("0412345678");
		Mockito.when(db.activateNumber("tom", "0412345678")).thenReturn("Customer doesn't exist in DB");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/activatenumber")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(input));

		mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
	}
	
	@Test
	public void activate_noNumber() throws Exception {
		ActivateNumber input = new ActivateNumber();
		input.setName("tom");
		input.setNumber("0412345678");
		Mockito.when(db.activateNumber("tom", "0412345678")).thenReturn("Number doesn't exist in DB");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/activatenumber")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(input));

		mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
	}
	
	@Test
	public void activate_nameNull() throws Exception {
		ActivateNumber input = new ActivateNumber();
		input.setNumber("0412345678");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/activatenumber")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(input));

		mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
	}
	
	@Test
	public void activate_numberNull() throws Exception {
		ActivateNumber input = new ActivateNumber();
		input.setName("tom");
		//Mockito.when(db.activateNumber("tom", "0412345678")).thenReturn("Number doesn't exist in DB");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/activatenumber")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(input));

		mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
	}
	
	@Test
	public void activate_inputNull() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/activatenumber")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(null));

		mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
	}

}
