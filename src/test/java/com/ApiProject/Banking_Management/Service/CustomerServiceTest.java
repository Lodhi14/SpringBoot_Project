package com.ApiProject.Banking_Management.Service;

import com.ApiProject.Banking_Management.Entity.Customer;
import com.ApiProject.Banking_Management.Repository.CustomerRepository;
import com.ApiProject.Banking_Management.Service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCustomer() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        customerList.add(new Customer());
        when(customerRepository.findAll()).thenReturn(customerList);
        List<Customer> result = customerService.getAllCustomer();
        verify(customerRepository, times(1)).findAll();
        assertNotNull(result);
        assertEquals(customerList, result);
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer();
        customerService.addCustomer(customer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer();
        customerService.updateCustomer(1, customer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testDeleteCustomer_existingCustomer() {
        Customer customer = new Customer();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        boolean result = customerService.deleteCustomer(1);
        verify(customerRepository, times(1)).findById(1);
        verify(customerRepository, times(1)).delete(customer);
        assertTrue(result);
    }

    @Test
    public void testDeleteCustomer_nonExistingCustomer() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());
        boolean result = customerService.deleteCustomer(1);
        verify(customerRepository, times(1)).findById(1);
        assertFalse(result);
    }

    @Test
    public void testGetCustomer_existingCustomer() {
        Customer customer = new Customer();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        Customer result = customerService.getCustomer(1);
        verify(customerRepository, times(1)).findById(1);
        assertEquals(customer, result);
    }

    @Test
    public void testGetCustomer_nonExistingCustomer() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());
        Customer result = customerService.getCustomer(1);
        verify(customerRepository, times(1)).findById(1);
        assertNull(result);
    }
}
