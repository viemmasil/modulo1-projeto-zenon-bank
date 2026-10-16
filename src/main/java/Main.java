import br.com.zenon.fraud.FraudAnalyzer;
import br.com.zenon.fraud.Transaction;
import br.com.zenon.fraud.TransactionIngestor;

void main() {
    var transactionIngestor = new TransactionIngestor();
    List<Transaction> transactions = transactionIngestor.readFile("data/kaggle_log.csv");

    var fraudAnalyzer = new FraudAnalyzer(transactions);

    IO.println("1. Total of Frauds: " + fraudAnalyzer.getCountFraudulentTransactions());

    IO.println("2. Top 3 Frauds Biggest Amounts:");
    fraudAnalyzer.findHighestValueFrauds(3L)
            .forEach(amount -> IO.println("%.2f".formatted(amount)));

    IO.println("3. Suspicious Customers:");
    fraudAnalyzer.findTopSuspiciousCustomers(5L).forEach(IO::println);

    IO.println("4. Total Loss in Frauds: " + fraudAnalyzer.getTotalFraudLoss());

    IO.println("5. Frauds by Type:");
    fraudAnalyzer.getFraudsByType()
            .forEach((type, amount) -> IO.println("- %s: %d".formatted(type, amount)));
}
