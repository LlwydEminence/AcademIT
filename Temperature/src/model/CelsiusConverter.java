package model;

import common.TemperatureConverter;

public class CelsiusConverter implements TemperatureConverter {
    private static final String NAME = "Шкала Цельсия";

    @Override
    public double convertTemperatureToCelsius(double temperature) {
        return temperature;
    }

    @Override
    public double convertCelsiusToTemperature(double celsius) {
        return celsius;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
