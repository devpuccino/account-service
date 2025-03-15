CREATE TABLE account_db.`transaction` (
	transaction_id BIGINT auto_increment NOT NULL,
	transaction_type varchar(100) NOT NULL,
	transaction_amount DOUBLE NOT NULL,
	transaction_date DATETIME NOT NULL,
	wallet_id INT NOT NULL,
	CONSTRAINT transaction_pk PRIMARY KEY (transaction_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;
