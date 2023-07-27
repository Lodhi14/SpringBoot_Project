package com.ApiProject.Banking_Management.repository;

import com.ApiProject.Banking_Management.entity.Customer;
import org.springframework.data.repository.CrudRepository;
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}
