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
import com.firefly.core.lending.supplychainfinance.core.services.CounterpartyService;
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.CounterpartyDTO;
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
@RequestMapping("/api/v1/supply-chain-finance/counterparties")
@Tag(name = "Counterparty", description = "Operations for Counterparties (Debtors & Suppliers)")
@RequiredArgsConstructor
public class CounterpartyController {

    private final CounterpartyService service;

    @GetMapping
    @Operation(summary = "List/Search counterparties", description = "Retrieve a paginated list of counterparties based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved counterparties",
                    content = @Content(schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<CounterpartyDTO>>> findAll(
            @Parameter(description = "Filter criteria for counterparties", required = true)
            @Valid @RequestBody FilterRequest<CounterpartyDTO> filterRequest) {

        return service.findAll(filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new counterparty", description = "Create a new counterparty in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Counterparty created successfully",
                    content = @Content(schema = @Schema(implementation = CounterpartyDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid counterparty data", content = @Content)
    })
    public Mono<ResponseEntity<CounterpartyDTO>> create(
            @Parameter(description = "Counterparty data to create", required = true)
            @Valid @RequestBody CounterpartyDTO dto) {

        return service.create(dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a counterparty by ID", description = "Retrieve a specific counterparty by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Counterparty found",
                    content = @Content(schema = @Schema(implementation = CounterpartyDTO.class))),
            @ApiResponse(responseCode = "404", description = "Counterparty not found", content = @Content)
    })
    public Mono<ResponseEntity<CounterpartyDTO>> getById(
            @Parameter(description = "Unique identifier of the counterparty", required = true)
            @PathVariable("id") UUID id) {

        return service.getById(id)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing counterparty", description = "Update the details of an existing counterparty")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Counterparty updated successfully",
                    content = @Content(schema = @Schema(implementation = CounterpartyDTO.class))),
            @ApiResponse(responseCode = "404", description = "Counterparty not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid counterparty data", content = @Content)
    })
    public Mono<ResponseEntity<CounterpartyDTO>> update(
            @Parameter(description = "Unique identifier of the counterparty", required = true)
            @PathVariable("id") UUID id,
            @Parameter(description = "Updated counterparty data", required = true)
            @Valid @RequestBody CounterpartyDTO dto) {

        return service.update(id, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a counterparty", description = "Delete a counterparty from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Counterparty deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Counterparty not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the counterparty", required = true)
            @PathVariable("id") UUID id) {

        return service.delete(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
