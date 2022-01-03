package com.example.restfulwebservice.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
public class UserController {
    private UserDaoService userDaoService;

    public UserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User foundUser = userDaoService.findOne(id);

        if(foundUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return foundUser;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("user = {}", user);

        User savedUser = userDaoService.save(user);

        // 특정 값을 포함한 `URI`를 전달해야하는 경우에 사용
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        // 201(`CREATED`) 상태 코드 설정
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User deletedUser = userDaoService.deleteById(id);
        if(deletedUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }
}
