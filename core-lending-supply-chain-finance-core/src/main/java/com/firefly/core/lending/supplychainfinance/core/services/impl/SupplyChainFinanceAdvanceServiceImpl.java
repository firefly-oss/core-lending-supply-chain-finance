/*
 * Copyright 2025 Firefly Software Solutions Inc
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.firefly.core.lending.supplychainfinance.core.services.impl;

import com.firefly.common.core.filters.*;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.supplychainfinance.core.mappers.SupplyChainFinanceAdvanceMapper;
import com.firefly.core.lending.supplychainfinance.core.services.SupplyChainFinanceAdvanceService;
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceAdvanceDTO;
import com.firefly.core.lending.supplychainfinance.models.entities.SupplyChainFinanceAdvance;
import com.firefly.core.lending.supplychainfinance.models.repositories.SupplyChainFinanceAdvanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplyChainFinanceAdvanceServiceImpl implements SupplyChainFinanceAdvanceService {
    private final SupplyChainFinanceAdvanceRepository repository;
    private final SupplyChainFinanceAdvanceMapper mapper;

    @Override
    public Mono<PaginationResponse<SupplyChainFinanceAdvanceDTO>> findAll(FilterRequest<SupplyChainFinanceAdvanceDTO> filterRequest) {
        return FilterUtils.createFilter(SupplyChainFinanceAdvance.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<SupplyChainFinanceAdvanceDTO> create(SupplyChainFinanceAdvanceDTO dto) {
        return Mono.just(dto).map(mapper::toEntity).flatMap(repository::save).map(mapper::toDTO);
    }

    @Override
    public Mono<SupplyChainFinanceAdvanceDTO> getById(UUID id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    @Override
    public Mono<SupplyChainFinanceAdvanceDTO> update(UUID id, SupplyChainFinanceAdvanceDTO dto) {
        return repository.findById(id).flatMap(existing -> {
            SupplyChainFinanceAdvance updated = mapper.toEntity(dto);
            updated.setId(existing.getId());
            return repository.save(updated);
        }).map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.findById(id).flatMap(repository::delete);
    }
}
