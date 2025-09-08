package com.fooddelivery.customerservice.controller;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.customerservice.dto.CustomerDto;
import com.fooddelivery.customerservice.exception.CustomerNotFoundException;
import com.fooddelivery.customerservice.exception.DuplicateEmailException;
import com.fooddelivery.customerservice.model.Customer;
import com.fooddelivery.customerservice.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customerService")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	/**
	 * @author saura Saurabh Rai
	 * @apiNote Register Customer
	 * @param customer
	 * @return
	 */
	@PostMapping("/registerCustomer")
	public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errors);
		}

		Customer customer2 = null;

		try {
			customer2 = customerService.registerCustomer(customer);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getLocalizedMessage()));
		}

		logger.info("Customer Registered Succesfully : " + customer);
		CustomerDto customerDto = new CustomerDto(customer2);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}

	/**
	 * @author Saurabh Rai
	 * @apiNote Get customer by id
	 * @param id
	 * @return
	 */
	@GetMapping("/getCustomerById/{id}")
	public ResponseEntity<?> getCustomerById(@PathVariable("id") int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerById(id));
		} catch (CustomerNotFoundException e) {
			logger.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getLocalizedMessage()));
		}
	}

	/**
	 * @author Saurabh Rai
	 * @apiNote Get all customers
	 * @return
	 */
	@GetMapping("/getAllCustomers")
	public ResponseEntity<?> getAllCustomer() {
		return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomer());
	}

	/**
	 * @author Saurabh Rai
	 * @apiNote Update customer
	 * @param customer
	 * @return
	 */
	@PutMapping("/updateCustomer")
	public ResponseEntity<?> updateCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult) {

		if (customer.getCustomerId() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Customer Id can not be null"));
		}
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			Customer customer2 = customerService.updateCustomer(customer);
			logger.info("Customer Update successfully : " + customer2);

		} catch (CustomerNotFoundException | DuplicateEmailException e) {
			logger.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getLocalizedMessage()));
		}
		CustomerDto customerDto = new CustomerDto(customer);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}

	/**
	 * @author Saurabh Rai
	 * @apiNote Delete Customer
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deleteCustomer/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") int id) {
		Customer customer = null;
		try {
			customer = customerService.deleteCustomer(id);
			logger.info("Customer deleted successfully : " + customer);
		} catch (CustomerNotFoundException e) {
			logger.error("--------------------------------------------------------msg : "+e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getLocalizedMessage()));
		}
		return ResponseEntity.status(HttpStatus.OK).body(customer);

	}
}
