package com.ApiProject.Banking_Management.service;

import com.ApiProject.Banking_Management.dto.CustomerReadDto;
import com.ApiProject.Banking_Management.dto.CustomerUpdateDto;
import com.ApiProject.Banking_Management.dto.CustomerWriteDto;
import com.ApiProject.Banking_Management.entity.Customer;
import com.ApiProject.Banking_Management.exceptions.ResourceNotFoundException;
import com.ApiProject.Banking_Management.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
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
    void testGetAllCustomers() {
        MockitoAnnotations.openMocks(this);
        List<Customer> mockCustomers = new ArrayList<>();
        Customer customer1 = new Customer();
        customer1.setFirst_name("Deepti");
        Customer customer2 = new Customer();
        customer2.setFirst_name("Anisha");
        mockCustomers.add(customer1);
        mockCustomers.add(customer2);

        when(customerRepository.findAll()).thenReturn(mockCustomers);

        List<CustomerReadDto> result = customerService.getAllCustomer();

        assertEquals(2, result.size());
        assertEquals("Deepti", result.get(0).getFirst_name());
        assertEquals("Anisha", result.get(1).getFirst_name());
    }

    @Test
    void testGetCustomerById_existingCustomer(){
        MockitoAnnotations.openMocks(this);
        int customerId = 1;
        Customer mockCustomer = new Customer();
        mockCustomer.setId(customerId);
        mockCustomer.setFirst_name("Deepti");
        mockCustomer.setAddress("Gurgaon");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        Customer result = customerService.getCustomerById(customerId);

        assertNotNull(result);
        assertEquals("Deepti", result.getFirst_name());
        assertEquals("Gurgaon", result.getAddress());
    }

    @Test
    void testGetCustomerById_nonExistingCustomer(){
        MockitoAnnotations.openMocks(this);
        int nonExistingCustomerId = 1;
        when(customerRepository.findById(nonExistingCustomerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(nonExistingCustomerId));
    }

    @Test
    public void testAddCustomer() {
        MockitoAnnotations.openMocks(this);

        CustomerWriteDto customerWriteDto = new CustomerWriteDto("Deepti", "Lodhi", "Gurgaon","9876543210","Female", Date.valueOf("2000-12-01"),"898989898989","deepti@gmail.com");
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1);
        mockCustomer.setLast_name(customerWriteDto.getLast_name());
        when(customerRepository.save(any())).thenReturn(mockCustomer);

        Customer result = customerService.addCustomer(customerWriteDto);

        assertEquals(1, result.getId());
        assertEquals("Lodhi", result.getLast_name());
    }

    @Test
    public void testUpdateCustomer_existingCustomer() {

        MockitoAnnotations.openMocks(this);
        int customerId = 1;
        CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto();
        customerUpdateDto.setEmail("abc@example.com");
        customerUpdateDto.setAddress("Udyog Vihar");
        customerUpdateDto.setPhone_no("9876543210");
        Customer mockCustomer = new Customer();
        mockCustomer.setId(customerId);
        mockCustomer.setEmail(customerUpdateDto.getEmail());
        mockCustomer.setPhone_no(customerUpdateDto.getPhone_no());
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));
        when(customerRepository.save(any())).thenReturn(mockCustomer);

        Customer result = customerService.updateCustomer(customerId, customerUpdateDto);

        assertNotNull(result);
        assertEquals("abc@example.com", result.getEmail());
        assertEquals("9876543210", result.getPhone_no());
    }
    @Test
    public void testUpdateCustomer_nonExistingCustomer(){
        MockitoAnnotations.openMocks(this);
        int nonExistingCustomerId = 1;
        CustomerUpdateDto updateDto = new CustomerUpdateDto();
        updateDto.setAddress("Udyog Vihar");
        updateDto.setPhone_no("9876543210");
        updateDto.setEmail("abc@example.com");

        when(customerRepository.findById(nonExistingCustomerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(nonExistingCustomerId, updateDto));
        verify(customerRepository, times(0)).save(any());
    }
    @Test
    public void testDeleteCustomer_existingCustomer() {
        MockitoAnnotations.openMocks(this);
        int customerId = 1;
        Customer mockCustomer = new Customer();
        mockCustomer.setId(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        assertDoesNotThrow(() -> customerService.deleteCustomer(customerId));
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    public void testDeleteCustomer_nonExistingCustomer() {
        MockitoAnnotations.openMocks(this);
        int nonExistingCustomerId = 1;
        when(customerRepository.findById(nonExistingCustomerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(nonExistingCustomerId));
        verify(customerRepository, times(0)).deleteById(nonExistingCustomerId);
    }

}
