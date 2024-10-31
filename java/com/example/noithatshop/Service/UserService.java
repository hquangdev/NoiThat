package com.example.noithatshop.Service;

import com.example.noithatshop.Model.User;
import com.example.noithatshop.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepo;

    public User finByUsername(String username){

        return userRepo.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
