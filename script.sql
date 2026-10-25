CREATE TABLE transactions
(
    id                    BIGINT UNSIGNED                                               NOT NULL PRIMARY KEY AUTO_INCREMENT,
    step                  INT                                                           NOT NULL,
    transaction_type      ENUM ('CASH_IN', 'CASH_OUT', 'DEBIT', 'PAYMENT', 'TRANSFER' ) NOT NULL,
    amount                DECIMAL(20, 4)                                                NOT NULL,
    origin_name           VARCHAR(100)                                                  NOT NULL,
    origin_old_balance    DECIMAL(20, 4)                                                NOT NULL,
    origin_new_balance    DECIMAL(20, 4)                                                NOT NULL,
    recipient_name        VARCHAR(100)                                                  NOT NULL,
    recipient_old_balance DECIMAL(20, 4)                                                NOT NULL,
    recipient_new_balance DECIMAL(20, 4)                                                NOT NULL,
    is_fraud              BOOLEAN                                                       NOT NULL DEFAULT FALSE,
    is_flagged_fraud      BOOLEAN                                                       NOT NULL DEFAULT FALSE
);