package com.exampleMainService.repositories;

import com.exampleMainService.entities.Authenticator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthenticatorRepository extends JpaRepository<Authenticator, Long>{
    public Authenticator findAuthenticatorByEmail(String email);
    public Authenticator findAuthenticatorByUsername(String username);
    public Authenticator findAuthenticatorBySecret(String secret);
}
