/*
 * Copyright 2025 Firefly Software Solutions Inc
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.firefly.core.lending.supplychainfinance.core.services.impl;

import com.firefly.common.core.filters.*;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.supplychainfinance.core.mappers.SupplyChainFinanceSettlementMapper;
import com.firefly.core.lending.supplychainfinance.core.services.SupplyChainFinanceSettlementService;
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceSettlementDTO;
import com.firefly.core.lending.supplychainfinance.models.entities.SupplyChainFinanceSettlement;
import com.firefly.core.lending.supplychainfinance.models.repositories.SupplyChainFinanceSettlementRepository;
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
