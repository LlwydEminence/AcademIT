package common;

public interface TemperatureConverter {
    double convertTemperatureToCelsius(double temperature);
    double convertCelsiusToTemperature(double celsius);
    String getName();
}
