package com.exampleMainService.services;

import com.exampleMainService.entities.User;
import com.exampleMainService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthorityService authorityService;

    public User save(User user){
        return userRepository.save(user);
    }
    public List<User> users(){
        return userRepository.findAll();
    }
    public boolean deleteByUsername(String username){
        userRepository.deleteById(username);
        if(userRepository.findById(username).isEmpty()){
            authorityService.deleteByUsername(username);
            if(authorityService.findByUsername(username) != null)
                return true;
        }
        return false;
    }
}
