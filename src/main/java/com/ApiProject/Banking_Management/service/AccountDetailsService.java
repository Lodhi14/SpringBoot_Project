package com.ApiProject.Banking_Management.service;

import com.ApiProject.Banking_Management.dto.AccountDetailsUpdateDto;
import com.ApiProject.Banking_Management.entity.AccountDetails;
import com.ApiProject.Banking_Management.entity.Customer;
import com.ApiProject.Banking_Management.exceptions.InsufficientBalanceException;
import com.ApiProject.Banking_Management.exceptions.InvalidArgumentException;
import com.ApiProject.Banking_Management.exceptions.ResourceNotFoundException;
import com.ApiProject.Banking_Management.repository.AccountDetailsRepository;
import com.ApiProject.Banking_Management.repository.CustomerRepository;
import com.ApiProject.Banking_Management.dto.AccountDetailsWriteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
import java.util.List;

@Service
public class AccountDetailsService {
    @Autowired
    public AccountDetailsRepository accountDetailsRepo;
    @Autowired
    public CustomerRepository customerRepo;

    private AccountDetailsWriteDto convertToWriteDto(AccountDetails accountDetails)
    {
        AccountDetailsWriteDto writeDto=new AccountDetailsWriteDto();
        writeDto.setCustomer_id(accountDetails.getCustomer().getId());
        writeDto.setAccount_type(accountDetails.getAccount_type());
        writeDto.setBalance(accountDetails.getBalance());
        writeDto.setBranch(accountDetails.getBranch());
        return writeDto;
    }
    public AccountDetailsWriteDto createAccount( AccountDetailsWriteDto accountDetailsWriteDto) {
        Customer customer= customerRepo.findById(accountDetailsWriteDto.getCustomer_id()).orElseThrow(()->new ResourceNotFoundException("Customer not found with id"+accountDetailsWriteDto.getCustomer_id()));
        AccountDetails newAccount= new AccountDetails();

        newAccount.setAccount_type(accountDetailsWriteDto.getAccount_type());
        newAccount.setBalance(accountDetailsWriteDto.getBalance());
        newAccount.setBranch(accountDetailsWriteDto.getBranch());
        newAccount.setCustomer(customer);
        newAccount=accountDetailsRepo.save(newAccount);

        return convertToWriteDto(newAccount);

    }
    public AccountDetails deposit(Long account_no, double amount) {
        if(amount<=0)
        {
            throw new InvalidArgumentException(" Deposit amount can not be less than or equal to 0");
        }
        AccountDetails accountDetails=accountDetailsRepo.findById(account_no).orElseThrow(()->new ResourceNotFoundException("Account not found."));
        accountDetails.setBalance(accountDetails.getBalance()+amount);
       return accountDetailsRepo.save(accountDetails);
    }
    public AccountDetails withdraw(Long account_no, double amount) {
        AccountDetails accountDetails = accountDetailsRepo.findById(account_no).orElseThrow(()->new ResourceNotFoundException("Account not found."));
        if(amount<=0)
        {
            throw new InvalidArgumentException(" Withdraw amount can not be less than or equal to 0");
        }
            double currentBalance = accountDetails.getBalance();
            if (currentBalance < amount) {
                throw new InsufficientBalanceException("Insufficient Balance");
            }
            else {
                accountDetails.setBalance(accountDetails.getBalance()-amount);
                return accountDetailsRepo.save(accountDetails);
            }
    }
    public Double balanceEnquiry(Long account_no) {
        AccountDetails accountDetails=accountDetailsRepo.findById(account_no).orElseThrow(()->new ResourceNotFoundException("Account not found."));
            return accountDetails.getBalance();
    }

    public AccountDetails getAccount(long account_no) {
        AccountDetails accountDetails=accountDetailsRepo.findById(account_no).orElseThrow(()->new ResourceNotFoundException("Account not found."));
        return accountDetails;
    }

    public void deleteAccount(Long account_no) {
        AccountDetails account=accountDetailsRepo.findById(account_no).orElseThrow(()->new ResourceNotFoundException("Account not found."));
            accountDetailsRepo.deleteById(account_no);
    }

    public List<AccountDetails> getAccountsByCustomerId(int customer_id) {
        Customer customer=customerRepo.findById(customer_id).orElseThrow(()->new ResourceNotFoundException("No customer exist with id: "+ customer_id));
        return accountDetailsRepo.findAllByCustomerId(customer_id);
    }


    public AccountDetails updateAccountDetails(Long account_no, @Valid AccountDetailsUpdateDto accountDetailsUpdateDto) {
        AccountDetails accountDetails=accountDetailsRepo.findById(account_no).orElseThrow(()->new ResourceNotFoundException("Account not found"));
        accountDetails.setBranch(accountDetailsUpdateDto.getBranch());
        accountDetails.setAccount_type(accountDetailsUpdateDto.getAccount_type());
        accountDetails=accountDetailsRepo.save(accountDetails);
        return accountDetails;
    }
}
