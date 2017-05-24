package ru.academits.streltsov.minesweeper;

import ru.academits.streltsov.minesweeper.common.View;
import ru.academits.streltsov.minesweeper.conroller.Controller;
import ru.academits.streltsov.minesweeper.model.Minesweeper;
import ru.academits.streltsov.minesweeper.textui.ConsoleView;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        View view = new ConsoleView();
        Minesweeper minesweeper = new Minesweeper();
        Controller controller = new Controller(minesweeper, view);
        view.addController(controller);
        view.startApplication();
    }
}
