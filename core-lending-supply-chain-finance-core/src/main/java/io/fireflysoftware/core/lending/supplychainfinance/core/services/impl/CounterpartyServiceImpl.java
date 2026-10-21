/*
 * Copyright 2025 Firefly Software Foundation
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


package io.fireflysoftware.core.lending.supplychainfinance.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.lending.supplychainfinance.core.mappers.CounterpartyMapper;
import io.fireflysoftware.core.lending.supplychainfinance.core.services.CounterpartyService;
import io.fireflysoftware.core.lending.supplychainfinance.interfaces.dtos.CounterpartyDTO;
import io.fireflysoftware.core.lending.supplychainfinance.models.entities.Counterparty;
import io.fireflysoftware.core.lending.supplychainfinance.models.repositories.CounterpartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CounterpartyServiceImpl implements CounterpartyService {

    private final CounterpartyRepository repository;
    private final CounterpartyMapper mapper;

    @Override
    public Mono<PaginationResponse<CounterpartyDTO>> findAll(FilterRequest<CounterpartyDTO> filterRequest) {
        return FilterUtils.createFilter(
                Counterparty.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<CounterpartyDTO> create(CounterpartyDTO dto) {
        if (dto.getPartyId() == null) {
            return Mono.error(new IllegalStateException(
                    "Counterparty must reference a party; customer details are owned by customer management"));
        }
        return Mono.just(dto)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CounterpartyDTO> getById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CounterpartyDTO> update(UUID id, CounterpartyDTO dto) {
        return repository.findById(id)
                .flatMap(existing -> {
                    if (dto.getPartyId() == null) {
                        return Mono.error(new IllegalStateException(
                                "Counterparty must reference a party; customer details are owned by customer management"));
                    }
                    Counterparty updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setId(existing.getId());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.findById(id)
                .flatMap(repository::delete);
    }
}
