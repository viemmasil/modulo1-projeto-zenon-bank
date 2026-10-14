package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.Objects;

public record Transaction(int step, TransactionType type, BigDecimal amount, TransactionCustomer origin,
                          TransactionCustomer recipient, boolean isFraud, boolean isFlaggedFraud) {

    public Transaction {
        Objects.requireNonNull(type, "type is null");
        Objects.requireNonNull(amount, "amount is null");
        Objects.requireNonNull(origin, "origin is null");
        Objects.requireNonNull(recipient, "recipient is null");

        if (step <= 0) {
            throw new IllegalArgumentException("Step should be positive: " + step);
        }
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("Amount should be positive: " + amount);
        }
    }
}
