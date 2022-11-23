package com.exampleMainService.entities;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Role")
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private long id;
    @Column(name = "role")
    private String role;
    @Column(name = "permissions")
    private String permissions;
    public Role(){}
    public Role(int id, String role) {
        this.role = role;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "{" +
                "'id':'" + id +"'"+
                ", 'role':'" + role + '\'' +
                ", 'permissions':'" + permissions + '\'' +
                '}';
    }
}
