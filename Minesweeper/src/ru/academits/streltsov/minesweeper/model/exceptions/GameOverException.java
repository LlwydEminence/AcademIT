package ru.academits.streltsov.minesweeper.model.exceptions;

public class GameOverException extends Exception {
    public GameOverException(String message) {
        super(message);
    }
}
