package com.app.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.model.Customer;
import com.app.model.SearchCriteria;
import com.app.model.SearchSpecification;
import com.app.repository.SearchCustomerRepository;

@Service
public class CustomerService {
	@Autowired
	SearchCustomerRepository searchCustomerRepository;
	
	public List<Customer> getAllCustomers(PageRequest pageRequest) {
		List<Customer> customers = new ArrayList<Customer>();
		searchCustomerRepository.findAll(pageRequest).forEach(customer -> customers.add(customer));
		return customers;
	}

	@Cacheable(cacheNames = "customers", key = "#id")
	public Customer getCustomerById(Long id) {
		return searchCustomerRepository.findById(id).get();
	}

	@Cacheable(cacheNames = "customers", key = "#searchKey")
	public List<Customer> getFilteredCustomers(String searchKey) throws IOException {
		SearchSpecification spec1 = new SearchSpecification(new SearchCriteria("firstname", ":", searchKey));
		SearchSpecification spec2 = new SearchSpecification(new SearchCriteria("lastname", ":", searchKey));
		SearchSpecification spec3 = new SearchSpecification(new SearchCriteria("email", ":", searchKey));
		SearchSpecification spec4 = new SearchSpecification(new SearchCriteria("jobtitle", ":", searchKey));
		SearchSpecification spec5 = new SearchSpecification(new SearchCriteria("company", ":", searchKey));
		
		List<Customer> customers = searchCustomerRepository.findAll(Specification.where(spec1).or(spec2).or(spec3).or(spec4).or(spec5));
		
		
		
		return customers;
	}

	public void saveOrUpdateCustomer(Customer Customer) {
		searchCustomerRepository.save(Customer);
	}

	public void deleteCustomer(Long id) {
		searchCustomerRepository.deleteById(id);
	}
}