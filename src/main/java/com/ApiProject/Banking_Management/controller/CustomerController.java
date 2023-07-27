package com.ApiProject.Banking_Management.controller;

import com.ApiProject.Banking_Management.dto.CustomerReadDto;
import com.ApiProject.Banking_Management.dto.CustomerUpdateDto;
import com.ApiProject.Banking_Management.dto.CustomerWriteDto;
import com.ApiProject.Banking_Management.entity.Customer;
import com.ApiProject.Banking_Management.exceptions.ForeignKeyConstraintException;
import com.ApiProject.Banking_Management.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public CustomerService customerService;

    @GetMapping("/fetchAll")
    public ResponseEntity<List<CustomerReadDto>>getAllCustomer() {
        logger.info("Fetching all customers");
        List <CustomerReadDto> custList=customerService.getAllCustomer();
        if(custList.size()<=0){
            logger.info("No customer found");
            return ResponseEntity.noContent().build();
        }
        else {
            logger.info("All customer found");
            return ResponseEntity.ok(custList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
        logger.info("Fetching customer with id: {}", id);
        Customer customer=customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<String> addCustomer(@RequestBody @Valid CustomerWriteDto customerWriteDto) {
        logger.info("Adding customer: {}", customerWriteDto);
        Customer customer=customerService.addCustomer(customerWriteDto);
        return ResponseEntity.ok().body("Customer added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCustomer(@RequestParam int id, @RequestBody @Valid CustomerUpdateDto customerUpdateDto) {
        logger.info("Updating customer with id: {}", id);
        Customer customer=customerService.updateCustomer(id,customerUpdateDto);
        return ResponseEntity.ok().body("Customer updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
        logger.info("Deleting customer with id: {}", id);
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().body("Customer deleted successfully");
    }
}
