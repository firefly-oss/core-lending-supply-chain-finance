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


package com.firefly.core.lending.supplychainfinance.models.repositories;

import com.firefly.core.lending.supplychainfinance.interfaces.enums.FinanceTypeEnum;
import com.firefly.core.lending.supplychainfinance.models.entities.SupplyChainFinanceSettlement;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * Repository for settlements.
 */
@Repository
public interface SupplyChainFinanceSettlementRepository extends BaseRepository<SupplyChainFinanceSettlement> {

    Flux<SupplyChainFinanceSettlement> findByInvoiceId(UUID invoiceId);

    Flux<SupplyChainFinanceSettlement> findByFinanceType(FinanceTypeEnum financeType);

    Flux<SupplyChainFinanceSettlement> findByFinanceTypeAndInvoiceId(FinanceTypeEnum financeType, UUID invoiceId);
}
