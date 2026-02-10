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
import com.firefly.core.lending.supplychainfinance.core.services.SupplyChainFinanceInvoiceService;
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceInvoiceDTO;
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
@RequestMapping("/api/v1/supply-chain-finance/invoices")
@Tag(name = "SupplyChainFinanceInvoice", description = "Operations for Supply Chain Finance Invoices")
@RequiredArgsConstructor
public class SupplyChainFinanceInvoiceController {

    private final SupplyChainFinanceInvoiceService service;

    @GetMapping
    @Operation(summary = "List/Search supply chain finance invoices", description = "Retrieve a paginated list of invoices based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved invoices",
                    content = @Content(schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<SupplyChainFinanceInvoiceDTO>>> findAll(
            @Parameter(description = "Filter criteria for invoices", required = true)
            @Valid @RequestBody FilterRequest<SupplyChainFinanceInvoiceDTO> filterRequest) {

        return service.findAll(filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new supply chain finance invoice", description = "Create a new invoice in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice created successfully",
                    content = @Content(schema = @Schema(implementation = SupplyChainFinanceInvoiceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid invoice data", content = @Content)
    })
    public Mono<ResponseEntity<SupplyChainFinanceInvoiceDTO>> create(
            @Parameter(description = "Invoice data to create", required = true)
            @Valid @RequestBody SupplyChainFinanceInvoiceDTO dto) {

        return service.create(dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a supply chain finance invoice by ID", description = "Retrieve a specific invoice by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice found",
                    content = @Content(schema = @Schema(implementation = SupplyChainFinanceInvoiceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invoice not found", content = @Content)
    })
    public Mono<ResponseEntity<SupplyChainFinanceInvoiceDTO>> getById(
            @Parameter(description = "Unique identifier of the invoice", required = true)
            @PathVariable("id") UUID id) {

        return service.getById(id)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing supply chain finance invoice", description = "Update the details of an existing invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice updated successfully",
                    content = @Content(schema = @Schema(implementation = SupplyChainFinanceInvoiceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invoice not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid invoice data", content = @Content)
    })
    public Mono<ResponseEntity<SupplyChainFinanceInvoiceDTO>> update(
            @Parameter(description = "Unique identifier of the invoice", required = true)
            @PathVariable("id") UUID id,
            @Parameter(description = "Updated invoice data", required = true)
            @Valid @RequestBody SupplyChainFinanceInvoiceDTO dto) {

        return service.update(id, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a supply chain finance invoice", description = "Delete an invoice from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Invoice deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Invoice not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the invoice", required = true)
            @PathVariable("id") UUID id) {

        return service.delete(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
