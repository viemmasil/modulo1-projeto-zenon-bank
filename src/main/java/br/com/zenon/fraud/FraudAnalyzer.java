package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FraudAnalyzer {
    private final List<Transaction> transactions;

    public FraudAnalyzer(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    public long getCountFraudulentTransactions() {
        return getFraudStream().count();
    }

    public List<BigDecimal> findHighestValueFrauds(long limit) {
        return getHighestValueFraudStream()
                .limit(limit)
                .map(Transaction::amount)
                .toList();
    }

    public List<String> findTopSuspiciousCustomers(long limit) {
        return getHighestValueFraudStream()
                .map(txn -> txn.origin().name())
                .distinct()
                .limit(limit)
                .toList();
    }

    public BigDecimal getTotalFraudLoss() {
        return getFraudStream()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<TransactionType, Long> getFraudsByType() {
        return getFraudStream()
                .collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
    }

    private Stream<Transaction> getFraudStream() {
        return transactions.stream()
                .filter(Transaction::isFraud);
    }

    private Stream<Transaction> getHighestValueFraudStream() {
        return getFraudStream()
                .sorted(Comparator.comparing(Transaction::amount).reversed());
    }
}
