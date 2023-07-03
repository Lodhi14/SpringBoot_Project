package com.ApiProject.Banking_Management.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Customer")
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
        private List<AccountDetails> accountDetails=new ArrayList<>();

        public Customer(int id, String first_name, String last_name, String address, String branch, String phone_no, String gender, String date_of_birth, String adhar_no) {
            this.id = id;
            this.first_name = first_name;
            this.last_name = last_name;
            this.address = address;
            this.branch = branch;
            this.phone_no = phone_no;
            this.gender = gender;
            this.date_of_birth = date_of_birth;
            this.adhar_no = adhar_no;
        }

        public Customer() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public String getPhone_no() {
            return phone_no;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDate_of_birth() {
            return date_of_birth;
        }

        public void setDate_of_birth(String date_of_birth) {
            this.date_of_birth = date_of_birth;
        }

        public String getAdhar_no() {
            return adhar_no;
        }

        public void setAdhar_no(String adhar_no) {
            this.adhar_no = adhar_no;
        }
}
