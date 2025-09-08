package com.fooddelivery.customerservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.customerservice.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	//Get customer by id
	Optional<Customer> findCustomerByCustomerId(Integer customerId);
}
