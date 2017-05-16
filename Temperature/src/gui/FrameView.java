package gui;

import common.TemperatureConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameView {
    private final JFrame frame;
    private final TemperatureConverter[] temperatureConverters;
    private final JComboBox<String> fromScale = new JComboBox<>();
    private JComboBox<String> toScale = new JComboBox<>();
    private final JTextField result;
    private final JButton convertButton;
    private final JTextField temperature;

    public FrameView(TemperatureConverter[] temperatureConverters) {
        frame = new JFrame("Конвертер температур");
        this.temperatureConverters = temperatureConverters;

        for (TemperatureConverter tc : temperatureConverters) {
            fromScale.addItem(tc.getName());
        }

        fromScale.setSelectedIndex(0);
        fillToScale();
        result = new JTextField(13);
        convertButton = new JButton("Перевести");
        temperature = new JTextField(12);
    }

    private void fillToScale() {
        String fromScaleName = fromScale.getItemAt(fromScale.getSelectedIndex());

        for (TemperatureConverter tc : temperatureConverters) {
            String tcName = tc.getName();
            if (!tcName.equals(fromScaleName)) {
                toScale.addItem(tcName);
            }
        }

        toScale.setSelectedIndex(0);
    }

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

        JLabel fromScaleLabel = new JLabel("Исходная: ");
        contentPanel.add(fromScaleLabel, new GBC(0, 0).setAnchor(GridBagConstraints.WEST));
        contentPanel.add(fromScale, new GBC(1, 0));

        JLabel toScaleLabel = new JLabel("Результирующая: ");
        contentPanel.add(toScaleLabel, new GBC(0, 1).setAnchor(GridBagConstraints.WEST));
        contentPanel.add(toScale, new GBC(1, 1));

        JLabel temperatureEnterLabel = new JLabel("Введите температуру: ");
        contentPanel.add(temperatureEnterLabel, new GBC(0, 2));

        contentPanel.add(temperature, new GBC(1, 2).setAnchor(GridBagConstraints.WEST));
        contentPanel.add(convertButton, new GBC(2, 2));

        result.setEditable(false);
        contentPanel.add(result, new GBC(3,2).setInsets(0, 1, 0, 0));

        frame.setContentPane(contentPanel);
        frame.pack();
    }

    private void initEvents() {
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double temperatureValue = Double.parseDouble(temperature.getText());
                    double celsiusValue =
                            temperatureConverters[fromScale.getSelectedIndex()].convertTemperatureToCelsius(temperatureValue);
                    for (TemperatureConverter tc : temperatureConverters) {
                        if (tc.getName().equals(toScale.getItemAt(toScale.getSelectedIndex()))) {
                            printConvertedTemperature(tc.convertCelsiusToTemperature(celsiusValue));
                            return;
                        }
                    }
                } catch (NumberFormatException ex) {
                    result.setText("Температура должна быть числом");
                }
            }
        });

        fromScale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result.setText(null);
                toScale.removeAllItems();
                fillToScale();
                toScale.setPreferredSize(fromScale.getPreferredSize());
            }
        });

        toScale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result.setText(null);
            }
        });
    }

    private void printConvertedTemperature(double temperature) {
       result.setText(Double.toString(temperature));
    }
}
