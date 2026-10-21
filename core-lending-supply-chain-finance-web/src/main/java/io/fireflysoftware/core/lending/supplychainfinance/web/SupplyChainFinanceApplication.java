/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.core.lending.supplychainfinance.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * Main Spring Boot application for Supply Chain Finance microservice.
 * Trade finance agreements. Stored product kinds are FACTORING and REVERSE_FACTORING.
 */
@SpringBootApplication(scanBasePackages = "io.fireflysoftware.core.lending.supplychainfinance")
@EnableR2dbcRepositories(basePackages = "io.fireflysoftware.core.lending.supplychainfinance.models.repositories")
public class SupplyChainFinanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplyChainFinanceApplication.class, args);
    }
}
