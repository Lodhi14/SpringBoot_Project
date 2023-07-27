package com.ApiProject.Banking_Management.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsWriteDto {
    @NotNull(message ="Customer id can not be blank")
    private int customer_id;
    @Pattern(regexp = "(?i)^Savings|Current|Salary|Fixed Deposit|Recurring Deposit", message = "Invalid account type. Allowed values: Savings, Current, Salary, Fixed Deposit, Recurring Deposit")
    private String account_type;
    @NotNull
    @DecimalMin(value = "499.0", inclusive = false, message = "Balance must be greater or equal to 500")
    private double balance;
    @Pattern(regexp = "^[A-Za-z]+$", message = "Field must contain alphabets only")
    private String branch;

}
