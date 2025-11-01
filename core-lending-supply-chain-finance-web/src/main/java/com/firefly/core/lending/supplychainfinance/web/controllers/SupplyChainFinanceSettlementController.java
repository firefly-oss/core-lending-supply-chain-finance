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

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.supplychainfinance.core.services.SupplyChainFinanceSettlementService;
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceSettlementDTO;
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
@RequestMapping("/api/v1/supply-chain-finance/settlements")
@Tag(name = "SupplyChainFinanceSettlement", description = "Operations for Supply Chain Finance Settlements")
@RequiredArgsConstructor
public class SupplyChainFinanceSettlementController {

    private final SupplyChainFinanceSettlementService service;

    @GetMapping
    @Operation(summary = "List/Search supply chain finance settlements", description = "Retrieve a paginated list of settlements based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved settlements",
                    content = @Content(schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<SupplyChainFinanceSettlementDTO>>> findAll(
            @Parameter(description = "Filter criteria for settlements", required = true)
            @Valid @RequestBody FilterRequest<SupplyChainFinanceSettlementDTO> filterRequest) {

        return service.findAll(filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new supply chain finance settlement", description = "Create a new settlement in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Settlement created successfully",
                    content = @Content(schema = @Schema(implementation = SupplyChainFinanceSettlementDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid settlement data", content = @Content)
    })
    public Mono<ResponseEntity<SupplyChainFinanceSettlementDTO>> create(
            @Parameter(description = "Settlement data to create", required = true)
            @Valid @RequestBody SupplyChainFinanceSettlementDTO dto) {

        return service.create(dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a supply chain finance settlement by ID", description = "Retrieve a specific settlement by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Settlement found",
                    content = @Content(schema = @Schema(implementation = SupplyChainFinanceSettlementDTO.class))),
            @ApiResponse(responseCode = "404", description = "Settlement not found", content = @Content)
    })
    public Mono<ResponseEntity<SupplyChainFinanceSettlementDTO>> getById(
            @Parameter(description = "Unique identifier of the settlement", required = true)
            @PathVariable("id") UUID id) {

        return service.getById(id)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing supply chain finance settlement", description = "Update the details of an existing settlement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Settlement updated successfully",
                    content = @Content(schema = @Schema(implementation = SupplyChainFinanceSettlementDTO.class))),
            @ApiResponse(responseCode = "404", description = "Settlement not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid settlement data", content = @Content)
    })
    public Mono<ResponseEntity<SupplyChainFinanceSettlementDTO>> update(
            @Parameter(description = "Unique identifier of the settlement", required = true)
            @PathVariable("id") UUID id,
            @Parameter(description = "Updated settlement data", required = true)
            @Valid @RequestBody SupplyChainFinanceSettlementDTO dto) {

        return service.update(id, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a supply chain finance settlement", description = "Delete a settlement from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Settlement deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Settlement not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the settlement", required = true)
            @PathVariable("id") UUID id) {

        return service.delete(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
