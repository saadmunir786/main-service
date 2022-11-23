package com.exampleMainService.utils.entities;

import org.slf4j.LoggerFactory;

public class Logger {
    private static org.slf4j.Logger instance;
    private Logger(){}

    public static org.slf4j.Logger getInstance(Class c){
        if(instance == null)
            instance = LoggerFactory.getLogger(c.getName());
        return instance;
    }
    public static void changeClass(Class c){
        instance = LoggerFactory.getLogger(c.getName());
    }
}
