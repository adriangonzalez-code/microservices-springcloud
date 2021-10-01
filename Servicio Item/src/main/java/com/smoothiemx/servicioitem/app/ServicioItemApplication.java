package com.smoothiemx.servicioitem.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*@RibbonClient(name = "servicio-productos")*/
/*@EnableCircuitBreaker*/
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ServicioItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicioItemApplication.class, args);
    }
}