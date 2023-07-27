package com.ApiProject.Banking_Management.repository;

import com.ApiProject.Banking_Management.entity.AccountDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountDetailsRepository extends CrudRepository<AccountDetails, Long> {
    List<AccountDetails> findAllByCustomerId(int customer_id);

}
