package com.ApiProject.Banking_Management.Repository;

import com.ApiProject.Banking_Management.Enitity.AccountDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountDetailsRepository extends CrudRepository<AccountDetails, Long> {
    List<AccountDetails> findAllByCustomerId(int customer_id);

}
