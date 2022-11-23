package com.exampleMainService.controllers;

import com.exampleMainService.utils.entities.Crypto;
import com.exampleMainService.utils.services.LoggerDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins = {"http://localhost:4000", "https://localhost:4000"})
public class MainController {
    @Autowired
    LoggerDBService loggerDBService;
    Crypto crypto = Crypto.getInstance();

    @GetMapping("/")
    @ResponseBody
    public String index(HttpSession session){
        return "<h1 style='text-align:center;'>Welcome "+session.getAttribute("username")+"</h1>";
    }
    @GetMapping("/logs")
    @ResponseBody
    public String logs(HttpSession session){
        return crypto.encrypt(loggerDBService.logs().toString());
    }
    @PostMapping("/encrypt")
    @ResponseBody
    public String encrypt(@RequestParam("data")String data){
        Crypto c = Crypto.getInstance();
        return c.encrypt(data);
    }
    @PostMapping("/decrypt")
    @ResponseBody
    public String decrypt(@RequestParam("enData")String data){
        Crypto c = Crypto.getInstance();
        return c.decrypt(data);
    }
}
