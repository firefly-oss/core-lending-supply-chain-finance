/*
 * Copyright 2025 Firefly Software Solutions Inc
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


package com.firefly.core.lending.supplychainfinance.web.controllers;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.lending.supplychainfinance.core.services.SupplyChainFinanceAgreementService;
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceAgreementDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/supply-chain-finance/agreements")
@Tag(name = "SupplyChainFinanceAgreement", description = "Operations for Supply Chain Finance Agreements (Factoring & Confirming)")
@RequiredArgsConstructor
public class SupplyChainFinanceAgreementController {

    private final SupplyChainFinanceAgreementService service;

    @GetMapping
    @Operation(summary = "List/Search supply chain finance agreements", description = "Retrieve a paginated list of supply chain finance agreements based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved agreements",
                    content = @Content(schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<SupplyChainFinanceAgreementDTO>>> findAll(
            @Parameter(description = "Filter criteria for agreements", required = true)
            @Valid @RequestBody FilterRequest<SupplyChainFinanceAgreementDTO> filterRequest) {

        return service.findAll(filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new supply chain finance agreement", description = "Create a new supply chain finance agreement in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agreement created successfully",
                    content = @Content(schema = @Schema(implementation = SupplyChainFinanceAgreementDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid agreement data", content = @Content)
    })
    public Mono<ResponseEntity<SupplyChainFinanceAgreementDTO>> create(
            @Parameter(description = "Agreement data to create", required = true)
            @Valid @RequestBody SupplyChainFinanceAgreementDTO dto) {

        return service.create(dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a supply chain finance agreement by ID", description = "Retrieve a specific agreement by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agreement found",
                    content = @Content(schema = @Schema(implementation = SupplyChainFinanceAgreementDTO.class))),
            @ApiResponse(responseCode = "404", description = "Agreement not found", content = @Content)
    })
    public Mono<ResponseEntity<SupplyChainFinanceAgreementDTO>> getById(
            @Parameter(description = "Unique identifier of the agreement", required = true)
            @PathVariable("id") UUID id) {

        return service.getById(id)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing supply chain finance agreement", description = "Update the details of an existing agreement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agreement updated successfully",
                    content = @Content(schema = @Schema(implementation = SupplyChainFinanceAgreementDTO.class))),
            @ApiResponse(responseCode = "404", description = "Agreement not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid agreement data", content = @Content)
    })
    public Mono<ResponseEntity<SupplyChainFinanceAgreementDTO>> update(
            @Parameter(description = "Unique identifier of the agreement", required = true)
            @PathVariable("id") UUID id,
            @Parameter(description = "Updated agreement data", required = true)
            @Valid @RequestBody SupplyChainFinanceAgreementDTO dto) {

        return service.update(id, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a supply chain finance agreement", description = "Delete an agreement from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agreement deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Agreement not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the agreement", required = true)
            @PathVariable("id") UUID id) {

        return service.delete(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
