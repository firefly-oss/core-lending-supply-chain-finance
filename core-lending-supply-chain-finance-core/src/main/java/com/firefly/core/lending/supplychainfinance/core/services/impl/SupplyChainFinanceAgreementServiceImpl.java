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

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.lending.supplychainfinance.core.mappers.SupplyChainFinanceAgreementMapper;
import com.firefly.core.lending.supplychainfinance.core.services.SupplyChainFinanceAgreementService;
import com.firefly.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceAgreementDTO;
import com.firefly.core.lending.supplychainfinance.interfaces.enums.FinanceTypeEnum;
import com.firefly.core.lending.supplychainfinance.models.entities.SupplyChainFinanceAgreement;
import com.firefly.core.lending.supplychainfinance.models.repositories.SupplyChainFinanceAgreementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SupplyChainFinanceAgreementServiceImpl implements SupplyChainFinanceAgreementService {

    private final SupplyChainFinanceAgreementRepository repository;
    private final SupplyChainFinanceAgreementMapper mapper;

    @Override
    public Mono<PaginationResponse<SupplyChainFinanceAgreementDTO>> findAll(FilterRequest<SupplyChainFinanceAgreementDTO> filterRequest) {
        return FilterUtils.createFilter(
                SupplyChainFinanceAgreement.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<SupplyChainFinanceAgreementDTO> create(SupplyChainFinanceAgreementDTO dto) {
        return validateFinanceTypeSpecificFields(dto)
                .then(Mono.just(dto))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO)
                .doOnSuccess(created -> log.info("Created {} agreement with ID: {}",
                        created.getFinanceType(), created.getId()));
    }

    @Override
    public Mono<SupplyChainFinanceAgreementDTO> getById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<SupplyChainFinanceAgreementDTO> update(UUID id, SupplyChainFinanceAgreementDTO dto) {
        return validateFinanceTypeSpecificFields(dto)
                .then(repository.findById(id))
                .flatMap(existingAgreement -> {
                    SupplyChainFinanceAgreement updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setId(existingAgreement.getId());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO)
                .doOnSuccess(updated -> log.info("Updated {} agreement with ID: {}",
                        updated.getFinanceType(), updated.getId()));
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.findById(id)
                .flatMap(repository::delete)
                .doOnSuccess(v -> log.info("Deleted agreement with ID: {}", id));
    }

    /**
     * Validates type-specific fields based on financeType discriminator.
     * This is the key pattern: validation depends on financeType value.
     */
    private Mono<Void> validateFinanceTypeSpecificFields(SupplyChainFinanceAgreementDTO dto) {
        if (dto.getFinanceType() == null) {
            return Mono.error(new IllegalArgumentException("Finance type is required"));
        }

        if (dto.getFinanceType() == FinanceTypeEnum.FACTORING) {
            return validateFactoringFields(dto);
        } else if (dto.getFinanceType() == FinanceTypeEnum.CONFIRMING) {
            return validateConfirmingFields(dto);
        }

        return Mono.empty();
    }

    private Mono<Void> validateFactoringFields(SupplyChainFinanceAgreementDTO dto) {
        // Factoring-specific validation
        if (dto.getRecourse() == null) {
            return Mono.error(new IllegalArgumentException("Recourse field is required for factoring agreements"));
        }
        if (dto.getAdvanceRate() == null) {
            return Mono.error(new IllegalArgumentException("Advance rate is required for factoring agreements"));
        }

        log.debug("Validated factoring-specific fields for agreement");
        return Mono.empty();
    }

    private Mono<Void> validateConfirmingFields(SupplyChainFinanceAgreementDTO dto) {
        // Confirming-specific validation
        if (dto.getSupplierEarlyPaymentOption() == null) {
            return Mono.error(new IllegalArgumentException("Supplier early payment option is required for confirming agreements"));
        }
        if (dto.getStandardPaymentTermDays() == null) {
            return Mono.error(new IllegalArgumentException("Standard payment term days is required for confirming agreements"));
        }

        log.debug("Validated confirming-specific fields for agreement");
        return Mono.empty();
    }
}
