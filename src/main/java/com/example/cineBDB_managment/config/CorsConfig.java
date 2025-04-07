package com.example.cineBDB_managment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")  // Aplica CORS a todas las rutas bajo /api
                        .allowedOrigins("http://localhost:4200")  // Origen del frontend Angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Métodos permitidos
                        .allowedHeaders("*")  // Headers permitidos (incluye Content-Type, Authorization, etc.)
                        .allowCredentials(true);  // Permite cookies/tokens de autenticación si los usas
            }
        };
    }
}
