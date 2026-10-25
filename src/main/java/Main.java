import br.com.zenon.fraud.Transaction;
import br.com.zenon.fraud.TransactionIngestor;
import br.com.zenon.fraud.TransactionListRepository;
import br.com.zenon.fraud.TransactionMapRepository;

void main() {
    var transactionIngestor = new TransactionIngestor();
    List<Transaction> transactions = transactionIngestor.readFile("data/kaggle_log.csv", 100_000);
    // List<Transaction> transactions = transactionIngestor.readFileNio2("data/kaggle_log.csv");

    IO.println("------------------------------------------------");
    IO.println("------------------ Task 06 ---------------------");
    var txnListRepository = new TransactionListRepository(transactions);
    var txnMapRepository = new TransactionMapRepository(transactions);

    List<String> originNames = List.of("C12345", "C1231006815");
    originNames.forEach(originName -> {
        var opTxnList = txnListRepository.findByOriginName(originName);
        opTxnList.ifPresentOrElse(IO::println,
                () -> IO.println("No transactions found for the customer %s".formatted(originName)));
    });
    IO.println("------------------------------------------------");
    String lastOriginName = "C1868032458";

    long startTime = System.nanoTime();
    txnListRepository.findByOriginName(lastOriginName)
            .ifPresentOrElse(IO::println, () -> IO.println("No transactions found for the customer " + lastOriginName));
    long elapsedNanos = System.nanoTime() - startTime;
    IO.println("List: Took %d nanos (%f ms) to find the last origin name.".formatted(elapsedNanos,
            elapsedNanos / 1_000_000.0));

    IO.println("------------------------------------------------");

    startTime = System.nanoTime();
    txnMapRepository.findByOriginName(lastOriginName)
            .ifPresentOrElse(IO::println, () -> IO.println("No transactions found for the customer " + lastOriginName));
    elapsedNanos = System.nanoTime() - startTime;
    IO.println("Map.: Took %d nanos (%f ms) to find the last origin name.".formatted(elapsedNanos,
            elapsedNanos / 1_000_000.0));

    /*IO.println("\n------------------------------------------------");
    IO.println("------------------ Task 05 ---------------------");
    var fiftyThousandTxn = transactionIngestor.readFile("data/kaggle_log.csv", 50_000);
    var fraudAnalyzer = new FraudAnalyzer(fiftyThousandTxn);

    IO.println("1. Total of Frauds: " + fraudAnalyzer.getCountFraudulentTransactions());

    IO.println("2. Top 3 Frauds Biggest Amounts:");
    fraudAnalyzer.findHighestValueFrauds(3L).forEach(amount -> IO.println("%.2f".formatted(amount)));

    IO.println("3. Suspicious Customers:");
    fraudAnalyzer.findTopSuspiciousCustomers(5L).forEach(IO::println);

    IO.println("4. Total Loss in Frauds: " + fraudAnalyzer.getTotalFraudLoss());

    IO.println("5. Frauds by Type:");
    fraudAnalyzer.getFraudsByType().forEach((type, amount) -> IO.println("- %s: %d".formatted(type, amount)));*/
}
