package com.ApiProject.Banking_Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsUpdateDto {
    @Pattern(regexp = "(?i)^Savings|Current|Salary|Fixed Deposit|Recurring Deposit", message = "Invalid account type. Allowed values: Savings, Current, Salary, Fixed Deposit, Recurring Deposit")
    private String account_type;
    @Pattern(regexp = "^[A-Za-z]+$", message = "Field must contain alphabets only")
    private String branch;
}
