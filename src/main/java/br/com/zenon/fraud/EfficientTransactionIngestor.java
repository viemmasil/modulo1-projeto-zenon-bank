package br.com.zenon.fraud;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EfficientTransactionIngestor extends TransactionIngestor {

    private static final int LIMIT = 10_000;
    private static final int BATCH = 2_000;

    private final Semaphore dbPermits = new Semaphore(16);

    public void readAsStream(String fileName, Consumer<Transaction> consumer) {
        Path path = Path.of(fileName);
        try (Stream<String> lines = Files.lines(path)) {
            lines.skip(1).limit(LIMIT)
                    .map(this::extractTransaction)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(consumer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file as stream", e);
        }
    }

    public void readBatch(String fileName, Consumer<List<Transaction>> consumer) {
        Path path = Path.of(fileName);
        List<String> transactions = new ArrayList<>(BATCH);

        try (var executor = Executors.newVirtualThreadPerTaskExecutor();
             Stream<String> lines = Files.lines(path).skip(1)) {

            var iterator = lines.iterator();
            while (iterator.hasNext()) {
                String line = iterator.next();
                transactions.add(line);

                if (transactions.size() >= BATCH) {
                    final var currentBatch = List.copyOf(transactions);
                    executor.submit(() -> executeBatch(currentBatch, consumer));
                    transactions.clear();
                }
            }
            if (!transactions.isEmpty()) {
                final var finalBatch = List.copyOf(transactions);
                IO.println("Final Batch Processing: " + finalBatch.size());
                executor.submit(() -> executeBatch(finalBatch, consumer));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file as stream", e);
        }
    }

    private void executeBatch(List<String> lines, Consumer<List<Transaction>> consumer) {
        List<Transaction> transactions = lines.stream()
                .map(this::extractTransaction)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        try {
            dbPermits.acquire();
            try {
                consumer.accept(transactions);
            } finally {
                dbPermits.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
