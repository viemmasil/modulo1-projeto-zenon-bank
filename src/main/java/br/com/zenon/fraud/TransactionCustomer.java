package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.Objects;

public record TransactionCustomer(String name, BigDecimal oldBalance, BigDecimal newBalance) {

    public TransactionCustomer {
        Objects.requireNonNull(name, "name is null");
        Objects.requireNonNull(oldBalance, "oldBalance is null");
        Objects.requireNonNull(newBalance, "newBalance is null");

        if (oldBalance.signum() < 0) {
            throw new IllegalArgumentException("OldBalance should be positive: " + oldBalance);
        }
        if (newBalance.signum() < 0) {
            throw new IllegalArgumentException("NewBalance should be positive: " + newBalance);
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name should not be empty");
        }
    }
}
