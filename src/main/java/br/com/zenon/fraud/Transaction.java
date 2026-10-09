package br.com.zenon.fraud;

import java.math.BigDecimal;

public record Transaction(long step, Type type, BigDecimal amount, String nameOrig, BigDecimal oldBalanceOrg,
                          BigDecimal newBalanceOrig, String nameDest, BigDecimal oldBalanceDest,
                          BigDecimal newBalanceDest, boolean isFraud, boolean isFlaggedFraud) {
    public enum Type {
        CASH_IN, CASH_OUT, DEBIT, PAYMENT, TRANSFER
    }

    @Override
    public String toString() {
        return "step: " + step +
                "\ntype: " + type +
                "\namount: " + amount +
                "\nnameOrig: " + nameOrig +
                "\noldBalanceOrg: " + oldBalanceOrg +
                "\nnewBalanceOrig: " + newBalanceOrig +
                "\nnameDest: " + nameDest +
                "\noldBalanceDest: " + oldBalanceDest +
                "\nnewBalanceDest: " + newBalanceDest +
                "\nisFraud: " + (isFraud ? "1" : "0") +
                "\nisFlaggedFraud: " + (isFlaggedFraud ? "1" : "0");
    }
}
