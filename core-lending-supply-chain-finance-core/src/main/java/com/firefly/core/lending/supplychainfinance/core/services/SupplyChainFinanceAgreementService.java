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
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceAgreementDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SupplyChainFinanceAgreementService {

    /**
     * Retrieves a paginated list of supply chain finance agreements based on the provided filter criteria.
     *
     * @param filterRequest a FilterRequest object containing filter criteria and pagination settings for fetching agreements
     * @return a Mono emitting a PaginationResponse containing a list of matching SupplyChainFinanceAgreementDTO objects
     */
    Mono<PaginationResponse<SupplyChainFinanceAgreementDTO>> findAll(FilterRequest<SupplyChainFinanceAgreementDTO> filterRequest);

    /**
     * Creates a new supply chain finance agreement.
     *
     * @param dto the data transfer object containing the details of the agreement to be created
     * @return a Mono emitting the created SupplyChainFinanceAgreementDTO object upon successful creation
     */
    Mono<SupplyChainFinanceAgreementDTO> create(SupplyChainFinanceAgreementDTO dto);

    /**
     * Retrieves a specific supply chain finance agreement by its unique identifier.
     *
     * @param id the unique identifier of the agreement to retrieve
     * @return a Mono emitting the SupplyChainFinanceAgreementDTO if found, or an empty Mono if not
     */
    Mono<SupplyChainFinanceAgreementDTO> getById(UUID id);

    /**
     * Updates an existing supply chain finance agreement.
     *
     * @param id the unique identifier of the agreement to be updated
     * @param dto the data transfer object containing the updated details of the agreement
     * @return a Mono emitting the updated SupplyChainFinanceAgreementDTO object upon successful update
     */
    Mono<SupplyChainFinanceAgreementDTO> update(UUID id, SupplyChainFinanceAgreementDTO dto);

    /**
     * Deletes a supply chain finance agreement by its unique identifier.
     *
     * @param id the unique identifier of the agreement to delete
     * @return a Mono<Void> that completes when the deletion is successful
     */
    Mono<Void> delete(UUID id);
}
