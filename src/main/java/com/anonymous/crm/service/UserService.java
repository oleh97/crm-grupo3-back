package com.anonymous.crm.service;

import com.anonymous.crm.model.User;
import com.anonymous.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User login(User user) {

        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());

    }

}
