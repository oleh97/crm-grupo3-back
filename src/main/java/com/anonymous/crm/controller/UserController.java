package com.anonymous.crm.controller;

import com.anonymous.crm.model.User;
import com.anonymous.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public @ResponseBody
    ResponseEntity<User> loginUser(@RequestBody User user) {
        User loggedUser = userService.login(user);
        if (loggedUser != null) {
            return new ResponseEntity<>(loggedUser, HttpStatus.OK);
        }
        else return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
    }

}
