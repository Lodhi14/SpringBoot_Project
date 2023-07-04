package com.ApiProject.Banking_Management.Controller;

import com.ApiProject.Banking_Management.Entity.AccountDetails;
import com.ApiProject.Banking_Management.Service.AccountDetailsService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class AccountDetailsController {
    private Logger logger= LoggerFactory.getLogger(AccountDetailsController.class);
    @Autowired
    public AccountDetailsService accountServ;

    @PostMapping("/open")
    public ResponseEntity<AccountDetails> createAccount(@RequestBody AccountDetails accountDetails) {
        logger.info("Creating account: {}", accountDetails);
        AccountDetails createdAccount = accountServ.createAccount(accountDetails);
        return ResponseEntity.ok(createdAccount);
    }

    @PostMapping("/deposit/{account_no}")
    public ResponseEntity<String> deposit(@PathVariable Long account_no, @RequestParam("amount") double amount){
        logger.info("Depositing amount {} into account {}", amount, account_no);
        boolean success =accountServ.deposit(account_no,amount);
        if (success) {
            logger.info("Deposit successful");
            return ResponseEntity.ok("Deposit successful");
        } else {
            logger.warn("Account not found");
            return ResponseEntity.badRequest().body("Account not found");
        }
    }

    @PostMapping("/withdraw/{account_no}")
    public ResponseEntity<String> withdraw(@PathVariable Long account_no, @RequestParam("amount") double amount){
        logger.info("Withdrawing amount {} from account {}",amount,account_no);
        boolean success = accountServ.withdraw(account_no,amount);
        if (success) {
            logger.info("Withdrawal successful");
            return ResponseEntity.ok("Withdrawal successful");
        } else {
            logger.warn("Insufficient balance or account not found");
            return ResponseEntity.badRequest().body("Insufficient balance or account not found");
        }
    }

    @GetMapping("/balance/{account_no}")
    public ResponseEntity<String> balanceEnquiry(@PathVariable Long account_no)
    {
        logger.info("Checking balance for account {}", account_no);
        Double balance = accountServ.balanceEnquiry(account_no);
        String bal=String.valueOf(balance);
        if (balance != null) {
            logger.info("Balance: {}", balance);
            return ResponseEntity.ok(bal);
        } else {
            logger.warn("Account not found.");
            return ResponseEntity.badRequest().body("Account not found.");
        }
    }

    @DeleteMapping("/delete/{account_no}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long account_no)
    {
        logger.info("Deleting account: {}", account_no);
        boolean success = accountServ.deleteAccount(account_no);
        if (success) {
            logger.info("Account deleted successfully");
            return ResponseEntity.ok("Account deleted successfully");
        } else {
            logger.warn("Account not found");
            return ResponseEntity.badRequest().body("Account not found");
        }
    }

    @GetMapping("accountDetails/customer/{customer_id}")
    public ResponseEntity<List<AccountDetails>> getAccountsByCustomerId (@PathVariable("customer_id") int customer_id)
    {
        logger.info("Fetching account details for customer with ID: {}", customer_id);
        List<AccountDetails> accountDetails=accountServ.getAccountsByCustomerId(customer_id);
        if(!accountDetails.isEmpty())
        {
            logger.info("Account details found.");
            return ResponseEntity.ok(accountDetails);
        }
        else {
            logger.warn("No account details found.");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("accountDetails/{account_no}")
    public AccountDetails getAccount (@PathVariable("account_no") long account_no)
    {
        logger.info("Fetching account details for account number: {}", account_no);
        return accountServ.getAccount(account_no);
    }

}
