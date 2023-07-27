package com.ApiProject.Banking_Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReadDto {
    private String first_name;
    private String last_name;
    private String address;
}
