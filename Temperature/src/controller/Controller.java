package controller;

import gui.FrameView;
import model.TemperatureConverter;

public class Controller {
    private FrameView frameView;
    private TemperatureConverter temperatureConverter;

    public Controller(FrameView frameView, TemperatureConverter temperatureConverter) {
        this.frameView = frameView;
        this.temperatureConverter = temperatureConverter;
    }

    public void needConvertCelsiusToFahrenheit(double celsius) {
        frameView.printConvertedTemperature(temperatureConverter.convertCelsiusToFahrenheit(celsius));
    }

    public void needConvertCelsiusToKelvin(double celsius) {
        frameView.printConvertedTemperature(temperatureConverter.convertCelsiusToKelvin(celsius));
    }

    public void needConvertKelvinToCelsius(double kelvin) {
        frameView.printConvertedTemperature(temperatureConverter.convertKelvinToCelsius(kelvin));
    }

    public void needConvertKelvinToFahrenheit(double kelvin) {
        frameView.printConvertedTemperature(temperatureConverter.convertKelvinToFahrenheit(kelvin));
    }

    public void needConvertFahrenheitToCelsius(double fahrenheit) {
        frameView.printConvertedTemperature(temperatureConverter.convertFahrenheitToCelsius(fahrenheit));
    }

    public void needConvertFahrenheitToKelvin(double fahrenheit) {
        frameView.printConvertedTemperature(temperatureConverter.convertFahrenheitToKelvin(fahrenheit));
    }
}
