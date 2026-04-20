CREATE TABLE weather_forecast_hour
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    hour TIME NOT NULL,
    weather_forecast_day_id BIGINT NOT NULL,
    condition VARCHAR(30),
    cloud_cover INTEGER,
    temperature_2m DOUBLE PRECISION,
    apparent_temperature DOUBLE PRECISION,
    precipitation DOUBLE PRECISION,
    precipitation_probability DOUBLE PRECISION,
    relative_humidity_2m INTEGER,
    wind_direction_10m VARCHAR(20),
    wind_speed_10m DOUBLE PRECISION,

    CONSTRAINT fk_weather_forecast_hour_day FOREIGN KEY (weather_forecast_day_id) REFERENCES weather_forecast_day(id) ON DELETE CASCADE
);