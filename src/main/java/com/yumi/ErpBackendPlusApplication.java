package com.yumi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class ErpBackendPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpBackendPlusApplication.class, args);
    }

}
