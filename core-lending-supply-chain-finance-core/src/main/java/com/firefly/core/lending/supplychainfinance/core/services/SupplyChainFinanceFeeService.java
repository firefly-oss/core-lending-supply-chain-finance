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

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceFeeDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SupplyChainFinanceFeeService {

    /**
     * Retrieves a paginated list of supply chain finance fees based on the provided filter criteria.
     *
     * @param filterRequest a FilterRequest object containing filter criteria and pagination settings for fetching fees
     * @return a Mono emitting a PaginationResponse containing a list of matching SupplyChainFinanceFeeDTO objects
     */
    Mono<PaginationResponse<SupplyChainFinanceFeeDTO>> findAll(FilterRequest<SupplyChainFinanceFeeDTO> filterRequest);

    /**
     * Creates a new supply chain finance fee.
     *
     * @param dto the data transfer object containing the details of the fee to be created
     * @return a Mono emitting the created SupplyChainFinanceFeeDTO object upon successful creation
     */
    Mono<SupplyChainFinanceFeeDTO> create(SupplyChainFinanceFeeDTO dto);

    /**
     * Retrieves a specific supply chain finance fee by its unique identifier.
     *
     * @param id the unique identifier of the fee to retrieve
     * @return a Mono emitting the SupplyChainFinanceFeeDTO if found, or an empty Mono if not
     */
    Mono<SupplyChainFinanceFeeDTO> getById(UUID id);

    /**
     * Updates an existing supply chain finance fee.
     *
     * @param id the unique identifier of the fee to be updated
     * @param dto the data transfer object containing the updated details of the fee
     * @return a Mono emitting the updated SupplyChainFinanceFeeDTO object upon successful update
     */
    Mono<SupplyChainFinanceFeeDTO> update(UUID id, SupplyChainFinanceFeeDTO dto);

    /**
     * Deletes a supply chain finance fee by its unique identifier.
     *
     * @param id the unique identifier of the fee to delete
     * @return a Mono<Void> that completes when the deletion is successful
     */
    Mono<Void> delete(UUID id);
}
