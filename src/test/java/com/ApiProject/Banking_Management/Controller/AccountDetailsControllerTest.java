package com.ApiProject.Banking_Management.Controller;
import com.ApiProject.Banking_Management.Enitity.AccountDetails;
import com.ApiProject.Banking_Management.Service.AccountDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountDetailsController.class)
public class AccountDetailsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountDetailsService accountDetailsService;

    @Test
    public void createAccountTest() throws Exception {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccount_no(1L);
        accountDetails.setAccount_type("Savings");
        accountDetails.setBalance(1000.0);
        accountDetails.setCreated_at(LocalDateTime.now());
        accountDetails.setUpdated_at(LocalDateTime.now());

        when(accountDetailsService.createAccount(any(AccountDetails.class))).thenReturn(accountDetails);

        mockMvc.perform(MockMvcRequestBuilders.post("/open")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account_no\": 1, \"account_type\": \"Savings\", \"balance\": 1000.0}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.account_no").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.account_type").value("Savings"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(1000.0));
    }

    @Test
    public void depositTest() throws Exception {
        when(accountDetailsService.deposit(1L, 500.0)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/deposit/{account_no}", 1)
                        .param("amount", "500.0"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Deposit successful"));
    }

    @Test
    public void withdrawTest() throws Exception {
        when(accountDetailsService.withdraw(1L, 200.0)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/withdraw/{account_no}", 1)
                        .param("amount", "200.0"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Withdrawal successful"));
    }

    @Test
    public void balanceEnquiryTest() throws Exception {
        when(accountDetailsService.balanceEnquiry(1L)).thenReturn(1000.0);

        mockMvc.perform(MockMvcRequestBuilders.get("/balance/{account_no}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1000.0"));
    }

    @Test
    public void deleteAccountTest() throws Exception {
        when(accountDetailsService.deleteAccount(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/{account_no}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Account deleted successfully"));
    }

    @Test
    public void getAccountsByCustomerIdTest() throws Exception {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccount_no(1L);
        accountDetails.setAccount_type("Savings");
        accountDetails.setBalance(1000.0);
        accountDetails.setCreated_at(LocalDateTime.now());
        accountDetails.setUpdated_at(LocalDateTime.now());

        List<AccountDetails> accountList = new ArrayList<>();
        accountList.add(accountDetails);

        when(accountDetailsService.getAccountsByCustomerId(1)).thenReturn(accountList);

        mockMvc.perform(MockMvcRequestBuilders.get("/accountDetails/customer/{customer_id}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].account_no").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].account_type").value("Savings"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].balance").value(1000.0));
    }

    @Test
    public void getAccountTest() throws Exception {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccount_no(1L);
        accountDetails.setAccount_type("Savings");
        accountDetails.setBalance(1000.0);
        accountDetails.setCreated_at(LocalDateTime.now());
        accountDetails.setUpdated_at(LocalDateTime.now());

        when(accountDetailsService.getAccount(1L)).thenReturn(accountDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/accountDetails/{account_no}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.account_no").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.account_type").value("Savings"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(1000.0));
    }
}

