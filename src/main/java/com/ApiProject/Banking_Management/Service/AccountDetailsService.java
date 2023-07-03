package com.ApiProject.Banking_Management.Service;

import com.ApiProject.Banking_Management.Entity.AccountDetails;
import com.ApiProject.Banking_Management.Repository.AccountDetailsRepository;
import com.ApiProject.Banking_Management.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDetailsService {
    @Autowired
    public AccountDetailsRepository accountDetailsRepo;
    public CustomerRepository customerRepo;
    public AccountDetails createAccount(AccountDetails accountDetails) {
        accountDetailsRepo.save(accountDetails);
        return accountDetails;
    }
    public boolean deposit(Long account_no, double amount) {
        AccountDetails accountDetails=accountDetailsRepo.findById(account_no).orElse(null);
        if(accountDetails!=null)
        {
            double currentBalance=accountDetails.getBalance();

            double newBalance = currentBalance+amount;
            accountDetails.setBalance(newBalance);
            accountDetailsRepo.save(accountDetails);
            return true;
        }
        return false;
    }
    public boolean withdraw(Long account_no, double amount) {
        AccountDetails accountDetails=accountDetailsRepo.findById(account_no).orElse(null);
        if(accountDetails!=null)
        {
            double currentBalance=accountDetails.getBalance();
            if (currentBalance>=amount)
            {
                double newBalance = currentBalance-amount;
                accountDetails.setBalance(newBalance);
                accountDetailsRepo.save(accountDetails);
                return true;
            }
        }
        return false;
    }
    public Double balanceEnquiry(Long account_no) {
        AccountDetails accountDetails=accountDetailsRepo.findById(account_no).orElse(null);
        if(accountDetails!=null)
        {
            return accountDetails.getBalance();
        }
        else {
            return null;
        }
    }
    //    public AccountDetails modifyAccount(Long account_no, AccountDetails updated) {
//        AccountDetails accountDetails=accountDetailsRepo.findById(account_no).orElse(null);
//        if(accountDetails!=null)
//        {
//            return accountDetailsRepo.save(updated);
//        }
//        else {
//            return null;
//        }
//    }
    public boolean deleteAccount(Long account_no) {
        AccountDetails account=accountDetailsRepo.findById(account_no).orElse(null);
        if(account!=null)
        {
            accountDetailsRepo.delete(account);
            return true;
        }
        return false;
    }

    public List<AccountDetails> getAccountsByCustomerId(int customer_id) {
        return accountDetailsRepo.findAllByCustomerId(customer_id);
    }

    public AccountDetails getAccount(long account_no) {
        AccountDetails accountDetails=accountDetailsRepo.findById(account_no).orElse(null);
        return accountDetails;
    }

//    public void deleteAccountAndCustomer(long account_no) {
//        AccountDetails accountToDelete=accountDetailsRepo.findById(account_no).orElseThrow(()-> new RuntimeException("Account not found."));
//        Customer customer=accountToDelete.getCustomer();
//        List<AccountDetails> accountDetails=customer.a
//        if(accountDetails.size() > 1)
//        {
//            accountDetailsRepo.deleteByCustomerId(customer_id);
//        } else if (accountCount==1) {
//            accountDetailsRepo.deleteByCustomerId(customer_id);
//            customerRepo.deleteById(customer_id);
//        }
//        else {
//            throw new RuntimeException("Customer does not have any accounts.");
//        }

}
