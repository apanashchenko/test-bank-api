package com.test.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class TestBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestBankApplication.class, args);
    }
}
