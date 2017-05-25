package ru.academits.streltsov.minesweeper;

import ru.academits.streltsov.minesweeper.gui.ChoiceFrame;

import javax.swing.*;

public class Application {
    private static void createAndShowGUI() {
        JFrame frame = new ChoiceFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::createAndShowGUI);
    }
}
