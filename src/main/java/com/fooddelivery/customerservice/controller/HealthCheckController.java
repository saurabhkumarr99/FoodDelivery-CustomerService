package com.fooddelivery.customerservice.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

	@GetMapping("/healthCheck")
	public ResponseEntity<?> healthCheck(){
		
		HashMap<String, String> response =  new HashMap<>();
		response.put("Status", "UP");
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
