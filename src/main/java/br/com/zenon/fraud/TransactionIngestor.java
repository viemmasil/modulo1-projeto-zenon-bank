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

    private final List<Transaction> transactions = new ArrayList<>();

    public TransactionIngestor(String fileName) {
        Path path = Paths.get(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line = reader.readLine(); // Reads first line to account for the header
            for (int i = 1; i <= 1000; i++) {
                line = reader.readLine();
                if (line != null) {
                    String[] chunks = line.split(",");
                    var transaction = new Transaction(Integer.parseInt(chunks[0]), TransactionType.valueOf(chunks[1]),
                            new BigDecimal(chunks[2]), new TransactionCustomer(chunks[3], new BigDecimal(chunks[4]),
                            new BigDecimal(chunks[5])), new TransactionCustomer(chunks[6], new BigDecimal(chunks[7]),
                            new BigDecimal(chunks[8])), chunks[9].equals("1"), chunks[10].equals("1"));
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file.", e);
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
