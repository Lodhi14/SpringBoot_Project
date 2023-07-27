package com.ApiProject.Banking_Management.repository;
import com.ApiProject.Banking_Management.entity.AccountDetails;
import com.ApiProject.Banking_Management.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
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
        Customer customer = new Customer();
        customer.setId(1);
        customer=entityManager.merge(customer);
        entityManager.flush();

        AccountDetails accountDetails1 = new AccountDetails();
        accountDetails1.setAccount_type("Savings");
        accountDetails1.setBalance(1000.0);
        accountDetails1.setCustomer(customer);
        accountDetailsRepository.save(accountDetails1);

        AccountDetails accountDetails2 = new AccountDetails();
        accountDetails2.setAccount_type("Checking");
        accountDetails2.setBalance(2000.0);
        accountDetails2.setCustomer(customer);
        accountDetailsRepository.save(accountDetails2);

        entityManager.persist(accountDetails1);
        entityManager.persist(accountDetails2);
        entityManager.flush();

        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAllByCustomerId(customer.getId());
        Assertions.assertFalse(accountDetailsList.isEmpty());
        Assertions.assertEquals(2, accountDetailsList.size());
    }
}
