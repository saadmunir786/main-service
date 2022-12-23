package com.exampleMainService.services;

import com.exampleMainService.entities.Authenticator;
import com.exampleMainService.entities.User;
import com.exampleMainService.repositories.AuthenticatorRepository;
import com.exampleMainService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticatorService {
    @Autowired
    AuthenticatorRepository authenticatorRepository;

    public Authenticator save(Authenticator authenticator){
        return authenticatorRepository.save(authenticator);
    }
    public Authenticator getAuthenticatorByEmail(String email){
        return authenticatorRepository.findAuthenticatorByEmail(email);
    }
    public Authenticator getAuthenticatorByUsername(String username){
        return authenticatorRepository.findAuthenticatorByUsername(username);
    }
    public Authenticator getAuthenticatorBySecret(String secret){
        return authenticatorRepository.findAuthenticatorBySecret(secret);
    }
}
