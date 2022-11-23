package com.exampleMainService.controllers;

import com.exampleMainService.clients.EmployeeClient;
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
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    LoggerDBService loggerDBService;
    @Autowired
    EmployeeClient client;
    Crypto crypto = Crypto.getInstance();
    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @PutMapping("/save")
    @ResponseBody
    public String employeeSave(@RequestBody String json, Principal principal){
        String responseBody = null;
        json = json.replaceAll("\"", "");
        logger.info("JSON "+json);
        json = crypto.decrypt(json);
        logger.info("JSON "+json);

        try {
            responseBody = client.saveEmployee(json, principal.getName());
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return crypto.encrypt(responseBody);
    }
    @GetMapping("/get")
    @Cacheable(value = "employees")
    @ResponseBody
    public String employees(Principal principal){
        String responseBody = null;

        try {
            responseBody = client.getEmployees(principal.getName());
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return crypto.encrypt(responseBody);
    }
    @GetMapping("/get/{id}")
    @Cacheable(value = "employee", key = "#id")
    @ResponseBody
    public String getById(@PathVariable("id")long id, Principal principal){
        String responseBody = null;

        try {
            responseBody = client.getEmployeeById(id, principal.getName());
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return responseBody;
    }
    @PutMapping("/update")
    @ResponseBody
    public String updateEmployee(@RequestBody String json, Principal principal){
        String responseBody = null;
        json = crypto.decrypt(json);

        try {
            responseBody = client.updateEmployee(json, principal.getName());
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return crypto.encrypt(responseBody);
    }
    @PostMapping("/delete")
    @ResponseBody
    public String deleteEmployee(@RequestParam("id") String id, Principal principal){
        String responseBody = null;
        id = crypto.decrypt(id);
        long empId = Long.parseLong(id);

        try {
            responseBody = client.deleteEmployee(empId, principal.getName());
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return crypto.encrypt(responseBody);
    }
    @GetMapping("/getByDepartment/{name}")
    @Cacheable(value = "employeeByDepartment", key = "#name")
    @ResponseBody
    public String getByDepartmentName(@PathVariable("name")String name, Principal principal){
        String responseBody = null;

        try {
            responseBody = client.getEmployeeByDepartmentName(name, principal.getName());
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return responseBody;
    }
    @GetMapping("/getByPagination")
    @ResponseBody
    public String getByPagination(@RequestParam("page") int page, @RequestParam("size") int size, Principal principal){
        String responseBody = null;

        try {
            responseBody = client.getEmployeeByPagination(page, size, principal.getName());
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return responseBody;
    }
}
