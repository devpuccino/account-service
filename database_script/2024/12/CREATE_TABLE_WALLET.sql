CREATE TABLE wallet (
	wallet_id INT auto_increment NOT NULL,
	wallet_name varchar(200) NOT NULL,
	currency varchar(20) NOT NULL,
	wallet_type varchar(50) NOT NULL,
	card_type varchar(100) NOT NULL,
	balance DECIMAL DEFAULT NULL NULL,
	credit_limit DECIMAL DEFAULT NULL NULL,
	payment_due_date INT NULL,
	billing_date INT NULL,
	CONSTRAINT Wallet_PK PRIMARY KEY (wallet_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;
