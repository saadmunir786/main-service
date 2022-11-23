package com.exampleMainService.utils.entities;

import org.springframework.context.ConfigurableApplicationContext;

public class AppContext {
    private static AppContext instance = new AppContext();
    private ConfigurableApplicationContext ctx;

    private AppContext(){}

    public static AppContext getInstance() {
        return instance;
    }
    public void setContext(ConfigurableApplicationContext ctx){
        this.ctx = ctx;
    }
    public ConfigurableApplicationContext getContext(){
        return ctx;
    }
}
