package com.custom.prome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@ComponentScan(basePackages = "com.custom.prome.*")
public class CustomPromeTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomPromeTestApplication.class, args);
    }

}
