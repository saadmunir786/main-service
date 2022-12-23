package com.exampleMainService.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Authenticator")
@Table(name = "authenticationKeys")
public class Authenticator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "secret", unique = true)
    private String secret;


    public Authenticator(){}
    public Authenticator(long id, String username, String email, String secret) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.secret = secret;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "Authenticator{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}
