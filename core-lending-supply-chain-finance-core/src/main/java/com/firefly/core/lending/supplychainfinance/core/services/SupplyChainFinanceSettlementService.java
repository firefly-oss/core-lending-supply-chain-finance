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
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceSettlementDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SupplyChainFinanceSettlementService {

    /**
     * Retrieves a paginated list of supply chain finance settlements based on the provided filter criteria.
     *
     * @param filterRequest a FilterRequest object containing filter criteria and pagination settings for fetching settlements
     * @return a Mono emitting a PaginationResponse containing a list of matching SupplyChainFinanceSettlementDTO objects
     */
    Mono<PaginationResponse<SupplyChainFinanceSettlementDTO>> findAll(FilterRequest<SupplyChainFinanceSettlementDTO> filterRequest);

    /**
     * Creates a new supply chain finance settlement.
     *
     * @param dto the data transfer object containing the details of the settlement to be created
     * @return a Mono emitting the created SupplyChainFinanceSettlementDTO object upon successful creation
     */
    Mono<SupplyChainFinanceSettlementDTO> create(SupplyChainFinanceSettlementDTO dto);

    /**
     * Retrieves a specific supply chain finance settlement by its unique identifier.
     *
     * @param id the unique identifier of the settlement to retrieve
     * @return a Mono emitting the SupplyChainFinanceSettlementDTO if found, or an empty Mono if not
     */
    Mono<SupplyChainFinanceSettlementDTO> getById(UUID id);

    /**
     * Updates an existing supply chain finance settlement.
     *
     * @param id the unique identifier of the settlement to be updated
     * @param dto the data transfer object containing the updated details of the settlement
     * @return a Mono emitting the updated SupplyChainFinanceSettlementDTO object upon successful update
     */
    Mono<SupplyChainFinanceSettlementDTO> update(UUID id, SupplyChainFinanceSettlementDTO dto);

    /**
     * Deletes a supply chain finance settlement by its unique identifier.
     *
     * @param id the unique identifier of the settlement to delete
     * @return a Mono<Void> that completes when the deletion is successful
     */
    Mono<Void> delete(UUID id);
}
