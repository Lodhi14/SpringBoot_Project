package com.ApiProject.Banking_Management.service;

import com.ApiProject.Banking_Management.dto.CustomerReadDto;
import com.ApiProject.Banking_Management.dto.CustomerUpdateDto;
import com.ApiProject.Banking_Management.dto.CustomerWriteDto;
import com.ApiProject.Banking_Management.entity.Customer;
import com.ApiProject.Banking_Management.exceptions.ForeignKeyConstraintException;
import com.ApiProject.Banking_Management.exceptions.ResourceNotFoundException;
import com.ApiProject.Banking_Management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepo;
    private CustomerReadDto convertToReadDto(Customer customer){
        CustomerReadDto readDto=new CustomerReadDto();
        readDto.setFirst_name(customer.getFirst_name());
        readDto.setLast_name(customer.getLast_name());
        readDto.setAddress(customer.getAddress());
        return readDto;
    }
    public List<CustomerReadDto> getAllCustomer(){
        List<Customer> customers= (List<Customer>) customerRepo.findAll();
        return customers.stream().map(this::convertToReadDto).collect(Collectors.toList());
    }

    public Customer getCustomerById(int id) {
        Customer cust=customerRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer does not exist th id: " + id));
        return cust;
    }

    public Customer addCustomer(CustomerWriteDto customerWriteDto) {
        Customer customer= new Customer();
        customer.setFirst_name(customerWriteDto.getFirst_name());
        customer.setLast_name(customerWriteDto.getLast_name());
        customer.setAddress(customerWriteDto.getAddress());
        customer.setPhone_no(customerWriteDto.getPhone_no());
        customer.setGender(customerWriteDto.getGender());
        customer.setDate_of_birth(customerWriteDto.getDate_of_birth());
        customer.setAadhaar_no(customerWriteDto.getAadhaar_no());
        customer.setEmail(customerWriteDto.getEmail());
        return customerRepo.save(customer);
    }


    public Customer updateCustomer(int id, CustomerUpdateDto customerUpdateDto) {
        Customer customer=customerRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Non-existing Id"));
        customer.setEmail(customerUpdateDto.getEmail());
        customer.setAddress(customerUpdateDto.getAddress());
        customer.setPhone_no(customerUpdateDto.getPhone_no());
        customer=customerRepo.save(customer);
        return customer;
    }

    public void deleteCustomer(int id) {
        try {
            Customer customer = customerRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Non-existing Id"));
            customerRepo.deleteById(id);
        }
        catch (DataAccessException exception)
        {
            throw new ForeignKeyConstraintException("Can not delete customer as customer has existing accounts.");
        }
    }



}
