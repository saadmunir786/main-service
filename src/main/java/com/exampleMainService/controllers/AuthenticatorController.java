package com.exampleMainService.controllers;

import com.exampleMainService.constants.constants;
import com.exampleMainService.entities.Authenticator;
import com.exampleMainService.services.AuthenticatorService;
import com.exampleMainService.utils.entities.AuthenticatorUtils;
import com.exampleMainService.utils.entities.GeneratedStringResponseWrapper;
import com.exampleMainService.utils.entities.ResponseWrapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4000", "https://localhost:4000"})
@RequestMapping("/authenticator")
@RestController
public class AuthenticatorController {
    @Autowired
    AuthenticatorService authenticatorService;
    AuthenticatorUtils authenticatorUtils = new AuthenticatorUtils();
    Logger logger = com.exampleMainService.utils.entities.Logger.getInstance(AuthenticatorController.class);
    @PostMapping("/authenticatorString")
    public ResponseEntity<?> authenticatorString(@RequestParam("username") String username, @RequestParam("email") String email){
        try {
            logger.info("Trying to generate the Authenticator String for user with username: " + username);
            String secret = "";
            Authenticator authenticatorRecord = authenticatorService.getAuthenticatorByUsername(username);
            if(authenticatorRecord == null){
                secret = authenticatorUtils.generateSecretKey();
                authenticatorRecord = new Authenticator(0,username,email,secret);
                authenticatorService.save(authenticatorRecord);
                return new ResponseEntity<>(new GeneratedStringResponseWrapper(HttpStatus.OK.value()
                        , authenticatorUtils.makeAuthenticationString(email,secret))
                        , HttpStatus.OK);
            }
            return new ResponseEntity<>(new GeneratedStringResponseWrapper(HttpStatus.OK.value()
                    , authenticatorUtils.makeAuthenticationString(authenticatorRecord.getEmail()
                    ,authenticatorRecord.getSecret()))
                    , HttpStatus.OK);
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
        }
        return new ResponseEntity<>(new ResponseWrapper(HttpStatus.UNAUTHORIZED.value()
                , constants.UNAUTHORIZED)
                , HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/validateOtp")
    public ResponseEntity<?> validateOpt(@RequestParam("username") String username, @RequestParam("otp") String inputOtp){
        try {
            logger.info("Trying to validate the OTP: {} against the username: {}", inputOtp, username);
            Authenticator authenticatorRecord = authenticatorService.getAuthenticatorByUsername(username);
            String originalOtp = authenticatorUtils.getTOTPCode(authenticatorRecord.getSecret());
            if(inputOtp.equals(originalOtp)){
                return new ResponseEntity<>(new ResponseWrapper(HttpStatus.OK.value()
                        ,constants.VERIFIED)
                        , HttpStatus.OK);
            }
        }catch (Exception ex){
            logger.error("Error: "+ex.getMessage());
            return new ResponseEntity<>(new ResponseWrapper(HttpStatus.BAD_REQUEST.value()
                    , constants.BAD_REQUEST)
                    , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseWrapper(HttpStatus.UNAUTHORIZED.value()
                , constants.UNAUTHORIZED)
                , HttpStatus.UNAUTHORIZED);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        errors.put("code", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
