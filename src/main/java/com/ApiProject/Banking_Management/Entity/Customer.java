package com.ApiProject.Banking_Management.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Customer")
@Data
public class Customer {
        @Id
        private int id;
        private String first_name;
        private String last_name;
        private String address;
        private String branch;
        private String phone_no;
        private String gender;
        private String date_of_birth;
        private String adhar_no;
        @OneToMany(mappedBy = "customer")
        @JsonIgnore
        private List<AccountDetails> accountDetails=new ArrayList<>();
}
