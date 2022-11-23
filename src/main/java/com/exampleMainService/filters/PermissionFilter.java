package com.exampleMainService.filters;

import com.exampleMainService.entities.Role;
import com.exampleMainService.services.RoleService;
import com.exampleMainService.utils.entities.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
public class PermissionFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(PermissionFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Inside doFilter method.");
        AppContext context = AppContext.getInstance();
        RoleService roleService = context.getContext().getBean(RoleService.class);
        try {
            String method = ((HttpServletRequest)servletRequest).getMethod();
            String role = null;
            logger.info("Request method: "+method);

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {
                for (GrantedAuthority authority:((UserDetails) principal).getAuthorities()) {
                    role = authority.getAuthority();
                };
            }
            logger.info(role);
            Role userRole = roleService.findByRole(role);
            boolean isValidPermission = false;
            String[] permissions = userRole.getPermissions().split(",");
            for (String permission:permissions) {
                if(permission.equals(method))
                    isValidPermission = true;
            }
            if(isValidPermission==false){
                HttpServletResponse response = (HttpServletResponse)servletResponse;
                response.sendError(403,"You are not have the permission to do that.");
                return;
            }


        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
