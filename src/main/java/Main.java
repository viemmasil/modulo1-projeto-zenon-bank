import br.com.zenon.fraud.TransactionIngestor;

void main() {

    var transactionIngestor = new TransactionIngestor();

    var transactions = transactionIngestor.readFile("data/paysim_with_bad_data.csv");
    IO.println("Quantity: " + transactions.size());
    transactions.forEach(IO::println);
}
