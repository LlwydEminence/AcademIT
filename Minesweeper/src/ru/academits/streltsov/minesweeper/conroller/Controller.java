package ru.academits.streltsov.minesweeper.conroller;

import ru.academits.streltsov.minesweeper.common.View;
import ru.academits.streltsov.minesweeper.model.HighScores;
import ru.academits.streltsov.minesweeper.model.Minesweeper;
import ru.academits.streltsov.minesweeper.model.Winner;

import javax.naming.OperationNotSupportedException;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
    private Minesweeper minesweeper;
    private View view;

    public Controller(Minesweeper minesweeper, View view) {
        this.minesweeper = minesweeper;
        this.view = view;
    }

    private Timer timer = new Timer(1000, e -> updateTimer());

    public void initField(String level) {
        minesweeper.initField(level);
    }

    public void initRowsNumber(int rowsNumber) {
        minesweeper.initRowsNumber(rowsNumber);
    }

    public void initColumnsNumber(int columnsNumber) {
        minesweeper.initColumnsNumber(columnsNumber);
    }

    public void initMinesNumber(int minesNumber) {
        minesweeper.initMinesNumber(minesNumber);
    }

    public void initField() {
        minesweeper.initField();
    }

    public void printField() {
        view.printField(minesweeper.getCells());
    }

    public void printOpenedField() {
        view.printOpenedField(minesweeper.getCells());
    }

    private boolean checkGameOverAndVictory() throws FileNotFoundException {
        if (minesweeper.isGameOver()) {
            timer.stop();
            view.printOpenedField(minesweeper.getCells());
            view.onGameOver();
            return true;
        }

        if (minesweeper.isVictory()) {
            timer.stop();
            long time = minesweeper.getTime();
            view.printOpenedField(minesweeper.getCells());
            view.onVictory(time);

            if (!getLevel().equals(Minesweeper.USER)) {
                ArrayList<Winner> winners = getHighScores();
                if (winners.isEmpty() || winners.get(winners.size() - 1).getTime() >= time
                        || winners.size() < HighScores.HIGH_SCORES_POSITION_NUMBERS) {
                    addWinner(view.getWinnerName(), time, winners);
                }
            }

            return true;
        }

        return false;
    }

    public boolean openCell(int row, int column) throws OperationNotSupportedException, FileNotFoundException {
        if (!minesweeper.isFirstCellOpened()) {
            timer.start();
        }

        minesweeper.openCell(row, column);
        return checkGameOverAndVictory();
    }

    public void setMark(int row, int column) throws OperationNotSupportedException {
        minesweeper.setMark(row, column);
    }

    public void deleteMark(int row, int column) throws OperationNotSupportedException {
        minesweeper.deleteMark(row, column);
    }

    public void setQuestion(int row, int column) throws OperationNotSupportedException {
        minesweeper.setQuestion(row, column);
    }

    public void deleteQuestion(int row, int column) throws OperationNotSupportedException {
        minesweeper.deleteQuestion(row, column);
    }

    private void addWinner(String name, long time, ArrayList<Winner> winners) throws FileNotFoundException {
        minesweeper.addWinner(name, time, winners);
    }

    public boolean fastOpen(int row, int column) throws OperationNotSupportedException, FileNotFoundException {
        minesweeper.fastOpenNeighbors(row, column);
        return checkGameOverAndVictory();
    }

    private ArrayList<Winner> getHighScores() throws FileNotFoundException {
        return minesweeper.getWinners();
    }

    public int getMinesNumber() {
        return minesweeper.getMinesNumber();
    }

    public ArrayList<Winner> getHighScores(String level) throws FileNotFoundException {
        return new HighScores(level).getData();
    }

    private String getLevel() {
        return minesweeper.getLevel();
    }

    private void updateTimer() {
        long time = minesweeper.getTime();
        view.updateTimer(time);
    }

    public int getMaxMinesNumber() {
       return minesweeper.getMaxMinesNumber();
    }
}
