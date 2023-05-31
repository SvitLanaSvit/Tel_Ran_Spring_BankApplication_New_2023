package com.example.bankapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The `BankApplication` class is the main entry point of the Spring Boot application.
 *
 * @SpringBootApplication: This annotation is used to indicate that this class is a Spring Boot application.
 * It combines three annotations: @Configuration, @EnableAutoConfiguration, and @ComponentScan.
 * @Configuration indicates that this class provides configuration to the application context.
 * @EnableAutoConfiguration enables Spring Boot's auto-configuration mechanism to automatically configure
 * the application based on its dependencies and classpath.
 * @ComponentScan tells Spring to scan and discover components, configurations, and services within
 * the specified package and its sub-packages.
 * <p>
 * main(String[] args): The main method is the entry point of the application.
 * It calls the `run` method of `SpringApplication` class to start the Spring Boot application.
 * It passes the `BankApplication` class and the command line arguments `args` to the `run` method.
 * <p>
 * By running this class, the Spring Boot application will start, and the configured components, services,
 * and configurations will be initialized and ready for use.
 */
@SpringBootApplication
public class BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }
}