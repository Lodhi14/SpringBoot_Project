package com.ApiProject.Banking_Management.Repository;

import com.ApiProject.Banking_Management.Enitity.AccountDetails;
import com.ApiProject.Banking_Management.Enitity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.List;

@SpringJUnitConfig
@SpringBootTest
public class AccountDetailsRepositoryTest {

    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    @Test
    public void testFindAllByCustomerId() {
        // Create a test customer
        Customer customer = new Customer(1, "John", "Doe", "Address", "Branch", "1234567890", "Male", "2000-01-01", "123456789");

        // Create test account details
        AccountDetails accountDetails1 = new AccountDetails(1, "Savings", 1000.0, customer, LocalDateTime.now(), LocalDateTime.now());
        AccountDetails accountDetails2 = new AccountDetails(2, "Checking", 2000.0, customer, LocalDateTime.now(), LocalDateTime.now());

        // Save the account details
        accountDetailsRepository.save(accountDetails1);
        accountDetailsRepository.save(accountDetails2);

        // Retrieve account details by customer ID
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAllByCustomerId(1);

        // Assertions
        Assertions.assertEquals(4, accountDetailsList.size());
        Assertions.assertEquals("Savings", accountDetailsList.get(0).getAccount_type());
        Assertions.assertEquals("Checking", accountDetailsList.get(1).getAccount_type());

        // Clean up the test data
        accountDetailsRepository.delete(accountDetails1);
        accountDetailsRepository.delete(accountDetails2);
    }


}
