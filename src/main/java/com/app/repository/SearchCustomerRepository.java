package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.app.model.Customer;

public interface SearchCustomerRepository 
	extends JpaRepository<Customer, Long>, 
			JpaSpecificationExecutor<Customer>, 
			PagingAndSortingRepository<Customer, Long> 
{
	
}
