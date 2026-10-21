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
import io.fireflysoftware.core.lending.supplychainfinance.core.mappers.SupplyChainFinanceAgreementMapper;
import io.fireflysoftware.core.lending.supplychainfinance.core.services.SupplyChainFinanceAgreementService;
import io.fireflysoftware.core.lending.supplychainfinance.interfaces.dtos.SupplyChainFinanceAgreementDTO;
import io.fireflysoftware.core.lending.supplychainfinance.interfaces.enums.AgreementStatusEnum;
import io.fireflysoftware.core.lending.supplychainfinance.interfaces.enums.FinanceTypeEnum;
import io.fireflysoftware.core.lending.supplychainfinance.models.entities.SupplyChainFinanceAgreement;
import io.fireflysoftware.core.lending.supplychainfinance.models.repositories.SupplyChainFinanceAgreementRepository;
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
                .doOnNext(entity -> {
                    if (entity.getStatus() == null) {
                        entity.setStatus(AgreementStatusEnum.DRAFT);
                    }
                })
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
                    AgreementStatusEnum current = existingAgreement.getStatus() != null
                            ? existingAgreement.getStatus()
                            : AgreementStatusEnum.DRAFT;
                    SupplyChainFinanceAgreement updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setId(existingAgreement.getId());
                    if (updatedEntity.getStatus() == null) {
                        updatedEntity.setStatus(current);
                    }
                    if (!AgreementLifecycle.canTransition(current, updatedEntity.getStatus())) {
                        return Mono.error(new IllegalStateException(
                                "Agreement " + id + " cannot move from " + current + " to " + updatedEntity.getStatus()));
                    }
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO)
                .doOnSuccess(updated -> log.info("Updated {} agreement with ID: {}",
                        updated.getFinanceType(), updated.getId()));
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.findById(id)
                .flatMap(existing -> {
                    AgreementStatusEnum status = existing.getStatus() != null
                            ? existing.getStatus()
                            : AgreementStatusEnum.DRAFT;
                    if (status != AgreementStatusEnum.DRAFT) {
                        return Mono.error(new IllegalStateException(
                                "Agreement " + id + " is " + status
                                        + " and cannot be deleted; terminate it so the agreement history is kept"));
                    }
                    return repository.delete(existing);
                })
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
        } else if (dto.getFinanceType() == FinanceTypeEnum.REVERSE_FACTORING) {
            return validateReverseFactoringFields(dto);
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

    private Mono<Void> validateReverseFactoringFields(SupplyChainFinanceAgreementDTO dto) {
        if (dto.getSupplierEarlyPaymentOption() == null) {
            return Mono.error(new IllegalArgumentException("Supplier early payment option is required for reverse factoring agreements"));
        }
        if (dto.getStandardPaymentTermDays() == null) {
            return Mono.error(new IllegalArgumentException("Standard payment term days is required for reverse factoring agreements"));
        }

        log.debug("Validated reverse factoring fields for agreement");
        return Mono.empty();
    }
}
