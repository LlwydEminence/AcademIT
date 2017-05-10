package model;

public class TemperatureConverter {
    public double convertCelsiusToFahrenheit(double celsius) {
        return celsius * 9.0 / 5.0 + 32.0;
    }

    public double convertCelsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    public double convertFahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32.0) * 5.0 / 9.0;
    }

    public double convertFahrenheitToKelvin(double fahrenheit) {
        return convertCelsiusToKelvin(convertFahrenheitToCelsius(fahrenheit));
    }

    public double convertKelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    public double convertKelvinToFahrenheit(double kelvin) {
        return convertCelsiusToFahrenheit(convertKelvinToCelsius(kelvin));
    }
}
