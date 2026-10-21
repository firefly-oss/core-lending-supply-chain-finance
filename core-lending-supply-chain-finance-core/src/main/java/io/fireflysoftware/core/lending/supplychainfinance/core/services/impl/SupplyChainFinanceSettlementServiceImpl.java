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

import org.fireflyframework.core.filters.*;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.lending.supplychainfinance.core.mappers.SupplyChainFinanceSettlementMapper;
import io.fireflysoftware.core.lending.supplychainfinance.core.services.SupplyChainFinanceSettlementService;
import io.fireflysoftware.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceSettlementDTO;
import io.fireflysoftware.core.lending.supplychainfinance.models.entities.SupplyChainFinanceSettlement;
import io.fireflysoftware.core.lending.supplychainfinance.models.repositories.SupplyChainFinanceSettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplyChainFinanceSettlementServiceImpl implements SupplyChainFinanceSettlementService {
    private final SupplyChainFinanceSettlementRepository repository;
    private final SupplyChainFinanceSettlementMapper mapper;

    @Override
    public Mono<PaginationResponse<SupplyChainFinanceSettlementDTO>> findAll(FilterRequest<SupplyChainFinanceSettlementDTO> filterRequest) {
        return FilterUtils.createFilter(SupplyChainFinanceSettlement.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<SupplyChainFinanceSettlementDTO> create(SupplyChainFinanceSettlementDTO dto) {
        return Mono.just(dto).map(mapper::toEntity).flatMap(repository::save).map(mapper::toDTO);
    }

    @Override
    public Mono<SupplyChainFinanceSettlementDTO> getById(UUID id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    @Override
    public Mono<SupplyChainFinanceSettlementDTO> update(UUID id, SupplyChainFinanceSettlementDTO dto) {
        return repository.findById(id).flatMap(existing -> {
            SupplyChainFinanceSettlement updated = mapper.toEntity(dto);
            updated.setId(existing.getId());
            return repository.save(updated);
        }).map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.findById(id).flatMap(repository::delete);
    }
}
