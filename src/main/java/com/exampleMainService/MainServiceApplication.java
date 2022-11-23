package com.exampleMainService;

import com.exampleMainService.utils.entities.AppContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MainServiceApplication.class, args);
        AppContext appContext = AppContext.getInstance();
        appContext.setContext(applicationContext);
    }

}
