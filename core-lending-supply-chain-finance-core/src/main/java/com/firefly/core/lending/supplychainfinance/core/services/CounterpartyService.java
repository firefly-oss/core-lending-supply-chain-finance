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
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.CounterpartyDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CounterpartyService {

    /**
     * Retrieves a paginated list of counterparties based on the provided filter criteria.
     *
     * @param filterRequest a FilterRequest object containing filter criteria and pagination settings for fetching counterparties
     * @return a Mono emitting a PaginationResponse containing a list of matching CounterpartyDTO objects
     */
    Mono<PaginationResponse<CounterpartyDTO>> findAll(FilterRequest<CounterpartyDTO> filterRequest);

    /**
     * Creates a new counterparty.
     *
     * @param dto the data transfer object containing the details of the counterparty to be created
     * @return a Mono emitting the created CounterpartyDTO object upon successful creation
     */
    Mono<CounterpartyDTO> create(CounterpartyDTO dto);

    /**
     * Retrieves a specific counterparty by its unique identifier.
     *
     * @param id the unique identifier of the counterparty to retrieve
     * @return a Mono emitting the CounterpartyDTO if found, or an empty Mono if not
     */
    Mono<CounterpartyDTO> getById(UUID id);

    /**
     * Updates an existing counterparty.
     *
     * @param id the unique identifier of the counterparty to be updated
     * @param dto the data transfer object containing the updated details of the counterparty
     * @return a Mono emitting the updated CounterpartyDTO object upon successful update
     */
    Mono<CounterpartyDTO> update(UUID id, CounterpartyDTO dto);

    /**
     * Deletes a counterparty by its unique identifier.
     *
     * @param id the unique identifier of the counterparty to delete
     * @return a Mono<Void> that completes when the deletion is successful
     */
    Mono<Void> delete(UUID id);
}
