package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonFilter("UserInfo") // 필터 ID
public class User {
    private Integer id;
    @Size(min = 2, message = "Enter at least 2 characters.")
    private String name;
    @Past
    private Date joinDate;
    private String password;
    private String ssn;
}
