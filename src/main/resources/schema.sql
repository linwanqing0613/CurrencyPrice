CREATE TABLE currency (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(100),
    rate DECIMAL(20, 8),
    updated_time VARCHAR(100)
);