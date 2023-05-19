package com.example.bankapplication.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The @Configuration annotation marks this class as a configuration class,
 * indicating that it provides bean definitions and other configuration options to the Spring application context.
 *
 * The @EnableWebMvc annotation enables Spring MVC configuration, allowing the class to handle HTTP requests and responses.
 *
 * The WebConfig class implements the WebMvcConfigurer interface,
 * which provides callback methods for customizing the configuration of Spring MVC.
 *
 * The addCorsMappings method is an overridden method from the WebMvcConfigurer interface.
 * It configures Cross-Origin Resource Sharing (CORS) for the application.
 *
 * Inside the addCorsMappings method, a CorsRegistry object is provided as a parameter,
 * allowing the registration of CORS mappings.
 *
 * The registry.addMapping("/**") statement specifies that CORS configuration should be applied to all endpoints.
 *
 * The .allowedOrigins("http://localhost:" + port) method defines the allowed origins for CORS requests, in this case,
 * restricted to http://localhost with the specified port.
 *
 * The .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") method specifies
 * the HTTP methods that are allowed for CORS requests.
 *
 * The .allowedHeaders("Content-Type", "Authorization") method sets the allowed headers for CORS requests,
 * including "Content-Type" and "Authorization".
 *
 * The .maxAge(3600) method sets the maximum age (in seconds) for which the preflight response is cached.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        int port = 63350;
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:" + port)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "Authorization")
                .maxAge(3600);
    }
}