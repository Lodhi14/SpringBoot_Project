package com.ApiProject.Banking_Management.Controller;

import com.ApiProject.Banking_Management.Entity.Customer;
import com.ApiProject.Banking_Management.Service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getAllCustomer() {
        logger.info("Fetching all customers");
        return customerService.getAllCustomer();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable int id) {
        logger.info("Fetching customer with id: {}", id);
        return customerService.getCustomer(id);
    }

    @PostMapping("/customers")
    public void addCustomer(@RequestBody Customer customer) {
        logger.info("Adding customer: {}", customer);
        customerService.addCustomer(customer);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/customers/update/{id}")
    public void updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        logger.info("Updating customer with id: {}", id);
        customerService.updateCustomer(id, customer);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/customers/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
        logger.info("Deleting customer with id: {}", id);
        boolean success = customerService.deleteCustomer(id);
        if (success) {
            logger.info("Customer deleted successfully");
            return ResponseEntity.ok("Customer deleted successfully");
        } else {
            logger.warn("Customer not found");
            return ResponseEntity.badRequest().body("Customer not found");
        }
    }
}
