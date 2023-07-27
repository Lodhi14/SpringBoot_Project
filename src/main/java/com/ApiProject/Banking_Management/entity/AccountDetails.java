package com.ApiProject.Banking_Management.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@Entity
@Table(name ="AccountDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetails {
    @Id
    @GeneratedValue
    private long account_no;

    private String account_type;
    private double balance;
    private String branch;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
