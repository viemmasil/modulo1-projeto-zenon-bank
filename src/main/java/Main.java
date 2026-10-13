import br.com.zenon.fraud.TransactionIngestor;

void main() {

    var transactionIngestor = new TransactionIngestor();

    long before = System.currentTimeMillis();
    var transactions = transactionIngestor.readFile("data/kaggle_log.csv");
    long after = System.currentTimeMillis();
    IO.println(String.format("Time taken to read %d lines with IO approach: %d ms", transactions.size(),
            (after - before)));
    transactions.stream().limit(10).forEach(IO::println);
}
