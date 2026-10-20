package br.com.zenon.fraud;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class TransactionReport {

    public Statistics generateReport(String fileName) {
        Path path = Path.of(fileName);

        try (Stream<String> streamLines = Files.lines(path, StandardCharsets.UTF_8)) {
            return streamLines.skip(1)
                    .map(TransactionReport::extractReportTransaction)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .reduce(Statistics.ZERO, Statistics::addReportTransaction, Statistics::add);
        } catch (IOException e) {
            throw new RuntimeException("Failed during file processing", e);
        }
    }

    private static Optional<ReportTransaction> extractReportTransaction(String line) {
        try {
            String[] chunks = line.split(",");
            BigDecimal amount = new BigDecimal(chunks[2]);
            boolean isFraud = "1".equals(chunks[9]);
            return Optional.of(new ReportTransaction(amount, isFraud));

        } catch (Exception e) {
            System.err.printf("Error: %s | %s: %s%n", line, e.getClass().getName(), e.getMessage());
            return Optional.empty();
        }
    }

    private record ReportTransaction(BigDecimal amount, boolean isFraud) {

    }

    public record Statistics(long totalTxn, long totalFraud, BigDecimal totalAmount) {
        private static final Statistics ZERO = new Statistics(0, 0, BigDecimal.ZERO);

        private Statistics addReportTransaction(ReportTransaction rt) {
            return new Statistics(totalTxn + 1, totalFraud + (rt.isFraud ? 1 : 0), totalAmount.add(rt.amount));
        }

        private Statistics add(Statistics stats) {
            return new Statistics(totalTxn + stats.totalTxn, totalFraud + stats.totalFraud,
                    totalAmount.add(stats.totalAmount));
        }
    }
}
