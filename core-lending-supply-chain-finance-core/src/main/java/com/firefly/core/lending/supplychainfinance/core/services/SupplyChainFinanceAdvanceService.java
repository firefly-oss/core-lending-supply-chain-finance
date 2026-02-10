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
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceAdvanceDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SupplyChainFinanceAdvanceService {

    /**
     * Retrieves a paginated list of supply chain finance advances based on the provided filter criteria.
     *
     * @param filterRequest a FilterRequest object containing filter criteria and pagination settings for fetching advances
     * @return a Mono emitting a PaginationResponse containing a list of matching SupplyChainFinanceAdvanceDTO objects
     */
    Mono<PaginationResponse<SupplyChainFinanceAdvanceDTO>> findAll(FilterRequest<SupplyChainFinanceAdvanceDTO> filterRequest);

    /**
     * Creates a new supply chain finance advance.
     *
     * @param dto the data transfer object containing the details of the advance to be created
     * @return a Mono emitting the created SupplyChainFinanceAdvanceDTO object upon successful creation
     */
    Mono<SupplyChainFinanceAdvanceDTO> create(SupplyChainFinanceAdvanceDTO dto);

    /**
     * Retrieves a specific supply chain finance advance by its unique identifier.
     *
     * @param id the unique identifier of the advance to retrieve
     * @return a Mono emitting the SupplyChainFinanceAdvanceDTO if found, or an empty Mono if not
     */
    Mono<SupplyChainFinanceAdvanceDTO> getById(UUID id);

    /**
     * Updates an existing supply chain finance advance.
     *
     * @param id the unique identifier of the advance to be updated
     * @param dto the data transfer object containing the updated details of the advance
     * @return a Mono emitting the updated SupplyChainFinanceAdvanceDTO object upon successful update
     */
    Mono<SupplyChainFinanceAdvanceDTO> update(UUID id, SupplyChainFinanceAdvanceDTO dto);

    /**
     * Deletes a supply chain finance advance by its unique identifier.
     *
     * @param id the unique identifier of the advance to delete
     * @return a Mono<Void> that completes when the deletion is successful
     */
    Mono<Void> delete(UUID id);
}
