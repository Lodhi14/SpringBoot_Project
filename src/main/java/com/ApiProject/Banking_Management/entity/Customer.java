package com.ApiProject.Banking_Management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Customer")
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Customer {
        @Id
        @GeneratedValue
        private int id;
        private String first_name;
        private String last_name;
        private String address;
        private String phone_no;
        private String gender;
        private Date date_of_birth;
        private String aadhaar_no;
        private String email;
        @OneToMany(mappedBy = "customer")
        @JsonIgnore
        private List<AccountDetails> accountDetails=new ArrayList<>();


}
