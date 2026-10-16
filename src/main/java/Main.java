import br.com.zenon.fraud.FraudAnalyzer;

void main() {

    var fraudAnalyzer = new FraudAnalyzer();

    IO.println("1. Total of Frauds: " + fraudAnalyzer.getCountFraudulentTransactions());

    IO.println("2. Top 3 Frauds Biggest Amounts:");
    fraudAnalyzer.getTop3FraudulentAmounts()
            .forEach(bigDecimal -> IO.println(String.format(Locale.US, "%.2f", bigDecimal)));

    IO.println("3. Suspect Customers:");
    fraudAnalyzer.getBiggestSuspectCustomers().forEach(IO::println);

    IO.println("4. Total Loss in Frauds: " + fraudAnalyzer.getTotalFraudLoss());

    IO.println("5. Frauds by Type:");
    fraudAnalyzer.getFraudsByType()
            .forEach((type, amount) -> IO.println("- " + type + ": " + amount));
}
