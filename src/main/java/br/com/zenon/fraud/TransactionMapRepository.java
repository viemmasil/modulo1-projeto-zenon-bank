package br.com.zenon.fraud;

import java.util.*;

public class TransactionMapRepository implements TransactionRepository {
    Map<String, Transaction> mapTransactions =  new HashMap<>();

    public TransactionMapRepository(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        transactions.forEach(txn -> mapTransactions.put(txn.origin().name(), txn));
    }
    @Override
    public Optional<Transaction> getTransactionByOriginName(String originName) {
        return Optional.of(mapTransactions.get(originName));
    }
}
