package com.exampleMainService.entities;

import javax.persistence.*;

@Entity(name = "User")
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private boolean isEnabled;
    @Column(name = "email")
    private String email;

    public User(){
        isEnabled = true;
        username = password = email = null;
    }

    public User(String username, String password, boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{" +
                "'username':'" + username + '\'' +
                ", 'password':'" + password + '\'' +
                ", 'isEnabled':" + isEnabled +
                ", 'email':'" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        User user = (User)obj;
        if(this.isEnabled == user.isEnabled && this.password == user.password && this.username == user.username && this.email == user.email)
            return true;
        return false;
    }
}
