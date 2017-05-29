package ru.academits.streltsov.minesweeper;

import ru.academits.streltsov.minesweeper.common.View;
import ru.academits.streltsov.minesweeper.conroller.Controller;
import ru.academits.streltsov.minesweeper.gui.GameView;
import ru.academits.streltsov.minesweeper.model.Minesweeper;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            View view = new GameView();
            Minesweeper minesweeper = new Minesweeper();
            Controller controller = new Controller(minesweeper, view);
            view.addController(controller);
            try {
                view.startApplication();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
