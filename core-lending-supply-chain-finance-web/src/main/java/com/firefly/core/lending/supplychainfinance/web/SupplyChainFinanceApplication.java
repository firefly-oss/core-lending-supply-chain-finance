package com.firefly.core.lending.supplychainfinance.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * Main Spring Boot application for Supply Chain Finance microservice.
 * Unified implementation supporting both FACTORING and CONFIRMING.
 */
@SpringBootApplication(scanBasePackages = "com.firefly.core.lending.supplychainfinance")
@EnableR2dbcRepositories(basePackages = "com.firefly.core.lending.supplychainfinance.models.repositories")
public class SupplyChainFinanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplyChainFinanceApplication.class, args);
    }
}
