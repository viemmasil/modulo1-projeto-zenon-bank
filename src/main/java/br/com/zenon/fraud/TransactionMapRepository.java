package br.com.zenon.fraud;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransactionMapRepository implements TransactionRepository {
    private final Map<String, Transaction> mapTransactions;

    public TransactionMapRepository(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        // transactions.forEach(txn -> mapTransactions.put(txn.origin().name(), txn));
        mapTransactions = transactions.stream().collect(Collectors.toMap(txn -> txn.origin().name(),
                Function.identity(), (existingTxn, newTxn) -> newTxn));
    }

    @Override
    public Optional<Transaction> findByOriginName(String originName) {
        return Optional.ofNullable(this.mapTransactions.get(originName));
    }

    @Override
    public void save(Transaction transaction) {
        this.mapTransactions.putIfAbsent(transaction.origin().name(), transaction);
    }
}
