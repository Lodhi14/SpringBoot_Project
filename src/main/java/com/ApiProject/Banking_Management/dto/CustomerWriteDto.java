package com.ApiProject.Banking_Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerWriteDto {
    @NotBlank(message = "First name shouldn't be null")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "First name must contain only alphabets and spaces")
    private String first_name;

    @NotBlank(message = "Last name shouldn't be null")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Last name must contain only alphabets and spaces")
    private String last_name;

    @NotBlank(message = "Address can not be blank")
    private String address;

    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid mobile number format")
    private String phone_no;

    @Pattern(regexp = "^(Male|Female|Other)$", message = "Invalid gender value. Must be one of 'Male', 'Female', or 'Other'")
    private String gender;

    @NotNull(message = "Date of birth must not be null")
    @Past(message = "Date of birth must be in the past")
    private Date date_of_birth;

    @Pattern(regexp = "^[2-9][0-9]{11}$", message = "Invalid aadhaar number format")
    private String aadhaar_no;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

}
