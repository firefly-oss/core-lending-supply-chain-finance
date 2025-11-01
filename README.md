# Core Lending Supply Chain Finance

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)

A unified microservice for managing both **factoring** and **confirming** supply chain finance operations within the Firefly lending platform.

---

## Table of Contents

- [Overview](#overview)
- [Purpose](#purpose)
- [Architecture](#architecture)
  - [Module Structure](#module-structure)
  - [Technology Stack](#technology-stack)
  - [Key Design Principles](#key-design-principles)
- [Domain Model](#domain-model)
  - [Entity Relationship Diagram](#entity-relationship-diagram)
  - [Entities](#entities)
  - [Enumerations](#enumerations)
- [API Endpoints](#api-endpoints)
  - [Supply Chain Finance Agreements](#supply-chain-finance-agreements)
  - [Counterparties](#counterparties)
  - [Invoices](#invoices)
  - [Advances](#advances)
  - [Fees](#fees)
  - [Settlements](#settlements)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Environment Variables](#environment-variables)
  - [Build and Run](#build-and-run)
- [Development](#development)
  - [Project Structure](#project-structure)
  - [Database Migrations](#database-migrations)
  - [Testing](#testing)
- [Integration](#integration)
- [License](#license)

---

## Overview

This microservice consolidates the previously separate `core-lending-factoring` and `core-lending-confirming` services into a single, unified codebase. The two modules shared approximately 85% of their code, making this merge a significant improvement in maintainability and consistency.

**Key Benefits:**
- ✅ 85% code reduction through elimination of duplication
- ✅ Unified API for both factoring and confirming
- ✅ Single deployment reduces operational complexity
- ✅ Easier maintenance with one codebase
- ✅ Consistent business logic across finance types

---

## Purpose

The **Core Lending Supply Chain Finance** microservice is responsible for:

1. **Managing Supply Chain Finance Agreements** - Creating and maintaining both factoring and confirming agreements with customers
2. **Counterparty Management** - Managing debtors (in factoring) and suppliers (in confirming) with credit limits and exposure tracking
3. **Invoice Processing** - Handling invoice registration, verification, approval, and financing workflows
4. **Advance/Early Payment Processing** - Managing advances to suppliers in factoring and early payments to suppliers in confirming
5. **Fee Management** - Configuring and tracking various fee structures (discount fees, service fees, administration fees, etc.)
6. **Settlement Processing** - Managing final settlement of invoices including interest, fees, and balance calculations
7. **Credit Limit Monitoring** - Tracking available limits, outstanding amounts, and counterparty exposure

This microservice does **NOT** handle:
- Payment processing (handled by `core-lending-loan-servicing`)
- Repayment schedules (handled by `core-lending-loan-servicing`)
- Installment calculations (handled by `core-lending-loan-servicing`)

### Supply Chain Finance Lifecycle

The complete lifecycle of a supply chain finance operation follows this flow:

#### Factoring Flow

```
1. Agreement Created (FACTORING)
   ↓
2. Debtor (Counterparty) Registered & Approved
   ↓
3. 📄 INVOICE Registered
   │  Status: REGISTERED → PENDING_VERIFICATION → VERIFIED → APPROVED
   │  Tracks: Invoice details, debtor, amounts, due dates
   ↓
4. 💰 ADVANCE to Supplier
   │  Advance amount = Invoice amount × Advance rate
   │  Deducts: Discount fees, service fees
   │  Net amount paid to supplier
   ↓
5. Invoice Status: FINANCED
   ↓
6. Debtor Pays Invoice (at due date)
   │  Status: PAID
   ↓
7. 📋 SETTLEMENT
   │  Calculates: Settlement amount - Advanced amount - Interest - Fees
   │  Balance due paid to supplier (if positive) or collected from supplier (if negative)
   ↓
8. Invoice Status: SETTLED
```

#### Confirming Flow

```
1. Agreement Created (CONFIRMING)
   ↓
2. Supplier (Counterparty) Registered & Approved
   ↓
3. 📄 INVOICE Registered by Buyer
   │  Status: REGISTERED → PENDING_VERIFICATION → VERIFIED → CONFIRMED
   │  Tracks: Invoice details, supplier, amounts, payment terms
   ↓
4. Supplier Requests Early Payment (Optional)
   ↓
5. 💰 EARLY PAYMENT to Supplier
   │  Early payment amount = Invoice amount - Discount
   │  Deducts: Early payment fees
   │  Net amount paid to supplier
   ↓
6. Invoice Status: FINANCED
   ↓
7. Buyer Pays at Standard Payment Term
   │  Status: PAID
   ↓
8. 📋 SETTLEMENT
   │  Reconciles payment from buyer
   │  Calculates final amounts
   ↓
9. Invoice Status: SETTLED
```

**Key Differences:**
- **Factoring**: Supplier sells receivables to factor; factor advances funds and collects from debtor
- **Confirming**: Buyer confirms invoices; supplier can request early payment; buyer pays at standard terms
- **Counterparty Role**: In factoring = debtor (who owes); In confirming = supplier (who is owed)
- **Risk**: Factoring can be recourse or non-recourse; Confirming is typically non-recourse to supplier

---

## Architecture

### Module Structure

```
core-lending-supply-chain-finance/
├── core-lending-supply-chain-finance-interfaces/  # DTOs, Enums, API Contracts
├── core-lending-supply-chain-finance-models/      # JPA Entities, Repositories, Database Schema
├── core-lending-supply-chain-finance-core/        # Business Logic, Services, Mappers
├── core-lending-supply-chain-finance-web/         # REST Controllers, Spring Boot Application
└── core-lending-supply-chain-finance-sdk/         # Client SDK, OpenAPI Specification
```

**Module Responsibilities:**

- **interfaces**: Data Transfer Objects (DTOs) and enumerations used across all layers
- **models**: Database entities, R2DBC repositories, and Flyway migration scripts
- **core**: Service interfaces and implementations, MapStruct mappers, business logic
- **web**: REST API controllers, Spring Boot configuration, application entry point
- **sdk**: Client SDK generation and OpenAPI specification

### Technology Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.x with Spring WebFlux (Reactive)
- **Database**: PostgreSQL with R2DBC (Reactive Database Connectivity)
- **Migration**: Flyway
- **API Documentation**: SpringDoc OpenAPI 3.0
- **Mapping**: MapStruct
- **Build Tool**: Maven
- **Architecture**: Reactive, Non-blocking I/O

### Key Design Principles

1. **Finance Type Discriminator**: The unified model uses a `financeType` field (`FACTORING` or `CONFIRMING`) to distinguish between use cases, allowing shared infrastructure while maintaining business logic flexibility.

2. **Reactive Programming**: Built on Spring WebFlux and R2DBC for non-blocking, scalable operations.

3. **Domain-Driven Design**: Clear separation between domain entities, DTOs, and service layers.

4. **External Integration**: Links to `core-lending-loan-servicing` for payment processing and account management.

---

## Domain Model

### Entity Relationship Diagram

```mermaid
erDiagram
    SUPPLY_CHAIN_FINANCE_AGREEMENT ||--o{ COUNTERPARTY : "has counterparties"
    SUPPLY_CHAIN_FINANCE_AGREEMENT ||--o{ SUPPLY_CHAIN_FINANCE_INVOICE : "has invoices"
    SUPPLY_CHAIN_FINANCE_AGREEMENT ||--o{ SUPPLY_CHAIN_FINANCE_FEE : "has fees"
    COUNTERPARTY ||--o{ SUPPLY_CHAIN_FINANCE_INVOICE : "issues/receives invoices"
    SUPPLY_CHAIN_FINANCE_INVOICE ||--o{ SUPPLY_CHAIN_FINANCE_ADVANCE : "has advances"
    SUPPLY_CHAIN_FINANCE_INVOICE ||--o{ SUPPLY_CHAIN_FINANCE_SETTLEMENT : "has settlements"

    SUPPLY_CHAIN_FINANCE_AGREEMENT {
        uuid id PK
        finance_type_enum finance_type "NOT NULL - FACTORING or CONFIRMING"
        varchar agreement_number "NOT NULL - Unique agreement number"
        uuid customer_id FK "NOT NULL - Reference to customer"
        agreement_status_enum status "NOT NULL - Agreement status"
        date start_date "NOT NULL"
        date end_date "NOT NULL"
        currency_code_enum currency "NOT NULL"
        decimal credit_limit "NOT NULL - Maximum credit limit"
        decimal available_limit "NOT NULL - Available credit"
        decimal outstanding_amount "NOT NULL - Current outstanding"
        boolean recourse "NULLABLE - Factoring: with/without recourse"
        boolean notification_required "NULLABLE - Factoring: debtor notification"
        decimal advance_rate "NULLABLE - Factoring: advance percentage"
        boolean supplier_early_payment_option "NULLABLE - Confirming: early payment available"
        integer standard_payment_term_days "NULLABLE - Confirming: standard payment terms"
        integer early_payment_discount_days "NULLABLE - Confirming: early payment discount period"
        decimal interest_rate "NULLABLE - Annual interest rate"
        text notes "NULLABLE"
        uuid created_by FK "NOT NULL"
        uuid last_modified_by FK "NOT NULL"
        timestamp created_at "NOT NULL"
        timestamp updated_at "NOT NULL"
    }

    COUNTERPARTY {
        uuid id PK
        uuid agreement_id FK "NOT NULL"
        finance_type_enum finance_type "NOT NULL"
        varchar counterparty_name "NOT NULL - Max 200 chars"
        varchar tax_id "NULLABLE - Max 50 chars"
        varchar registration_number "NULLABLE - Max 100 chars"
        text address "NULLABLE"
        varchar contact_person "NULLABLE - Max 200 chars"
        varchar contact_email "NULLABLE - Max 200 chars"
        varchar contact_phone "NULLABLE - Max 50 chars"
        boolean approved "DEFAULT FALSE"
        decimal approved_limit "NULLABLE - Credit limit for this counterparty"
        decimal current_exposure "NULLABLE - Current exposure amount"
        varchar credit_rating "NULLABLE - Max 20 chars"
        text notes "NULLABLE"
        timestamp created_at "NOT NULL"
        timestamp updated_at "NOT NULL"
    }

