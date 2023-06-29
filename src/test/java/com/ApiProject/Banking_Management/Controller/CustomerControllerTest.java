package com.ApiProject.Banking_Management.Controller;

import com.ApiProject.Banking_Management.Enitity.Customer;
import com.ApiProject.Banking_Management.Service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCustomer() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer(1, "John", "Doe", "Address 1", "Branch 1", "1234567890", "Male", "2000-01-01", "123456789"));
        customerList.add(new Customer(2, "Jane", "Smith", "Address 2", "Branch 2", "9876543210", "Female", "2000-02-02", "987654321"));
        when(customerService.getAllCustomer()).thenReturn(customerList);
        List<Customer> result = customerController.getAllCustomer();
        verify(customerService, times(1)).getAllCustomer();
        assertEquals(customerList, result);
    }

    @Test
    public void testGetCustomer() {
        Customer customer = new Customer(1, "John", "Doe", "Address 1", "Branch 1", "1234567890", "Male", "2000-01-01", "123456789");
        when(customerService.getCustomer(1)).thenReturn(customer);
        Customer result = customerController.getCustomer(1);
        verify(customerService, times(1)).getCustomer(1);
        assertEquals(customer, result);
    }

    @Test
    public void testAddCustomer() {

        Customer customer = new Customer(1, "John", "Doe", "Address 1", "Branch 1", "1234567890", "Male", "2000-01-01", "123456789");
        customerController.addCustomer(customer);
        verify(customerService, times(1)).addCustomer(customer);
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer(1, "John", "Doe", "Address 1", "Branch 1", "1234567890", "Male", "2000-01-01", "123456789");
        customerController.updateCustomer(1, customer);
        verify(customerService, times(1)).updateCustomer(1, customer);
    }

    @Test
    public void testDeleteCustomer_existingCustomer() {
        when(customerService.deleteCustomer(1)).thenReturn(true);
        ResponseEntity<String> response = customerController.deleteCustomer(1);
        verify(customerService, times(1)).deleteCustomer(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer deleted successfully", response.getBody());
    }

    @Test
    public void testDeleteCustomer_nonExistingCustomer() {
        when(customerService.deleteCustomer(1)).thenReturn(false);
        ResponseEntity<String> response = customerController.deleteCustomer(1);
        verify(customerService, times(1)).deleteCustomer(1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Customer not found", response.getBody());
    }
}

