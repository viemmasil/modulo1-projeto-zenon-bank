package br.com.zenon.fraud;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionIngestor {

    public List<Transaction> readFile(String fileName) {
        List<Transaction> transactions = new ArrayList<>();
        Path path = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            lines.stream().skip(1).map(this::extractTransaction)
                    .forEach(transaction -> transaction.ifPresent(transactions::add));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file.", e);
        }
        return transactions;
    }

    private Optional<Transaction> extractTransaction(String line) {
        try {
            String[] chunks = line.split(",");
            int step = Integer.parseInt(chunks[0]);
            TransactionType type = TransactionType.valueOf(chunks[1]);
            BigDecimal amount = new BigDecimal(chunks[2]);
            TransactionCustomer origin = new TransactionCustomer(chunks[3], new BigDecimal(chunks[4]),
                    new BigDecimal(chunks[5]));
            TransactionCustomer recipient = new TransactionCustomer(chunks[6], new BigDecimal(chunks[7]),
                    new BigDecimal(chunks[8]));
            boolean isFraud = "1".equals(chunks[9]);
            boolean isFlaggedFraud = "1".equals(chunks[10]);

            return Optional.of(new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud));

        } catch (Exception e) {
            System.err.printf("Error: %s | %s: %s%n", line, e.getClass().getName(), e.getMessage());
            return Optional.empty();
        }
    }

}
