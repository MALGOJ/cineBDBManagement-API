package com.example.cineBDB_managment.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Manuel Gomez Jimenez",
                        email = "jimenezmanuelalejandro57@gmail.com"
                ),
                description = "Api documentation for cineBDB",
                title = "API specification - cineBDB",
                version = "1.0.0"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8081",
                        description = "Local ENV"
                )
        }
)
public class OpenApiConfig {
}

