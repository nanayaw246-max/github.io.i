package com.example.semproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.semproject") // Explicitly scan the base package
public class SemprojectApplication {

    
    public static void main(String[] args) {
        SpringApplication.run(SemprojectApplication.class, args);
    }

}
