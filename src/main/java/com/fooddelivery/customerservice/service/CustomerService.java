package com.fooddelivery.customerservice.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fooddelivery.customerservice.exception.CustomerNotFoundException;
import com.fooddelivery.customerservice.exception.DuplicateEmailException;
import com.fooddelivery.customerservice.model.Customer;
import com.fooddelivery.customerservice.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

	/**
	 * @author Saurabh Rai
	 * @apiNote Create Customer
	 * @param customer
	 * @return
	 */
	public Customer registerCustomer(Customer customer) throws Exception {
		try {
			return customerRepository.save(customer);
		} catch (DataIntegrityViolationException e) {
			// This happens if email is duplicate
			throw new DuplicateEmailException("Email already exists: " + customer.getEmail());
		}
	}

	/**
	 * @author saura Saurabh Rai
	 * @apiNote Return customer with id
	 * @param cutomerId
	 * @return Customer
	 * @throws Exception
	 */
	public Customer getCustomerById(int cutomerId) throws CustomerNotFoundException {

		Optional<Customer> customer = customerRepository.findCustomerByCustomerId(cutomerId);
		if (customer.isPresent()) {
			return customer.get();
		} else {
			throw new CustomerNotFoundException("Customer Not found with ID : " + cutomerId);
		}

	}

	/**
	 * @author Saurabh Rai
	 * @apiNote Get all customer
	 * @return Customer
	 */
	public List<Customer> getAllCustomer() {
		return customerRepository.findAll();
	}

	/**
	 * @author Saurabh Rai
	 * @apiNote Update customer
	 * @param customer
	 * @return
	 * @throws CustomerNotFoundException
	 * @throws DuplicateEmailException
	 */
	public Customer updateCustomer(Customer customer) throws CustomerNotFoundException, DuplicateEmailException {
		Optional<Customer> customer2 = customerRepository.findCustomerByCustomerId(customer.getCustomerId());
		if (customer2.isPresent()) {
			try {
				customerRepository.save(customer);
			} catch (DataIntegrityViolationException e) {
				// This happens if email is duplicate
				throw new DuplicateEmailException("Email already exists: " + customer.getEmail());
			}
		} else {
			throw new CustomerNotFoundException("Customer Not Exist with ID : " + customer.getCustomerId());
		}

		return customer;
	}

	/**
	 * @author Saurabh Rai
	 * @apiNote Delete Customer by id
	 * @param customer
	 * @return
	 * @throws CustomerNotFoundException
	 */
	public Customer deleteCustomer(int id) throws CustomerNotFoundException {
		Optional<Customer> customer2 = customerRepository.findCustomerByCustomerId(id);
	
		if (customer2.isPresent()) {
			customerRepository.deleteById(customer2.get().getCustomerId());
		} else {
			throw new CustomerNotFoundException("Customer Not Exist with ID : " + id);
		}

		return customer2.get();
	}
}
