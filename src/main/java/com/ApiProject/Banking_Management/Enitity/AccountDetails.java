package com.ApiProject.Banking_Management.Enitity;

import javax.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name ="AccountDetails")
public class AccountDetails {
    @Id
    private long account_no;

    private String account_type;
    private double balance;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDateTime created_at = LocalDateTime.now();

    private LocalDateTime updated_at = LocalDateTime.now();

    public AccountDetails(long account_no, String account_type, double balance, Customer customer, LocalDateTime created_at, LocalDateTime updated_at) {
        this.account_no = account_no;
        this.account_type = account_type;
        this.balance = balance;
        this.customer = customer;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public AccountDetails() {
    }

    public long getAccount_no() {
        return account_no;
    }

    public void setAccount_no(long account_no) {
        this.account_no = account_no;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
