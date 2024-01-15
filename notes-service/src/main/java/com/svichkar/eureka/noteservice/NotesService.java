package com.svichkar.eureka.noteservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "NotesService microservice REST API Documentation",
                description = "NotesService microservice REST API Documentation",
                version = "v1"
                )
)
public class NotesService {

    public static void main(String[] args) {
        SpringApplication.run(NotesService.class, args);
    }

}
