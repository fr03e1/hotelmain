-- liquibase formatted sql

-- changeset a.bocharov:V3_create_table_city
CREATE TABLE city
(
    id         SERIAL NOT NULL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    country_id BIGINT       NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_country_id FOREIGN KEY (country_id) REFERENCES country (id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS city_country_id ON city (country_id);