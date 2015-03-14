package com.lukzar.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 *
 * @author lukasz
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.lukzar.controller", "com.lukzar.config", "com.lukzar.service"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
