package org.doksanbir.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Transaction {

    //TODO: validate all fields.

    private long id;

    //nonnull
    private TransactionType type;
    // nonnull
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String description;



}
