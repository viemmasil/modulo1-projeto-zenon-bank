package br.com.zenon.fraud;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TransactionListRepository implements TransactionRepository {
    private final List<Transaction> transactions;

    public TransactionListRepository(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    @Override
    public Optional<Transaction> findByOriginName(String name) {
        return this.transactions.stream()
                .filter(txn -> txn.origin().name().equals(name)).findFirst();
    }

    @Override
    public void save(Transaction transaction) {
        this.transactions.add(transaction);
    }
}
