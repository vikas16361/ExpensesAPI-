package com.vikas.TrackerAPI.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserModel {
    @NotBlank(message  ="name should not be blank")
    private String name;
    @NotNull(message = "email should not be empty")
    @Email(message = "enter valid email id")
    private String email;
    @NotNull(message = "password should be empty")
    @Size(min = 5,message = "atleast 5 character")
    private String password;
    private Long age = 0L;


}
