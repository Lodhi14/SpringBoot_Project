package com.ApiProject.Banking_Management.Service;
import com.ApiProject.Banking_Management.Entity.AccountDetails;
import com.ApiProject.Banking_Management.Repository.AccountDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountDetailsServiceTest {
    @Mock
    private AccountDetailsRepository accountDetailsRepository;

    @InjectMocks
    private AccountDetailsService accountDetailsService;

    private AccountDetails accountDetails;

    @BeforeEach
    public void setup() {
        accountDetails = new AccountDetails();
        accountDetails.setAccount_no(1L);
        accountDetails.setAccount_type("Savings");
        accountDetails.setBalance(1000.0);
        accountDetails.setCreated_at(LocalDateTime.now());
        accountDetails.setUpdated_at(LocalDateTime.now());
    }

    @Test
    public void createAccountTest() {
        when(accountDetailsRepository.save(any(AccountDetails.class))).thenReturn(accountDetails);

        AccountDetails createdAccount = accountDetailsService.createAccount(accountDetails);

        assertNotNull(createdAccount);
        assertEquals(accountDetails.getAccount_no(), createdAccount.getAccount_no());
        assertEquals(accountDetails.getAccount_type(), createdAccount.getAccount_type());
        assertEquals(accountDetails.getBalance(), createdAccount.getBalance());
        assertEquals(accountDetails.getCreated_at(), createdAccount.getCreated_at());
        assertEquals(accountDetails.getUpdated_at(), createdAccount.getUpdated_at());
    }

    @Test
    public void depositTest() {
        when(accountDetailsRepository.findById(accountDetails.getAccount_no())).thenReturn(Optional.of(accountDetails));

        boolean result = accountDetailsService.deposit(accountDetails.getAccount_no(), 500.0);

        assertTrue(result);
        assertEquals(1500.0, accountDetails.getBalance());
    }

    @Test
    public void withdrawTest() {
        when(accountDetailsRepository.findById(accountDetails.getAccount_no())).thenReturn(Optional.of(accountDetails));

        boolean result = accountDetailsService.withdraw(accountDetails.getAccount_no(), 200.0);

        assertTrue(result);
        assertEquals(800.0, accountDetails.getBalance());
    }

    @Test
    public void withdrawInsufficientBalanceTest() {
        when(accountDetailsRepository.findById(accountDetails.getAccount_no())).thenReturn(Optional.of(accountDetails));

        boolean result = accountDetailsService.withdraw(accountDetails.getAccount_no(), 2000.0);

        assertFalse(result);
        assertEquals(1000.0, accountDetails.getBalance());
    }

    @Test
    public void balanceEnquiryTest() {
        when(accountDetailsRepository.findById(accountDetails.getAccount_no())).thenReturn(Optional.of(accountDetails));

        Double balance = accountDetailsService.balanceEnquiry(accountDetails.getAccount_no());

        assertEquals(accountDetails.getBalance(), balance);
    }

    @Test
    public void balanceEnquiryNonExistingAccountTest() {
        when(accountDetailsRepository.findById(accountDetails.getAccount_no())).thenReturn(Optional.empty());

        Double balance = accountDetailsService.balanceEnquiry(accountDetails.getAccount_no());

        assertNull(balance);
    }

    @Test
    public void deleteAccountTest() {
        when(accountDetailsRepository.findById(accountDetails.getAccount_no())).thenReturn(Optional.of(accountDetails));

        boolean result = accountDetailsService.deleteAccount(accountDetails.getAccount_no());

        assertTrue(result);
    }

    @Test
    public void deleteNonExistingAccountTest() {
        when(accountDetailsRepository.findById(accountDetails.getAccount_no())).thenReturn(Optional.empty());

        boolean result = accountDetailsService.deleteAccount(accountDetails.getAccount_no());

        assertFalse(result);
    }

    @Test
    public void getAccountsByCustomerIdTest() {
        List<AccountDetails> accountList = new ArrayList<>();
        accountList.add(accountDetails);

        when(accountDetailsRepository.findAllByCustomerId(1)).thenReturn(accountList);

        List<AccountDetails> accounts = accountDetailsService.getAccountsByCustomerId(1);

        assertEquals(1, accounts.size());
        assertEquals(accountDetails, accounts.get(0));
    }

    @Test
    public void getAccountTest() {
        when(accountDetailsRepository.findById(accountDetails.getAccount_no())).thenReturn(Optional.of(accountDetails));

        AccountDetails account = accountDetailsService.getAccount(accountDetails.getAccount_no());

        assertEquals(accountDetails, account);
    }

    @Test
    public void getNonExistingAccountTest() {
        when(accountDetailsRepository.findById(accountDetails.getAccount_no())).thenReturn(Optional.empty());

        AccountDetails account = accountDetailsService.getAccount(accountDetails.getAccount_no());

        assertNull(account);
    }
}

