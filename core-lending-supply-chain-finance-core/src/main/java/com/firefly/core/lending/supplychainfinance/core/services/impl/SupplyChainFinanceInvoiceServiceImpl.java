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

package com.firefly.core.lending.supplychainfinance.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.supplychainfinance.core.mappers.SupplyChainFinanceInvoiceMapper;
import com.firefly.core.lending.supplychainfinance.core.services.SupplyChainFinanceInvoiceService;
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceInvoiceDTO;
import com.firefly.core.lending.supplychainfinance.models.entities.SupplyChainFinanceInvoice;
import com.firefly.core.lending.supplychainfinance.models.repositories.SupplyChainFinanceInvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplyChainFinanceInvoiceServiceImpl implements SupplyChainFinanceInvoiceService {

    private final SupplyChainFinanceInvoiceRepository repository;
    private final SupplyChainFinanceInvoiceMapper mapper;

    @Override
    public Mono<PaginationResponse<SupplyChainFinanceInvoiceDTO>> findAll(FilterRequest<SupplyChainFinanceInvoiceDTO> filterRequest) {
        return FilterUtils.createFilter(SupplyChainFinanceInvoice.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<SupplyChainFinanceInvoiceDTO> create(SupplyChainFinanceInvoiceDTO dto) {
        return Mono.just(dto).map(mapper::toEntity).flatMap(repository::save).map(mapper::toDTO);
    }

    @Override
    public Mono<SupplyChainFinanceInvoiceDTO> getById(UUID id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    @Override
    public Mono<SupplyChainFinanceInvoiceDTO> update(UUID id, SupplyChainFinanceInvoiceDTO dto) {
        return repository.findById(id)
                .flatMap(existing -> {
                    SupplyChainFinanceInvoice updated = mapper.toEntity(dto);
                    updated.setId(existing.getId());
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.findById(id).flatMap(repository::delete);
    }
}
