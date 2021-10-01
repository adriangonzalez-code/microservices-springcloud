package com.smoothiemx.servicioproducto.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServicioProductoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicioProductoApplication.class, args);
    }

}
