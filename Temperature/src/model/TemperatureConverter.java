package model;

public class TemperatureConverter {
    private static final String CELSIUS_TO_KELVIN = "Цельсий в Кельвин";
    private static final String CELSIUS_TO_FAHRENHEIT = "Цельсий в Фаренгейт";
    private static final String FAHRENHEIT_TO_CELSIUS = "Фаренгейт в Цельсий";
    private static final String FAHRENHEIT_TO_KELVIN = "Фаренгейт в Кельвин";
    private static final String KELVIN_TO_CELSIUS = "Кельвин в Цельсий";
    private static final String KELVIN_TO_FAHRENHEIT = "Кельвин в Фаренгейт";
    private static final String[] TEMPERATURE_SCALES =
            new String[] {CELSIUS_TO_KELVIN, CELSIUS_TO_FAHRENHEIT, FAHRENHEIT_TO_CELSIUS, FAHRENHEIT_TO_KELVIN,
                    KELVIN_TO_CELSIUS, KELVIN_TO_FAHRENHEIT};

    private static double convertCelsiusToFahrenheit(double celsius) {
        return celsius * 9.0 / 5.0 + 32.0;
    }

    private static double convertCelsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    private static double convertFahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32.0) * 5.0 / 9.0;
    }

    private static double convertFahrenheitToKelvin(double fahrenheit) {
        return convertCelsiusToKelvin(convertFahrenheitToCelsius(fahrenheit));
    }

    private static double convertKelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    private static double convertKelvinToFahrenheit(double kelvin) {
        return convertCelsiusToFahrenheit(convertKelvinToCelsius(kelvin));
    }

    public static double convert(double temperature, String scale) {
        switch (scale) {
            case CELSIUS_TO_KELVIN: {
                return convertCelsiusToKelvin(temperature);
            }

            case CELSIUS_TO_FAHRENHEIT: {
                return convertCelsiusToFahrenheit(temperature);
            }

            case FAHRENHEIT_TO_CELSIUS: {
                return convertFahrenheitToCelsius(temperature);
            }

            case  FAHRENHEIT_TO_KELVIN: {
                return convertCelsiusToKelvin(temperature);
            }

            case KELVIN_TO_CELSIUS: {
                return convertKelvinToCelsius(temperature);
            }

            case KELVIN_TO_FAHRENHEIT: {
                return convertKelvinToFahrenheit(temperature);
            }

            default: {
                throw new IllegalArgumentException("Неверная шкала.");
            }
        }
    }

    public static String[] getTemperatureScales() {
        return TEMPERATURE_SCALES;
    }
}
