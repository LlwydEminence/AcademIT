package ru.academits.streltsov.minesweeper.gui;

import ru.academits.streltsov.minesweeper.common.View;
import ru.academits.streltsov.minesweeper.conroller.Controller;
import ru.academits.streltsov.minesweeper.model.Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class ChoiceFrame extends JFrame {
    private final Container contentPane;

    public ChoiceFrame() {
        contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(0,1));

        JLabel label = new JLabel(" Выберите режим:");
        contentPane.add(label);

        String[] levels = Minesweeper.LEVELS;
        for (String level : levels) {
            addButton(level);
        }
        pack();
    }

    private void addButton(String level) {
        JButton button = new JButton(level);
        contentPane.add(button);

        if (!level.equals(Minesweeper.USER)) {
            button.addActionListener(e -> {
                Minesweeper minesweeper = new Minesweeper();
                minesweeper.initField(level);
                setVisible(false);

                View gameView = new GameView(this);
                Controller controller = new Controller(minesweeper, gameView);
                gameView.addController(controller);
                try {
                    gameView.startApplication();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            });
        } else {
            button.addActionListener(e -> {
                setVisible(false);
                new UserSliderFrame(this);
            });
        }
    }
}
