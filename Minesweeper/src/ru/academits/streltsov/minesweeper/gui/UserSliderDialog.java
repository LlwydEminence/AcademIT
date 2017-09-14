package ru.academits.streltsov.minesweeper.gui;

import ru.academits.streltsov.minesweeper.controller.Controller;
import ru.academits.streltsov.minesweeper.model.Minesweeper;

import javax.swing.*;
import java.awt.*;

class UserSliderDialog extends JDialog {
    private JPanel sliderPanel;
    private JLabel widthLabel = new JLabel();
    private JLabel heightLabel = new JLabel();
    private JLabel minesNumberLabel = new JLabel();
    private JSlider minesNumberSlider = new JSlider();
    private Controller controller;
    private Dimension preferredSizeForLabel =
            new JLabel(Integer.toString(100)).getPreferredSize();

    private int rowsNumber;
    private int columnsNumber;
    private int minesNumber;
    private boolean isFieldCreated;

    UserSliderDialog(GameView gameView) {
        super(gameView, "Настройка пользовательских параметров", true);
        controller = gameView.getController();
        controller.stopTimer();

        sliderPanel = new JPanel(new GridBagLayout());
        addWidthSlider();
        addHeightSlider();
        addMinesSlider();
        JButton button = new JButton("Создать поле");
        button.addActionListener(e -> {
            setVisible(false);
            controller.clearAll();
            controller.initField(rowsNumber, columnsNumber, minesNumber);
            isFieldCreated = true;
        });
        add(sliderPanel, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
        setLocationRelativeTo(gameView);
        pack();
    }

    private void addWidthSlider() {
        JSlider slider =
                new JSlider(Minesweeper.MIN_COLUMNS_NUMBER, Minesweeper.MAX_COLUMNS_NUMBER, Minesweeper.MIN_COLUMNS_NUMBER);

        widthLabel.setText(Integer.toString(Minesweeper.MIN_COLUMNS_NUMBER));
        widthLabel.setPreferredSize(preferredSizeForLabel);
        columnsNumber = Minesweeper.MIN_COLUMNS_NUMBER;

        slider.addChangeListener(e -> {
            columnsNumber = slider.getValue();
            widthLabel.setText(Integer.toString(columnsNumber));
            int maxMinesNumber = controller.getMaxMinesNumber(rowsNumber, columnsNumber);

            if (maxMinesNumber < minesNumberSlider.getValue()) {
                minesNumberSlider.setValue(maxMinesNumber);
                minesNumber = maxMinesNumber;
                minesNumberLabel.setText(Integer.toString(maxMinesNumber));
            }

            minesNumberSlider.setMaximum(maxMinesNumber);
        });


        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(new JLabel("Ширина:"), new GBC(0,0));
        panel.add(slider, new GBC(0,1));
        panel.add(widthLabel, new GBC(0, 2));
        sliderPanel.add(panel, new GBC(0, sliderPanel.getComponentCount()).setAnchor(GBC.WEST));
    }

    private void addHeightSlider() {
        JSlider slider =
                new JSlider(Minesweeper.MIN_ROWS_NUMBER, Minesweeper.MAX_ROWS_NUMBER, Minesweeper.MIN_ROWS_NUMBER);
        heightLabel.setText(Integer.toString(Minesweeper.MIN_ROWS_NUMBER));
        heightLabel.setPreferredSize(preferredSizeForLabel);
        rowsNumber = Minesweeper.MIN_ROWS_NUMBER;

        slider.addChangeListener(e -> {
            rowsNumber = slider.getValue();
            heightLabel.setText(Integer.toString(rowsNumber));
            int maxMinesNumber = controller.getMaxMinesNumber(rowsNumber, columnsNumber);

            if (maxMinesNumber < minesNumberSlider.getValue()) {
                minesNumberSlider.setValue(maxMinesNumber);
                minesNumber = maxMinesNumber;
                minesNumberLabel.setText(Integer.toString(maxMinesNumber));
            }

            minesNumberSlider.setMaximum(maxMinesNumber);
        });

        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(new JLabel("Высота:"), new GBC(1,0));
        panel.add(slider, new GBC(1,1));
        panel.add(heightLabel, new GBC(1,2));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sliderPanel.add(panel, new GBC(0, sliderPanel.getComponentCount()).setAnchor(GBC.WEST));
    }

    private void addMinesSlider() {
        minesNumberSlider.setMinimum(Minesweeper.MIN_MINES_NUMBER);
        minesNumberSlider.setMaximum(controller.getMaxMinesNumber(rowsNumber, columnsNumber));
        minesNumberSlider.setValue(Minesweeper.MIN_MINES_NUMBER);
        minesNumberLabel.setText(Integer.toString(Minesweeper.MIN_MINES_NUMBER));
        minesNumberLabel.setPreferredSize(preferredSizeForLabel);
        minesNumber = Minesweeper.MIN_MINES_NUMBER;

        minesNumberSlider.addChangeListener(e -> {
            minesNumber = minesNumberSlider.getValue();
            minesNumberLabel.setText(Integer.toString(minesNumber));
        });

        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(new JLabel("Мины:"), new GBC(2,0));
        panel.add(minesNumberSlider, new GBC(2,1));
        panel.add(minesNumberLabel, new GBC(2,2));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sliderPanel.add(panel, new GBC(0, sliderPanel.getComponentCount()).setAnchor(GBC.WEST));
    }

    boolean isFieldCreated() {
        return isFieldCreated;
    }
}
