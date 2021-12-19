package com.technicalassignment.ebi01951;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com.technicalassignment.ebi01951"})
@EnableSwagger2
public class Ebi01951Application {

    public static void main(String[] args) {
        SpringApplication.run(Ebi01951Application.class, args);
    }
}
