package com.exampleMainService.repositories;

import com.exampleMainService.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    public Role findRoleByRole(String role);
}
