CREATE TABLE category (
	id INT auto_increment NOT NULL,
	category_name varchar(100) NULL,
	is_active BOOL DEFAULT true NULL,
	CONSTRAINT category_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

