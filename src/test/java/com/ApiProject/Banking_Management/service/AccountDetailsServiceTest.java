package com.ApiProject.Banking_Management.service;
import com.ApiProject.Banking_Management.dto.AccountDetailsWriteDto;
import com.ApiProject.Banking_Management.entity.AccountDetails;
import com.ApiProject.Banking_Management.entity.Customer;
import com.ApiProject.Banking_Management.exceptions.InsufficientBalanceException;
import com.ApiProject.Banking_Management.exceptions.ResourceNotFoundException;
import com.ApiProject.Banking_Management.repository.AccountDetailsRepository;
import com.ApiProject.Banking_Management.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountDetailsServiceTest {
    @Mock
    private AccountDetailsRepository accountDetailsRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AccountDetailsService accountDetailsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void createAccountTest() {
        int customerId = 1;
        AccountDetailsWriteDto accountDetailsWriteDto = new AccountDetailsWriteDto(customerId, "Savings", 1000.0, "Gurgaon");

        Customer mockCustomer = new Customer();
        mockCustomer.setId(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        AccountDetails mockAccountDetails = new AccountDetails(12345L, "Savings", 1000.0, "Gurgaon", mockCustomer);
        when(accountDetailsRepository.save(any())).thenReturn(mockAccountDetails);

        AccountDetailsWriteDto result = accountDetailsService.createAccount(accountDetailsWriteDto);

        assertNotNull(result);
        assertEquals(customerId, result.getCustomer_id());
        assertEquals("Savings", result.getAccount_type());
        assertEquals(1000.0, result.getBalance());
        assertEquals("Gurgaon", result.getBranch());
    }

    @Test
    public void depositTest_existingAccount() {
        long accountNo = 12345L;
        double balance = 1000.0;
        double depositAmount = 500.0;

        AccountDetails account = new AccountDetails();
        account.setAccount_no(accountNo);
        account.setBalance(balance);
        account.setAccount_type("Savings");
        account.setBranch("Gurgaon");
        when(accountDetailsRepository.findById(accountNo)).thenReturn(Optional.of(account));
        when(accountDetailsRepository.save(any(AccountDetails.class))).thenAnswer(invocation -> invocation.getArguments()[0]);


        AccountDetails result = assertDoesNotThrow(()->accountDetailsService.deposit(accountNo, depositAmount));

        assertNotNull(result);
        assertEquals(balance + depositAmount, result.getBalance());
    }

    @Test
    public void depositTest_nonExistingAccount(){
        long accountNo = 12345L;
        double depositAmount = 500.0;

        when(accountDetailsRepository.findById(accountNo)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountDetailsService.deposit(accountNo, depositAmount));
    }


    @Test
    public void sufficientAmountWithdrawTest_existingAccount() {
        long accountNo = 12345L;
        double balance = 1000.0;
        double withdrawalAmount = 500.0;

        AccountDetails account = new AccountDetails();
        account.setAccount_no(accountNo);
        account.setBalance(balance);
        account.setAccount_type("Savings");
        account.setBranch("Gurgaon");
        when(accountDetailsRepository.findById(accountNo)).thenReturn(Optional.of(account));
        when(accountDetailsRepository.save(any(AccountDetails.class))).thenAnswer(invocation -> invocation.getArguments()[0]);


        AccountDetails result = accountDetailsService.withdraw(accountNo, withdrawalAmount);

        assertNotNull(result);
        assertEquals(balance - withdrawalAmount, result.getBalance());

    }
    @Test
    public void insufficientAmountWithdrawTest_existingAccount(){
        long accountNo = 12345L;
        double balance = 100.0;
        double withdrawalAmount = 500.0;

        AccountDetails account = new AccountDetails(accountNo, "Savings", balance, "Gurgaon", new Customer());
        when(accountDetailsRepository.findById(accountNo)).thenReturn(Optional.of(account));

        assertThrows(InsufficientBalanceException.class, () -> accountDetailsService.withdraw(accountNo, withdrawalAmount));
    }
    @Test
    public void withdrawTest_nonExistingAccount(){
        long accountNo = 12345L;
        double withdrawalAmount = 500.0;

        when(accountDetailsRepository.findById(accountNo)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountDetailsService.withdraw(accountNo, withdrawalAmount));
    }

    @Test
    public void balanceEnquiryTest_existingAccount() {
        long accountNo = 12345L;
        double initialBalance = 1000.0;

        AccountDetails account = new AccountDetails();
        account.setAccount_no(accountNo);
        account.setBalance(initialBalance);
        when(accountDetailsRepository.findById(accountNo)).thenReturn(Optional.of(account));

        Double balance = accountDetailsService.balanceEnquiry(accountNo);

        assertNotNull(balance);
        assertEquals(initialBalance, balance);
    }

    @Test
    public void balanceEnquiryTest_nonExistingAccount() {
        long accountNo = 12345L;
        when(accountDetailsRepository.findById(accountNo)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> accountDetailsService.balanceEnquiry(accountNo));
    }

    @Test
    public void deleteAccountTest_existingAccount() {
        long accountNo = 12345L;

        AccountDetails account = new AccountDetails();
        account.setAccount_no(accountNo);
        when(accountDetailsRepository.findById(accountNo)).thenReturn(Optional.of(account));

        assertDoesNotThrow(() -> accountDetailsService.deleteAccount(accountNo));
        verify(accountDetailsRepository, times(1)).deleteById(accountNo);
    }

    @Test
    public void deleteAccountTest_nonExistingAccount() {
        long accountNo = 12345L;

        when(accountDetailsRepository.findById(accountNo)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountDetailsService.deleteAccount(accountNo));
        verify(accountDetailsRepository, times(0)).deleteById(accountNo);
    }

    @Test
    public void getAccountsByCustomerIdTest_existingCustomer_existingAccounts() {
        int customerId = 1;

        Customer customer = new Customer();
        customer.setId(customerId);

        List<AccountDetails> accounts = new ArrayList<>();
        accounts.add(new AccountDetails(12345L, "Savings", 1000.0, "Gurgaon", customer));
        accounts.add(new AccountDetails(67890L, "Current", 2000.0, "Sec 14", customer));

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountDetailsRepository.findAllByCustomerId(customerId)).thenReturn(accounts);

        List<AccountDetails> result = accountDetailsService.getAccountsByCustomerId(customerId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void getAccountsByCustomerIdTest_existingCustomer_nonExistingAccounts(){
        int customerId = 1;

        Customer customer = new Customer();
        customer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountDetailsRepository.findAllByCustomerId(customerId)).thenReturn(new ArrayList<>());

        List<AccountDetails> result = accountDetailsService.getAccountsByCustomerId(customerId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAccountsByCustomerIdTest_nonExistingCustomer(){
        int customerId = 123;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountDetailsService.getAccountsByCustomerId(customerId));
    }
    @Test
    public void getAccountByIdTest_existingAccount() {
        long accountNo = 12345L;
        double initialBalance = 1000.0;

        AccountDetails account = new AccountDetails();
        account.setAccount_no(accountNo);
        account.setBalance(initialBalance);
        account.setAccount_type("Savings");
        account.setBranch("Gurgaon");

        when(accountDetailsRepository.findById(accountNo)).thenReturn(Optional.of(account));

        AccountDetails result = accountDetailsService.getAccount(accountNo);

        assertNotNull(result);
        assertEquals(accountNo, result.getAccount_no());
        assertEquals("Savings", result.getAccount_type());
        assertEquals(initialBalance, result.getBalance());
        assertEquals("Gurgaon", result.getBranch());
    }

    @Test
    public void getAccountByIdTest_nonExistingAccount() {
        long accountNo = 12345L;
        when(accountDetailsRepository.findById(accountNo)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountDetailsService.getAccount(accountNo));
    }
}

