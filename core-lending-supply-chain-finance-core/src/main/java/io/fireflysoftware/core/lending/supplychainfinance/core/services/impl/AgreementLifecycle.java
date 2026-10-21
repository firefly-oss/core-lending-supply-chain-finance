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

import io.fireflysoftware.core.lending.supplychainfinance.interfaces.enums.AgreementStatusEnum;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Allowed moves for a supply-chain finance agreement. A draft is the only status that can be removed.
 */
final class AgreementLifecycle {

    private static final Map<AgreementStatusEnum, Set<AgreementStatusEnum>> ALLOWED = Map.of(
            AgreementStatusEnum.DRAFT, EnumSet.of(
                    AgreementStatusEnum.PENDING_APPROVAL, AgreementStatusEnum.ACTIVE, AgreementStatusEnum.TERMINATED),
            AgreementStatusEnum.PENDING_APPROVAL, EnumSet.of(
                    AgreementStatusEnum.DRAFT, AgreementStatusEnum.ACTIVE, AgreementStatusEnum.TERMINATED),
            AgreementStatusEnum.ACTIVE, EnumSet.of(
                    AgreementStatusEnum.SUSPENDED, AgreementStatusEnum.CLOSED, AgreementStatusEnum.TERMINATED),
            AgreementStatusEnum.SUSPENDED, EnumSet.of(
                    AgreementStatusEnum.ACTIVE, AgreementStatusEnum.CLOSED, AgreementStatusEnum.TERMINATED)
    );

    private AgreementLifecycle() {
    }

    static boolean canTransition(AgreementStatusEnum from, AgreementStatusEnum to) {
        if (from == to) {
            return true;
        }
        Set<AgreementStatusEnum> next = ALLOWED.get(from);
        return next != null && next.contains(to);
    }
}
