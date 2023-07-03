package com.ApiProject.Banking_Management.Repository;

import com.ApiProject.Banking_Management.Entity.Customer;
import org.springframework.data.repository.CrudRepository;
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}
