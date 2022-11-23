package com.exampleMainService.services;

import com.exampleMainService.entities.Authority;
import com.exampleMainService.repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorityService {
    @Autowired
    AuthorityRepository authorityRepository;

    public Authority save(Authority authority){
        return authorityRepository.save(authority);
    }
    public List<Authority> authorities(){
        return authorityRepository.findAll();
    }
    public boolean deleteByUsername(String username){
        authorityRepository.deleteById(username);
        if(authorityRepository.findById(username).isEmpty()){
            return true;
        }
        return false;
    }
    public Authority findByUsername(String username){
        Authority authority = new Authority();
        Optional<Authority> optionalAuthority = authorityRepository.findById(username);
        if(optionalAuthority.isPresent())
            authority = optionalAuthority.get();
        return authority;
    }
}
