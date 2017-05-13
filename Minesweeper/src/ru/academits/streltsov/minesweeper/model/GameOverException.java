package ru.academits.streltsov.minesweeper.model;

public class GameOverException extends Exception {
    GameOverException(String message) {
        super(message);
    }
}
