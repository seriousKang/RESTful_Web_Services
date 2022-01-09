package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "Domain object for user details")
public class User {
    private Integer id;
    @Size(min = 2, message = "Enter at least 2 characters.")
    @ApiModelProperty(notes = "Please enter your name")
    private String name;
    @Past
    @ApiModelProperty(notes = "Please enter your join date")
    private Date joinDate;
    @ApiModelProperty(notes = "Please enter your password")
    private String password;
    @ApiModelProperty(notes = "Please enter your social security number")
    private String ssn;
}
