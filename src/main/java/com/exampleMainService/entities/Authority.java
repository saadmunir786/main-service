package com.exampleMainService.entities;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Authority")
@Table(name = "authorities")
public class Authority {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "authority")
    private String authority;

    public Authority(){
        username = authority = null;
    }

    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "{" +
                "'username':'" + username + '\'' +
                ", 'authority':'" + authority + '\'' +
                '}';
    }
}
