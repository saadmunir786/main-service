package com.exampleMainService.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterUserFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(RegisterUserFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Inside doFilter method.");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username="";

        if (principal instanceof UserDetails) {
            username= ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        try {
            logger.info(username);
            String[] tokens = username.split("name=");
            String[] nameToken = tokens[2].trim().split(",");
            username = nameToken[0];
            HttpSession session = ((HttpServletRequest)servletRequest).getSession(true);
            session.setAttribute("username", username);
            logger.info(username);



        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
