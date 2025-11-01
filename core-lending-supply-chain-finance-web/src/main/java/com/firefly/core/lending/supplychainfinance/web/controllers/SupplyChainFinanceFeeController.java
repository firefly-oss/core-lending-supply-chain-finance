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
import com.firefly.core.lending.supplychainfinance.core.services.SupplyChainFinanceFeeService;
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceFeeDTO;
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
@RequestMapping("/api/v1/supply-chain-finance/fees")
@Tag(name = "SupplyChainFinanceFee", description = "Operations for Supply Chain Finance Fees")
@RequiredArgsConstructor
public class SupplyChainFinanceFeeController {

    private final SupplyChainFinanceFeeService service;

    @GetMapping
    @Operation(summary = "List/Search supply chain finance fees", description = "Retrieve a paginated list of fees based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved fees",
                    content = @Content(schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<SupplyChainFinanceFeeDTO>>> findAll(
            @Parameter(description = "Filter criteria for fees", required = true)
            @Valid @RequestBody FilterRequest<SupplyChainFinanceFeeDTO> filterRequest) {

        return service.findAll(filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new supply chain finance fee", description = "Create a new fee in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fee created successfully",
                    content = @Content(schema = @Schema(implementation = SupplyChainFinanceFeeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid fee data", content = @Content)
    })
    public Mono<ResponseEntity<SupplyChainFinanceFeeDTO>> create(
            @Parameter(description = "Fee data to create", required = true)
            @Valid @RequestBody SupplyChainFinanceFeeDTO dto) {

        return service.create(dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a supply chain finance fee by ID", description = "Retrieve a specific fee by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fee found",
                    content = @Content(schema = @Schema(implementation = SupplyChainFinanceFeeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Fee not found", content = @Content)
    })
    public Mono<ResponseEntity<SupplyChainFinanceFeeDTO>> getById(
            @Parameter(description = "Unique identifier of the fee", required = true)
            @PathVariable("id") UUID id) {

        return service.getById(id)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing supply chain finance fee", description = "Update the details of an existing fee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fee updated successfully",
                    content = @Content(schema = @Schema(implementation = SupplyChainFinanceFeeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Fee not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid fee data", content = @Content)
    })
    public Mono<ResponseEntity<SupplyChainFinanceFeeDTO>> update(
            @Parameter(description = "Unique identifier of the fee", required = true)
            @PathVariable("id") UUID id,
            @Parameter(description = "Updated fee data", required = true)
            @Valid @RequestBody SupplyChainFinanceFeeDTO dto) {

        return service.update(id, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a supply chain finance fee", description = "Delete a fee from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Fee not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the fee", required = true)
            @PathVariable("id") UUID id) {

        return service.delete(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
