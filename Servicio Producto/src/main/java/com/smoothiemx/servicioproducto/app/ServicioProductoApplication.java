package com.smoothiemx.servicioproducto.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.smoothiemx.commons.app.models"})
public class ServicioProductoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicioProductoApplication.class, args);
    }

}
