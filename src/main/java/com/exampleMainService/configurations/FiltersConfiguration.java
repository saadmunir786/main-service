package com.exampleMainService.configurations;

import com.exampleMainService.filters.PermissionFilter;
import com.exampleMainService.filters.RegisterUserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FiltersConfiguration {

    //This will bind our filter class to with the given url pattern.
    @Bean
    public FilterRegistrationBean<RegisterUserFilter> registrationBean(){
        FilterRegistrationBean<RegisterUserFilter> registrationBean = new FilterRegistrationBean<RegisterUserFilter>();
        registrationBean.setFilter(new RegisterUserFilter());
        registrationBean.addUrlPatterns("/");
        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean<PermissionFilter> permissionBean(){
        FilterRegistrationBean<PermissionFilter> registrationBean = new FilterRegistrationBean<PermissionFilter>();
        registrationBean.setFilter(new PermissionFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
