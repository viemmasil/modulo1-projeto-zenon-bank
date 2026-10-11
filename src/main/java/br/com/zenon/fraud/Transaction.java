package br.com.zenon.fraud;

import java.math.BigDecimal;

public record Transaction(int step, TransactionType type, BigDecimal amount, TransactionCustomer origin,
                          TransactionCustomer recipient, boolean isFraud, boolean isFlaggedFraud) {

    @Override
    public String toString() {
        return "step: " + step + "\ntype: " + type + "\namount: " + amount + "\norigin: " + origin + "\nrecipient: "
            + recipient + "\nisFraud: " + (isFraud ? "1" : "0") + "\nisFlaggedFraud: " + (isFlaggedFraud ? "1" : "0");
    }
}
