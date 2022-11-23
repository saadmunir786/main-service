package com.exampleMainService.services;

import com.exampleMainService.entities.Role;
import com.exampleMainService.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthorityService authorityService;

    public Role save(Role role){
        return roleRepository.save(role);
    }
    public List<Role> roles(){
        return roleRepository.findAll();
    }
    public boolean delete(long id){
        roleRepository.deleteById(id);
        return roleRepository.existsById(id);
    }
    public Role findByRole(String role){
        List<Role> list = roleRepository.findAll();
        for (Role r:list) {
            if(r.getRole().equals(role))
                return r;
        }
        return new Role();
    }
}
