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
    private long timeBeforeStop;

    public void initField(String level) {
        minesweeper.initField(level);
    }

    public void clearAll() {
        stopTimer();
        minesweeper.clearAll();
    }

    public void initField(int rowsNumber, int columnsNumber, int minesNumber) {
        minesweeper.initField(rowsNumber, columnsNumber, minesNumber);
    }

    public void printField() {
        view.printField(minesweeper.getCells());
    }

    public void printOpenedField() {
        view.printOpenedField(minesweeper.getCells());
    }

    private boolean checkGameOverAndVictory() throws FileNotFoundException {
        if (minesweeper.isGameOver()) {
            stopTimer();
            view.printOpenedField(minesweeper.getCells());
            view.onGameOver();
            return true;
        }

        if (minesweeper.isVictory()) {
            stopTimer();
            long time = minesweeper.getTime();
            view.printOpenedField(minesweeper.getCells());
            view.onVictory(time);

            if (!getLevel().equals(Minesweeper.USER)) {
                ArrayList<Winner> winners = getHighScores();
                if (winners.isEmpty() || winners.get(winners.size() - 1).getTime() >= time
                        || winners.size() < HighScores.HIGH_SCORES_POSITION_NUMBERS) {
                    addWinner(view.getWinnerName(), time, winners);
                    view.showHighScores(minesweeper.getLevel());
                }
            }

            return true;
        }

        return false;
    }

    public boolean openCell(int row, int column) throws OperationNotSupportedException, FileNotFoundException {
        if (!minesweeper.isFirstCellOpened()) {
            startTimer();
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

    public int getMaxMinesNumber(int rowsNumber, int columnsNumber) {
       return minesweeper.getMaxMinesNumber(rowsNumber, columnsNumber);
    }

    public void stopTimer() {
        timeBeforeStop = minesweeper.getTime() / 1000 * 1000;
        timer.stop();
    }

    public void startTimer() {
        timer.start();
    }

    public void renewStartTime() {
        minesweeper.renewStartTime(timeBeforeStop);
    }
}
