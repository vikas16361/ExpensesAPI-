package com.vikas.TrackerAPI.controller;

import com.vikas.TrackerAPI.entity.User;
import com.vikas.TrackerAPI.model.UserModel;
import com.vikas.TrackerAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;



    @GetMapping("/users/{id}")
    public ResponseEntity<User> readByUser(@PathVariable Long id) {
        return new ResponseEntity<User>(userService.readUser(id), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        return new ResponseEntity<User>(userService.updateUser(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
         userService.deleteUser(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
