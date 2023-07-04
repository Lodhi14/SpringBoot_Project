package com.ApiProject.Banking_Management.Repository;

import com.ApiProject.Banking_Management.Entity.AccountDetails;
import com.ApiProject.Banking_Management.Entity.Customer;
import com.ApiProject.Banking_Management.Repository.AccountDetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test_application.properties")
public class AccountDetailsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    @Test
    public void testFindAllByCustomerId() {
        // Create a test customer
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirst_name("John");
        customer.setLast_name("Doe");
        customer.setAddress("Address");
        customer.setBranch("Branch");
        customer.setPhone_no("1234567890");
        customer.setGender("Male");
        customer.setDate_of_birth("2000-01-01");
        customer.setAdhar_no("123456789");

        // Create test account details
        AccountDetails accountDetails1 = new AccountDetails();
        accountDetails1.setAccount_no(1);
        accountDetails1.setAccount_type("Savings");
        accountDetails1.setBalance(1000.0);
        accountDetails1.setCustomer(customer);
        accountDetails1.setCreated_at(LocalDateTime.now());
        accountDetails1.setUpdated_at(LocalDateTime.now());

        AccountDetails accountDetails2 = new AccountDetails();
        accountDetails2.setAccount_no(2);
        accountDetails2.setAccount_type("Checking");
        accountDetails2.setBalance(2000.0);
        accountDetails2.setCustomer(customer);
        accountDetails2.setCreated_at(LocalDateTime.now());
        accountDetails2.setUpdated_at(LocalDateTime.now());

        // Save the account details
        entityManager.persistAndFlush(customer);
        entityManager.persistAndFlush(accountDetails1);
        entityManager.persistAndFlush(accountDetails2);

        // Retrieve account details by customer ID
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAllByCustomerId(1);

        // Assertions
        Assertions.assertEquals(2, accountDetailsList.size());
    }
}
