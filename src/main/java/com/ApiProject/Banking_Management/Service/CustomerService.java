package com.ApiProject.Banking_Management.Service;

import com.ApiProject.Banking_Management.Enitity.Customer;
import com.ApiProject.Banking_Management.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepo;
    public List<Customer> getAllCustomer(){
        List<Customer> customers=new ArrayList<>();
        customerRepo.findAll().forEach(customers::add);
        return customers;
    }

    public void addCustomer(Customer customer) {
        customerRepo.save(customer);
    }


    public void updateCustomer(int id, Customer customer) {
        customerRepo.save(customer);
    }

    public boolean deleteCustomer(int id) {
        Customer customer=customerRepo.findById(id).orElse(null);
        if(customer!=null)
        {
            customerRepo.delete(customer);
            return true;
        }
        return false;
    }


    public Customer getCustomer(int id) {
        Customer cust=customerRepo.findById(id).orElse(null);
        return cust;
    }
}
