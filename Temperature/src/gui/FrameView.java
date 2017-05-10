package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameView {
    private final JFrame frame = new JFrame("Конвертер температур");
    private final JComboBox<String> converter = new JComboBox<>(new String[]{DEFAULT_CONVERTER_NAME, "Цельсий в Фаренгейт",
            "Кельвин в Цельсий", "Кельвин в Фаренгейт", "Фаренгейт в Цельсий", "Фаренгейт в Кельвин"});
    private final JTextField result = new JTextField(20);
    private final JButton convertButton = new JButton("Перевести");
    private final JTextField temperature = new JTextField(5);

    private static final String DEFAULT_CONVERTER_NAME = "Цельсий в Кельвин";

    private String converterName = DEFAULT_CONVERTER_NAME;
    private Controller controller;

    public void startTemperatureConverter() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initContent();
                initFrame();
                initEvents();
            }
        });
    }

    private void initFrame() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void initContent() {
        JPanel contentPanel = new JPanel(new GridBagLayout());

        JLabel temperatureEnterLabel = new JLabel("Введите температуру: ");
        contentPanel.add(temperatureEnterLabel, new GBC(0, 0, 3,1));

        contentPanel.add(temperature, new GBC(3, 0));
        contentPanel.add(convertButton, new GBC(4, 0).setInsets(5));

        result.setEditable(false);
        contentPanel.add(result, new GBC(5,0, 2, 1));

        JLabel converterChoiceLabel = new JLabel("Выберите конвертер: ");
        contentPanel.add(converterChoiceLabel, new GBC(0, 1, 3, 1));

        contentPanel.add(converter, new GBC(3, 1, 3, 1));
        frame.setContentPane(contentPanel);
        frame.pack();
    }

    private void initEvents() {
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double temperatureValue = Double.parseDouble(temperature.getText());
                    switch (converterName) {
                        case DEFAULT_CONVERTER_NAME: {
                            controller.needConvertCelsiusToKelvin(temperatureValue);
                            break;
                        }

                        case "Цельсий в Фаренгейт": {
                            controller.needConvertCelsiusToFahrenheit(temperatureValue);
                            break;
                        }

                        case "Кельвин в Цельсий": {
                            controller.needConvertKelvinToCelsius(temperatureValue);
                            break;
                        }

                        case "Кельвин в Фаренгейт": {
                            controller.needConvertKelvinToFahrenheit(temperatureValue);
                            break;
                        }

                        case "Фаренгейт в Цельсий": {
                            controller.needConvertFahrenheitToCelsius(temperatureValue);
                            break;
                        }

                        case "Фаренгейт в Кельвин": {
                            controller.needConvertFahrenheitToKelvin(temperatureValue);
                            break;
                        }
                    }
                } catch (NumberFormatException ex) {
                    result.setText("Температура должна быть числом");
                }
            }
        });
        converter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                converterName = (String) converter.getSelectedItem();
            }
        });
    }

    public void printConvertedTemperature(double temperature) {
       result.setText(Double.toString(temperature));
    }

    public void addController(Controller controller) {
        this.controller = controller;
    }
}
