import br.com.zenon.fraud.*;

void main() {
    var transactionIngestor = new TransactionIngestor();
    List<Transaction> transactions = transactionIngestor.readFile("data/kaggle_log.csv");

    IO.println("------------------------------------------------");
    IO.println("------------------ Task 06 ---------------------");
    var txnListRepository = new TransactionListRepository(transactions);
    var txnMapRepository = new TransactionMapRepository(transactions);

    List<String> originNames = List.of("C12345", "C1231006815");
    originNames.forEach(originName -> {
        var opTxnList = txnListRepository.getTransactionByOriginName(originName);
        if (opTxnList.isPresent()) {
            IO.println(opTxnList.get());
        } else {
            IO.println("No transactions found for the customer %s".formatted(originName));
        }
    });
    IO.println("------------------------------------------------");

    long startTime = System.nanoTime();
    var opTxnList2 = txnListRepository.getTransactionByOriginName("C1868032458");
    long elapsedNanos = System.nanoTime() - startTime;
    IO.println("List: Took %d nanos to find the last origin name. %n%s".formatted(elapsedNanos,
            opTxnList2.isPresent() ? opTxnList2.get() : "Not Found"));

    IO.println("------------------------------------------------");

    startTime = System.nanoTime();
    var opTxnMap = txnMapRepository.getTransactionByOriginName("C1868032458");
    elapsedNanos = System.nanoTime() - startTime;
    IO.println("Map: Took %d nanos to find the last origin name. %n%s".formatted(elapsedNanos,
            opTxnMap.isPresent() ? opTxnMap.get() : "Not Found"));

    IO.println("\n------------------------------------------------");
    IO.println("------------------ Task 05 ---------------------");
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
