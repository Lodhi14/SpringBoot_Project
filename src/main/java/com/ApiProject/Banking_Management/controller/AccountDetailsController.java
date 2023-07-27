package com.ApiProject.Banking_Management.controller;
import com.ApiProject.Banking_Management.dto.AccountDetailsUpdateDto;
import com.ApiProject.Banking_Management.entity.AccountDetails;
import com.ApiProject.Banking_Management.service.AccountDetailsService;
import com.ApiProject.Banking_Management.dto.AccountDetailsWriteDto;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountDetailsController {

    private Logger logger= LoggerFactory.getLogger(AccountDetailsController.class);

    @Autowired
    public AccountDetailsService accountServ;

    @PostMapping("/open")
    public ResponseEntity<String> createAccount(@RequestBody @Valid AccountDetailsWriteDto accountDetailsWriteDto) {
        logger.info("Creating account: {}", accountDetailsWriteDto);
        accountServ.createAccount(accountDetailsWriteDto);
        return ResponseEntity.ok("Account created successfully");
    }

    @PostMapping("/deposit/{account_no}")
    public ResponseEntity<String> deposit(@PathVariable Long account_no, @RequestParam("amount") double amount){
        logger.info("Depositing amount {} into account {}", amount, account_no);
        AccountDetails accountDetails=accountServ.deposit(account_no,amount);
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/withdraw/{account_no}")
    public ResponseEntity<String> withdraw(@PathVariable Long account_no, @RequestParam("amount") double amount){
        logger.info("Withdrawing amount {} from account {}",amount,account_no);
        AccountDetails accountDetails= accountServ.withdraw(account_no,amount);
        return ResponseEntity.ok("Withdrawal successful");
    }

    @GetMapping("/balance/{account_no}")
    public ResponseEntity<String> balanceEnquiry(@PathVariable Long account_no) {
        logger.info("Checking balance for account {}", account_no);
        Double balance = accountServ.balanceEnquiry(account_no);
        String bal=String.valueOf(balance);
        return ResponseEntity.ok(bal);
    }
    @GetMapping("accountDetails/{account_no}")
    public ResponseEntity<AccountDetails>getAccount (@PathVariable("account_no") long account_no) {
        logger.info("Fetching account details for account number: {}", account_no);
        AccountDetails accountDetails = accountServ.getAccount(account_no);
        return ResponseEntity.ok(accountDetails);
    }
    @DeleteMapping("/delete/{account_no}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long account_no) {
        logger.info("Deleting account: {}", account_no);
        accountServ.deleteAccount(account_no);
        logger.info("Account deleted successfully");
        return ResponseEntity.ok("Account deleted successfully");
    }
    @GetMapping("accountDetails/customer/{customer_id}")
    public ResponseEntity<List<AccountDetails>> getAccountsByCustomerId (@PathVariable("customer_id") int customer_id) {
        logger.info("Fetching account details for customer with ID: {}", customer_id);
        List<AccountDetails> accountDetails=accountServ.getAccountsByCustomerId(customer_id);
        logger.info("All accounts found");
        return ResponseEntity.ok(accountDetails);
    }

    @PutMapping("/update/{account_no}")
    public ResponseEntity<String> updateAccountDetails(@PathVariable Long account_no, @RequestBody @Valid AccountDetailsUpdateDto accountDetailsUpdateDto)
    {
        logger.info("Updating account details with ID: {}",account_no);
        AccountDetails accountDetails=accountServ.updateAccountDetails(account_no,accountDetailsUpdateDto);
        return ResponseEntity.ok().body("Account details updated successfully");
    }
}
