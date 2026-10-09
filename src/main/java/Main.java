import br.com.zenon.fraud.Transaction;

import static br.com.zenon.fraud.Transaction.Type.CASH_OUT;
import static br.com.zenon.fraud.Transaction.Type.PAYMENT;

void main() {

    var transaction1 = new Transaction(1L, PAYMENT, new BigDecimal("9839.64"), "C1231006815",
            new BigDecimal("170136.0"), new BigDecimal("160296.36"), "M1979787155", BigDecimal.ZERO,
            BigDecimal.ZERO, false, false);

    var transaction2 = new Transaction(743L, CASH_OUT, new BigDecimal("850002.52"), "C1280323807",
            new BigDecimal("850002.52"), BigDecimal.ZERO, "C873221189", new BigDecimal("6510099.11"),
            new BigDecimal("7360101.63"), true, false);

    IO.println("\nTransaction 1:\n" + transaction1);
    IO.println("\nTransaction 2:\n" + transaction2);
}
