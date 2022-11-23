package com.exampleMainService.configurations;

import com.exampleMainService.entities.Role;
import com.exampleMainService.services.RoleService;
import com.exampleMainService.utils.services.LoggerDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Autowired
    LoggerDBService loggerDBService;
    @Autowired
    RoleService roleService;
    Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        try {
            auth.jdbcAuthentication()
                    .dataSource(dataSource);
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<Role> allRoles = roleService.roles();
        List<String> roles = new ArrayList<String>();
        for (Role role:allRoles) {
            roles.add(role.getRole().replace("ROLE_",""));
        }
        String allowedRoles = roles.toString().replace("[", "").replace("]", "")
                .replace(" ", "");
        logger.info("Allowed Roles: "+allowedRoles);

        http.authorizeRequests()
                .antMatchers("/department/**").hasAnyRole(allowedRoles.split(","))
                .antMatchers("/employee/*").hasAnyRole(allowedRoles.split(","))
                .antMatchers("/user/validate").permitAll()
                .antMatchers("/user/**").hasAnyRole(allowedRoles.split(","))
                .antMatchers("/user/user").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable().cors().and().httpBasic().and().oauth2Login();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }
}
