DROP TABLE "transaction";

CREATE TABLE "transaction"  (
    id UUID NOT NULL PRIMARY KEY,
    amount NUMERIC(19, 2) NOT NULL,
    description TEXT NOT NULL,
    transaction_date DATE NOT NULL,
    transaction_time TIME NOT NULL,
    account_id UUID,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (account_id) REFERENCES account(id)
);