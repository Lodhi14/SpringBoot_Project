package com.ApiProject.Banking_Management.Controller;

import com.ApiProject.Banking_Management.Enitity.Customer;
import com.ApiProject.Banking_Management.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    public CustomerService customerServ;

    @GetMapping("/customers")
    public List<Customer> getAllCustomer()
    {
        return customerServ.getAllCustomer();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable int id)
    {

        return customerServ.getCustomer(id);
    }

    @PostMapping("/customers")
    public void addCustomer(@RequestBody Customer customer)
    {
        customerServ.addCustomer(customer);
    }

    @RequestMapping(method = RequestMethod.PUT , value = "/customers/update/{id}")
    public void updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        customerServ.updateCustomer(id, customer);
    }

    @RequestMapping(method = RequestMethod.DELETE , value = "/customers/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id)
    {
        boolean success=customerServ.deleteCustomer(id);
        if (success) {
            return ResponseEntity.ok("Customer deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Customer not found");
        }
    }
}
