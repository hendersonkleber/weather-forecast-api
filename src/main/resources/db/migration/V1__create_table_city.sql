CREATE TABLE city
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at      TIMESTAMP               DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP               DEFAULT CURRENT_TIMESTAMP,
    last_sync_at    TIMESTAMP,
    priority        VARCHAR(10)    NOT NULL DEFAULT 'LOW',
    name            VARCHAR(120)   NOT NULL,
    name_normalized VARCHAR(120)   NOT NULL,
    latitude        NUMERIC(10, 8) NOT NULL,
    longitude       NUMERIC(11, 8) NOT NULL,
    state           VARCHAR(120),
    country         VARCHAR(120)   NOT NULL
);

CREATE INDEX idx_city_name_normalized ON city (name_normalized);
CREATE INDEX idx_city_priority ON city (priority);