package com.exampleMainService.controllers;

import com.exampleMainService.entities.Role;
import com.exampleMainService.services.RoleService;
import com.exampleMainService.utils.entities.Crypto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = {"http://localhost:4000", "https://localhost:4000"})
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    Crypto crypto = Crypto.getInstance();

    Logger logger = com.exampleMainService.utils.entities.Logger.getInstance(RoleController.class);

   @GetMapping("/roles")
   @ResponseBody
   public String roles(){
       return crypto.encrypt(roleService.roles().toString());
   }
   @PutMapping("/create")
   @ResponseBody
   public String create(@RequestParam("role")String name, @RequestParam("permissions")String permissions){
       name = crypto.decrypt(name);
       permissions = crypto.decrypt(permissions);
       Role role = new Role(0, name);
       role.setPermissions(permissions);

//       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//       List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
//       updatedAuthorities.add(new SimpleGrantedAuthority(name));
//       Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
//
//       SecurityContextHolder.getContext().setAuthentication(newAuth);

       return crypto.encrypt(roleService.save(role).toString());
   }
    @PutMapping("/update")
    @ResponseBody
    public String update(@RequestParam("id")String id, @RequestParam("role")String name, @RequestParam("permissions")String permissions){
       id = crypto.decrypt(id);
       name = crypto.decrypt(name);
       permissions = crypto.decrypt(permissions);

       int roleId = Integer.parseInt(id);
        Role role = new Role(roleId, name);
        role.setPermissions(permissions);
        return crypto.encrypt(roleService.save(role).toString());
    }
    @PostMapping("/delete")
    @ResponseBody
    public boolean delete(@RequestParam("id")String id){
       id = crypto.decrypt(id);
       long roleId = Long.parseLong(id);
        return !roleService.delete(roleId);
    }
    @GetMapping("/role")
    @ResponseBody
    public String getRole(@RequestParam("role")String role){
        role = crypto.decrypt(role);
        return crypto.encrypt(roleService.findByRole(role).toString());
    }

}
