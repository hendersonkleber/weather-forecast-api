CREATE TABLE weather_forecast_day
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date DATE NOT NULL,
    city_id BIGINT NOT NULL,
    condition VARCHAR(30),
    temperature_2m_max DOUBLE PRECISION,
    temperature_2m_min DOUBLE PRECISION,
    apparent_temperature_max DOUBLE PRECISION,
    apparent_temperature_min DOUBLE PRECISION,
    precipitation_sum DOUBLE PRECISION,
    relative_humidity_2m_min INTEGER,
    relative_humidity_2m_max INTEGER,
    precipitation_probability_max DOUBLE PRECISION,
    precipitation_probability_min DOUBLE PRECISION,
    sunrise_at TIMESTAMP,
    sunset_at TIMESTAMP,
    wind_speed_10m_max DOUBLE PRECISION,
    wind_direction_10m_dominant VARCHAR(20),

    CONSTRAINT fk_weather_forecast_day_city FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE CASCADE,
    CONSTRAINT uk_city_date UNIQUE (city_id, date)
);