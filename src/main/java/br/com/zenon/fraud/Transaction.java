package br.com.zenon.fraud;

import java.math.BigDecimal;

public record Transaction(int step, TransactionType type, BigDecimal amount, TransactionCustomer origin,
                          TransactionCustomer recipient, boolean isFraud, boolean isFlaggedFraud) {

    public Transaction {
        if (step <= 0) {
            throw new IllegalArgumentException("step must be greater than zero");
        }
        if (amount.doubleValue() < 0 || origin.oldBalance().doubleValue() < 0 || origin.newBalance().doubleValue() < 0
                || recipient.oldBalance().doubleValue() < 0 || recipient.newBalance().doubleValue() < 0) {
            throw new IllegalArgumentException("values must be greater than zero");
        }
        if (null == origin.name() || origin.name().isEmpty() || null == recipient.name() || recipient.name().isEmpty()) {
            throw new IllegalArgumentException("name must not be null or empty");
        }
    }

    @Override
    public String toString() {
        return "Transaction{" + "step=" + step + ", type=" + type + ", amount=" + amount + ", origin=" + origin + ", "
                + "recipient=" + recipient + ", isFraud=" + isFraud + ", isFlaggedFraud=" + isFlaggedFraud + '}';
    }
}
