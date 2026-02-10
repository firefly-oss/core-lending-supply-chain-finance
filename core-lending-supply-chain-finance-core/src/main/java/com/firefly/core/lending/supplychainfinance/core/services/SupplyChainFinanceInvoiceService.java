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


package com.firefly.core.lending.supplychainfinance.core.services;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceInvoiceDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SupplyChainFinanceInvoiceService {

    /**
     * Retrieves a paginated list of supply chain finance invoices based on the provided filter criteria.
     *
     * @param filterRequest a FilterRequest object containing filter criteria and pagination settings for fetching invoices
     * @return a Mono emitting a PaginationResponse containing a list of matching SupplyChainFinanceInvoiceDTO objects
     */
    Mono<PaginationResponse<SupplyChainFinanceInvoiceDTO>> findAll(FilterRequest<SupplyChainFinanceInvoiceDTO> filterRequest);

    /**
     * Creates a new supply chain finance invoice.
     *
     * @param dto the data transfer object containing the details of the invoice to be created
     * @return a Mono emitting the created SupplyChainFinanceInvoiceDTO object upon successful creation
     */
    Mono<SupplyChainFinanceInvoiceDTO> create(SupplyChainFinanceInvoiceDTO dto);

    /**
     * Retrieves a specific supply chain finance invoice by its unique identifier.
     *
     * @param id the unique identifier of the invoice to retrieve
     * @return a Mono emitting the SupplyChainFinanceInvoiceDTO if found, or an empty Mono if not
     */
    Mono<SupplyChainFinanceInvoiceDTO> getById(UUID id);

    /**
     * Updates an existing supply chain finance invoice.
     *
     * @param id the unique identifier of the invoice to be updated
     * @param dto the data transfer object containing the updated details of the invoice
     * @return a Mono emitting the updated SupplyChainFinanceInvoiceDTO object upon successful update
     */
    Mono<SupplyChainFinanceInvoiceDTO> update(UUID id, SupplyChainFinanceInvoiceDTO dto);

    /**
     * Deletes a supply chain finance invoice by its unique identifier.
     *
     * @param id the unique identifier of the invoice to delete
     * @return a Mono<Void> that completes when the deletion is successful
     */
    Mono<Void> delete(UUID id);
}
