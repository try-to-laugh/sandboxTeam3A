package com.sandbox.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${cors.allowed-methods}")
    private String allowedMethods;

    @Value("${cors.exposed-headers}")
    private String exposedHeaders;

    @Value("${cors.addMapping}")
    private String addMapping;

    @Override
    public void addCorsMappings(CorsRegistry registry){

        registry.addMapping(addMapping)
                .allowedHeaders(allowedHeaders)
                .allowedMethods(allowedMethods)
                .allowedOrigins(allowedOrigins)
                .exposedHeaders(exposedHeaders);
    }
}
