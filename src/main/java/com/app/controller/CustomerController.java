package com.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Customer;
import com.app.service.CustomerService;

@RestController
@EnableCaching
@CrossOrigin
public class CustomerController {
	@Autowired
	CustomerService customerService;

	@GetMapping("/customer")
	private List<Customer> getAllCustomers(@RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return customerService.getAllCustomers(pageRequest);
	}

	@GetMapping("/customer/{id}")
	private Customer getCustomer(@PathVariable("id") Long id) {
		return customerService.getCustomerById(id);
	}

	@GetMapping(value="/customer/names")
    public ResponseEntity<List<String>> searchCustomerNames(@RequestParam(value="searchKey", required=false, defaultValue="None")  String searchKey) {
        try {
            List<Customer> customers = customerService.getFilteredCustomers(searchKey.toUpperCase());
            List<String> customerNames = customers.stream().map(customer -> customer.getFirstname() + " " + customer.getLastname()).collect(Collectors.toList());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            return new ResponseEntity<List<String>>(customerNames, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<List<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping(value="/customer/list")
    public ResponseEntity<List<Customer>> searchCustomers(@RequestParam(value="searchKey", required=false, defaultValue="None")  String searchKey) {
        try {
            List<Customer> customers = customerService.getFilteredCustomers(searchKey.toUpperCase());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            return new ResponseEntity<List<Customer>>(customers, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<List<Customer>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@DeleteMapping("/customer/{id}")
	private void deleteCustomer(@PathVariable("id") Long id) {
		customerService.deleteCustomer(id);
	}

	@PostMapping("/customer")
	private Long saveCustomer(@RequestBody Customer customer) {
		customerService.saveOrUpdateCustomer(customer);
		return customer.getId();
	}
}
