-- liquibase formatted sql

-- changeset a.bocharov:V2_create_table_country
CREATE TABLE country
(
    id         SERIAL NOT NULL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    code       VARCHAR(10)  NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);