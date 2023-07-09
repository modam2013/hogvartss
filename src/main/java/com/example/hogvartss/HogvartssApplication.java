package com.example.hogvartss;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class HogvartssApplication {

    public static void main(String[] args) {
        SpringApplication.run(HogvartssApplication.class, args);
    }

}
