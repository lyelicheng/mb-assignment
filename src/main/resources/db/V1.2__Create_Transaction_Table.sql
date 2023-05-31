DROP TABLE "transaction" IF EXISTS;

CREATE TABLE "transaction"  (
    id UUID NOT NULL PRIMARY KEY,
    amount NUMERIC(19, 2) NOT NULL,
    description TEXT,
    transaction_date DATE,
    transaction_time TIME,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (account_id) REFERENCES account(id)
);