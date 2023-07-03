package com.ApiProject.Banking_Management.Controller;

import com.ApiProject.Banking_Management.Entity.AccountDetails;
import com.ApiProject.Banking_Management.Service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountDetailsController {
    @Autowired
    public AccountDetailsService accountServ;

    @PostMapping("/open")
    public ResponseEntity<AccountDetails> createAccount(@RequestBody AccountDetails accountDetails) {
        AccountDetails createdAccount = accountServ.createAccount(accountDetails);
        return ResponseEntity.ok(createdAccount);
    }

    @PostMapping("/deposit/{account_no}")
    public ResponseEntity<String> deposit(@PathVariable Long account_no, @RequestParam("amount") double amount){
        boolean success =accountServ.deposit(account_no,amount);
        if (success) {
            return ResponseEntity.ok("Deposit successful");
        } else {
            return ResponseEntity.badRequest().body("Account not found");
        }
    }

    @PostMapping("/withdraw/{account_no}")
    public ResponseEntity<String> withdraw(@PathVariable Long account_no, @RequestParam("amount") double amount){
        boolean success = accountServ.withdraw(account_no,amount);
        if (success) {
            return ResponseEntity.ok("Withdrawal successful");
        } else {
            return ResponseEntity.badRequest().body("Insufficient balance or account not found");
        }
    }

    @GetMapping("/balance/{account_no}")
    public ResponseEntity<String> balanceEnquiry(@PathVariable Long account_no)
    {
        Double balance = accountServ.balanceEnquiry(account_no);
        String bal=String.valueOf(balance);
        if (balance != null) {
            return ResponseEntity.ok(bal);
        } else {
            return ResponseEntity.badRequest().body("Account not found.");
        }
    }

//    @PutMapping("{account_no}/modify")
//    public ResponseEntity<AccountDetails> modifyAccount(@PathVariable Long account_no, @RequestBody AccountDetails updated){
//        AccountDetails modifiedAccount=accountServ.modifyAccount(account_no,updated);
//        if(modifiedAccount!=null){
//            return ResponseEntity.ok(modifiedAccount);
//        }
//        else
//        {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("/delete/{account_no}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long account_no)
    {
        boolean success = accountServ.deleteAccount(account_no);
        if (success) {
            return ResponseEntity.ok("Account deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Account not found");
        }
    }

    @GetMapping("accountDetails/customer/{customer_id}")
    public ResponseEntity<List<AccountDetails>> getAccountsByCustomerId (@PathVariable("customer_id") int customer_id)
    {
        List<AccountDetails> accountDetails=accountServ.getAccountsByCustomerId(customer_id);
        if(!accountDetails.isEmpty())
        {
            return ResponseEntity.ok(accountDetails);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("accountDetails/{account_no}")
    public AccountDetails getAccount (@PathVariable("account_no") long account_no)
    {
        return accountServ.getAccount(account_no);
    }

//    @DeleteMapping("customers/{account_no}/accounts")
//    public ResponseEntity<String> deleteCustomerAndAccount(@PathVariable("account_no") long account_no)
//    {
//        try {
//                accountServ.deleteAccountAndCustomer(account_no);
//                return ResponseEntity.ok("Account Deleted successfully.");
//            }
//            catch(RuntimeException e){
//                return ResponseEntity.badRequest().body(e.getMessage());
//            }
//    }
}
