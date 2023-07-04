package com.ApiProject.Banking_Management.Entity;
import lombok.Data;

import javax.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name ="AccountDetails")
@Data
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
}
