package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FraudAnalyzer {
    TransactionIngestor transactionIngestor = new TransactionIngestor();

    List<Transaction> transactions = transactionIngestor.readFile("data/kaggle_log.csv");

    public long getCountFraudulentTransactions() {
        return transactions.stream().filter(Transaction::isFraud).count();
    }

    public List<BigDecimal> getTop3FraudulentAmounts() {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .limit(3)
                .map(Transaction::amount)
                .toList();
    }

    public List<String> getBiggestSuspectCustomers() {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .distinct()
                .map(txn -> txn.origin().name())
                .limit(5)
                .toList();
    }

    public BigDecimal getTotalFraudLoss() {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<TransactionType, Long> getFraudsByType() {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
    }
}
