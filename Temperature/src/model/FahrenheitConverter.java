package model;

import common.TemperatureConverter;

public class FahrenheitConverter implements TemperatureConverter {
    private static final String NAME = "Шкала Фаренгейта";

    @Override
    public double convertTemperatureToCelsius(double temperature) {
        return (5.0 / 9.0) * (temperature - 32);
    }

    @Override
    public double convertCelsiusToTemperature(double celsius) {
        return (9.0 / 5.0) * celsius + 32;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
