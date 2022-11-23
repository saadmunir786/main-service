package com.exampleMainService.controllers;

import com.exampleMainService.clients.EmailClient;
import com.exampleMainService.entities.Authority;
import com.exampleMainService.entities.User;
import com.exampleMainService.services.AuthorityService;
import com.exampleMainService.services.UserService;
import com.exampleMainService.utils.entities.Crypto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@CrossOrigin(origins = {"http://localhost:4000", "https://localhost:4000"})
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthorityService authorityService;
    @Value("${subject}")
    private String subject;
    @Value("${body}")
    private String body;
    @Value("${method}")
    private String method;
    @Value("${EMAIL_SERVICE_URL}")
    private String URL;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    Crypto crypto = Crypto.getInstance();
    Logger logger = com.exampleMainService.utils.entities.Logger.getInstance(UserController.class);

    @PutMapping("/save")
    @ResponseBody
    public String saveUser(@RequestParam("username")String username, @RequestParam("enabled")String enabled,
                     @RequestParam("password")String password, @RequestParam("email")String email, @RequestParam("authority")String role){
        username = crypto.decrypt(username);
        boolean isEnabled = Boolean.parseBoolean(crypto.decrypt(enabled));
        password = crypto.decrypt(password);
        email = crypto.decrypt(email);
        role = crypto.decrypt(role);

        User user = new User(username, passwordEncoder.encode(password), isEnabled);
        user.setEmail(email);
        Authority authority = new Authority(username, "ROLE_"+role.toUpperCase());


        logger.info(user.toString());
        logger.info(authority.toString());
        authorityService.save(authority);
        EmailClient emailClient = new EmailClient();
        String json = "{" +
                "\"to\": \""+user.getEmail()+"\"," +
                "\"subject\": \""+subject+"\"," +
                "\"body\": \""+body+"\"," +
                "\"method\": \""+method+"\"" +
                "}";
        emailClient.sendMail(json, URL, username);
        return crypto.encrypt(userService.save(user).toString());
    }
    @GetMapping("/getUsers")
    @ResponseBody
    public String getUsers(){
        List<User> list = userService.users();
        logger.info(list.toString());
        return crypto.encrypt(list.toString());
    }
    @PostMapping("/deleteUser")
    @ResponseBody
    public boolean deleteUser(@RequestParam("username") String username){
        username = crypto.decrypt(username);
        try {
            logger.info("Trying to delete user with username: " + username);
            return userService.deleteByUsername(username);
        }catch (Exception ex){
            return false;
        }
    }
    @PutMapping("/updateUser")
    @ResponseBody
    public String updateUser(@RequestParam("username")String username, @RequestParam("enabled")String enabled,
                     @RequestParam("password")String password,@RequestParam("email")String email, @RequestParam("authority")String role){
        username = crypto.decrypt(username);
        boolean isEnabled = Boolean.parseBoolean(crypto.decrypt(enabled));
        password = crypto.decrypt(password);
        email = crypto.decrypt(email);
        role = crypto.decrypt(role);

        User user = new User(username, passwordEncoder.encode(password), isEnabled);
        user.setEmail(email);
        Authority authority = new Authority(username, "ROLE_"+role);


        logger.info(user.toString());
        logger.info(authority.toString());
        authorityService.save(authority);
        return crypto.encrypt(userService.save(user).toString());
    }
    @DeleteMapping("/deleteAuthority/{username}")
    @ResponseBody
    public boolean deleteAuthority(@PathVariable("username") String username){
        try{
        logger.info("Trying to delete authority with username: "+username);
        return authorityService.deleteByUsername(username);
        }catch (Exception ex){
            return false;
        }
    }
    @PutMapping("/updateAuthority")
    @ResponseBody
    public Authority updateAuthority(@RequestParam("username")String username, @RequestParam("authority")String role){
        Authority authority = new Authority(username, "ROLE_"+role);


        logger.info(authority.toString());
        return authorityService.save(authority);
    }
    @GetMapping("/authority")
    @ResponseBody
    public String authority(@RequestParam("username")String username){
        username = crypto.decrypt(username);
        return crypto.encrypt(authorityService.findByUsername(username).toString());
    }
    @GetMapping("/user")
    @ResponseBody
    public Principal user(Principal principal){
        return principal;
    }
    @GetMapping("/validate")
    @ResponseBody
    public boolean validate(@RequestParam("encodedPassword")String encodedPassword, @RequestParam("password")String password){
        encodedPassword = crypto.decrypt(encodedPassword);
        password = crypto.decrypt(password);
        if(passwordEncoder.matches(password, encodedPassword))
            return true;
        return false;
    }
}
