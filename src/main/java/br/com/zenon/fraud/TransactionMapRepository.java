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
                Function.identity()));
    }

    @Override
    public Optional<Transaction> getTransactionByOriginName(String originName) {
        return Optional.ofNullable(mapTransactions.get(originName));
    }
}
