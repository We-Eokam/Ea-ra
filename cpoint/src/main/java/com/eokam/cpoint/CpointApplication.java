package com.eokam.cpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CpointApplication {

    public static void main(String[] args) {
        SpringApplication.run(CpointApplication.class, args);
    }

}
