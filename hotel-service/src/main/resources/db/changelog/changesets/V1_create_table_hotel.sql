-- liquibase formatted sql

-- changeset a.bocharov:V1_create_table_hotel
CREATE TABLE hotel
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    address     VARCHAR(255),
    city        VARCHAR(100),
    country     VARCHAR(100),
    description TEXT,
    rating      DECIMAL(2, 1),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);