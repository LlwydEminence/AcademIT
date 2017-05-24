package ru.academits.streltsov.minesweeper.common;

import ru.academits.streltsov.minesweeper.conroller.Controller;
import ru.academits.streltsov.minesweeper.model.Cell;

import java.io.FileNotFoundException;

public interface View {
    void startApplication() throws FileNotFoundException;
    void addController(Controller controller);
    void printField(Cell[][] cells);
    void printOpenedField(Cell[][] cells);
    void onVictory() throws FileNotFoundException;
    void onGameOver();
}
