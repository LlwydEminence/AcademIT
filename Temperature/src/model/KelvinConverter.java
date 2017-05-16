package model;

import common.TemperatureConverter;

public class KelvinConverter implements TemperatureConverter{
    private final static String NAME = "Шкала Кельвина";

    @Override
    public double convertTemperatureToCelsius(double temperature) {
        return temperature - 273.15;
    }

    @Override
    public double convertCelsiusToTemperature(double celsius) {
        return celsius + 273.15;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
