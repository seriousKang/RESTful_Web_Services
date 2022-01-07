package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService userDaoService;

    public AdminUserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = userDaoService.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue jacksonValue = new MappingJacksonValue(users);
        jacksonValue.setFilters(filterProvider);

        return jacksonValue;
    }

//    @GetMapping("/v1/users/{id}")
//    @GetMapping(value="/users/{id}", params="version=1")
//    @GetMapping(value="/users/{id}", headers="X-API-VERSION=1")
    @GetMapping(value="/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
        User foundUser = userDaoService.findOne(id);

        if(foundUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // 지정된 필드만 JSON 반환
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue jacksonValue = new MappingJacksonValue(foundUser);
        jacksonValue.setFilters(filterProvider);

        return jacksonValue;
    }

//    @GetMapping("/v2/users/{id}")
//    @GetMapping(value="/users/{id}", params="version=2")
//    @GetMapping(value="/users/{id}", headers="X-API-VERSION=2")
    @GetMapping(value="/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        User foundUser = userDaoService.findOne(id);

        if(foundUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User → UserV2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(foundUser, userV2);
        userV2.setGrade("VIP");

        // 지정된 필드만 JSON 반환
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue jacksonValue = new MappingJacksonValue(userV2);
        jacksonValue.setFilters(filterProvider);

        return jacksonValue;
    }
}
