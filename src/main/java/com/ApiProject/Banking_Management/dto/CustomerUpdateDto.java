package com.ApiProject.Banking_Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateDto {
    @NotBlank(message = "Address can not be blank")
    private String address;

    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid mobile number format")
    private String phone_no;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
}
