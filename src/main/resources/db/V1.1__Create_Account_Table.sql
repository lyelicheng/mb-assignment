DROP TABLE account IF EXISTS;

CREATE TABLE account  (
    id UUID NOT NULL PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL,
    account_type VARCHAR(20),
    customer_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);