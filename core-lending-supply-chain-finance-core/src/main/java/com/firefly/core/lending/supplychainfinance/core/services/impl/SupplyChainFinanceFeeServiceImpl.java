/*
 * Copyright 2025 Firefly Software Solutions Inc
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.firefly.core.lending.supplychainfinance.core.services.impl;

import com.firefly.common.core.filters.*;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.supplychainfinance.core.mappers.SupplyChainFinanceFeeMapper;
import com.firefly.core.lending.supplychainfinance.core.services.SupplyChainFinanceFeeService;
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceFeeDTO;
import com.firefly.core.lending.supplychainfinance.models.entities.SupplyChainFinanceFee;
import com.firefly.core.lending.supplychainfinance.models.repositories.SupplyChainFinanceFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplyChainFinanceFeeServiceImpl implements SupplyChainFinanceFeeService {
    private final SupplyChainFinanceFeeRepository repository;
    private final SupplyChainFinanceFeeMapper mapper;

    @Override
    public Mono<PaginationResponse<SupplyChainFinanceFeeDTO>> findAll(FilterRequest<SupplyChainFinanceFeeDTO> filterRequest) {
        return FilterUtils.createFilter(SupplyChainFinanceFee.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<SupplyChainFinanceFeeDTO> create(SupplyChainFinanceFeeDTO dto) {
        return Mono.just(dto).map(mapper::toEntity).flatMap(repository::save).map(mapper::toDTO);
    }

    @Override
    public Mono<SupplyChainFinanceFeeDTO> getById(UUID id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    @Override
    public Mono<SupplyChainFinanceFeeDTO> update(UUID id, SupplyChainFinanceFeeDTO dto) {
        return repository.findById(id).flatMap(existing -> {
            SupplyChainFinanceFee updated = mapper.toEntity(dto);
            updated.setId(existing.getId());
            return repository.save(updated);
        }).map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.findById(id).flatMap(repository::delete);
    }
}
