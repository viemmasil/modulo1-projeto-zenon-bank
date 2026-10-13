package br.com.zenon.fraud;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TransactionIngestor {

    public List<Transaction> readFile(String fileName) {
        List<Transaction> transactions = new ArrayList<>();
        Path path = Paths.get(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line = reader.readLine(); // Reads first line to account for the header
            for (int i = 1; i <= 1000; i++) {
                line = reader.readLine();
                if (line != null) {
                    var transaction = extractTransaction(line);
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file.", e);
        }
        return transactions;
    }

    private Transaction extractTransaction(String line) {
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

        return new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud);
    }

}
