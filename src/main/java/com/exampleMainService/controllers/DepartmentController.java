package com.exampleMainService.controllers;

import com.exampleMainService.clients.DepartmentClient;
import com.exampleMainService.utils.entities.Crypto;
import com.exampleMainService.utils.services.LoggerDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@CrossOrigin(origins = {"http://localhost:4000", "https://localhost:4000"})
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    LoggerDBService loggerDBService;
    @Autowired
    DepartmentClient client;
    Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    Crypto crypto = Crypto.getInstance();
    @PutMapping("/save")
    @ResponseBody
    public String departmentSave(@RequestBody String json, Principal principal){
        json = json.replaceAll("\"", "");
        logger.info("JSON "+json);
        json = crypto.decrypt(json);
        logger.info("JSON "+json);
        String responseBody = null;
        logger.info("Here "+principal.getName());


        try {
            responseBody = client.saveDepartment(json, principal.getName());
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return crypto.encrypt(responseBody);
    }
    @GetMapping("/get")
    @Cacheable("departments")
    @ResponseBody
    public String departments(Principal principal){
        String responseBody = null;

        try {
            responseBody = client.getDepartment(principal.getName());
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return crypto.encrypt(responseBody);
    }
    @GetMapping("/getByName/{name}")
    @Cacheable(value = "department", key = "#name")
    @ResponseBody
    public String getByName(@PathVariable("name")String name, Principal principal){
        String responseBody = null;
        name = crypto.decrypt(name);

        try {
            responseBody = client.getDepartmentByName(name, principal.getName());
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return crypto.encrypt(responseBody);
    }
    @GetMapping("/get/{id}")
    @Cacheable(value = "departmentById", key = "#id")
    @ResponseBody
    public String getById(@PathVariable("id")long id, Principal principal){
        String responseBody = null;
        id = Long.parseLong(crypto.decrypt(Long.toString(id)));

        try {
            responseBody = client.getDepartmentById(id, principal.getName());
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return crypto.encrypt(responseBody);
    }
    @PutMapping("/update")
    @ResponseBody
    public String updateDepartment(@RequestBody String json, Principal principal){
        String responseBody = null;
        json = crypto.decrypt(json);

        try {
            responseBody = client.updateDepartment(json, principal.getName());
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return crypto.encrypt(responseBody);
    }
    @PostMapping("/delete")
    @ResponseBody
    public String deleteDepartment(@RequestParam("id")String id, Principal principal){
        String responseBody = null;
        id = crypto.decrypt(id);
        long deptId = Long.parseLong(id);
        try {
            responseBody = client.deleteDepartment(deptId, principal.getName());
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return crypto.encrypt(responseBody);
    }

}
