package br.com.zenon.fraud;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TransactionSqlRepository implements TransactionRepository {

    private final DataSource dataSource;

    public TransactionSqlRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String INSERT_SQL = """
             insert into transactions (step, transaction_type, amount, origin_name, origin_old_balance, origin_new_balance,
                                       recipient_name, recipient_old_balance, recipient_new_balance, is_fraud, is_flagged_fraud)
             values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

    @Override
    public Optional<Transaction> findByOriginName(String originName) {
        String query = "SELECT * FROM transactions WHERE origin_name = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, originName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int step = rs.getInt("step");
                    var type = TransactionType.valueOf(rs.getString("transaction_type"));
                    var amount = rs.getBigDecimal("amount");
                    var origin = new TransactionCustomer(rs.getString("origin_name"),
                            rs.getBigDecimal("origin_old_balance"), rs.getBigDecimal("origin_new_balance"));
                    var recipient = new TransactionCustomer(rs.getString("recipient_name"),
                            rs.getBigDecimal("recipient_old_balance"), rs.getBigDecimal("recipient_new_balance"));
                    boolean isFraud = rs.getBoolean("is_fraud");
                    boolean isFlaggedFraud = rs.getBoolean("is_flagged_fraud");

                    var txn = new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud);
                    return Optional.of(txn);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Transaction txn) {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
            mapStatement(stmt, txn);
            stmt.execute();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveInBatches(List<Transaction> transactions, int batchSize) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
                int counter = 0;
                for (Transaction txn : transactions) {
                    mapStatement(stmt, txn);
                    stmt.addBatch();

                    if (++counter % batchSize == 0) {
                        stmt.executeBatch();
                        conn.commit();
                    }
                }
                // Process remaining records
                if (counter % batchSize != 0) {
                    stmt.executeBatch();
                    conn.commit();
                }

            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void mapStatement(PreparedStatement stmt, Transaction txn) throws SQLException {
        stmt.setInt(1, txn.step());
        stmt.setString(2, txn.type().name());
        stmt.setBigDecimal(3, txn.amount());
        stmt.setString(4, txn.origin().name());
        stmt.setBigDecimal(5, txn.origin().oldBalance());
        stmt.setBigDecimal(6, txn.origin().newBalance());
        stmt.setString(7, txn.recipient().name());
        stmt.setBigDecimal(8, txn.recipient().oldBalance());
        stmt.setBigDecimal(9, txn.recipient().newBalance());
        stmt.setBoolean(10, txn.isFraud());
        stmt.setBoolean(11, txn.isFlaggedFraud());
    }
}
