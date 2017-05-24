package ru.academits.streltsov.minesweeper.conroller;

import ru.academits.streltsov.minesweeper.common.View;
import ru.academits.streltsov.minesweeper.model.HighScores;
import ru.academits.streltsov.minesweeper.model.Minesweeper;
import ru.academits.streltsov.minesweeper.model.Winner;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
    private Minesweeper minesweeper;
    private View view;

    public Controller(Minesweeper minesweeper, View view) {
        this.minesweeper = minesweeper;
        this.view = view;
    }

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

    public boolean openCell(int row, int column) throws OperationNotSupportedException, FileNotFoundException {
        minesweeper.openCell(row, column);
        if (minesweeper.isGameOver()) {
            view.printOpenedField(minesweeper.getCells());
            view.onGameOver();
            return true;
        }

        if (minesweeper.isVictory()) {
            long time = minesweeper.getFinishTime();
            view.printOpenedField(minesweeper.getCells());
            view.onVictory(time);
            return true;
        }

        return false;
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
        if (minesweeper.isGameOver()) {
            view.onGameOver();
            return true;
        }

        if (minesweeper.isVictory()) {
            long time = minesweeper.getFinishTime();
            view.onVictory(time);

            if (!getLevel().equals(Minesweeper.USER)) {
                ArrayList<Winner> winners = getHighScores();
                if (winners.isEmpty() || winners.get(winners.size() - 1).getTime() >= time) {
                    addWinner(view.getWinnerName(), time, winners);
                }
            }
            return true;
        }

        return false;
    }

    private ArrayList<Winner> getHighScores() throws FileNotFoundException {
        return minesweeper.getWinners();
    }

    public int getMinesNumber() {
        return minesweeper.getMinesNumber();
    }

    public int getRowsNumber() {
        return minesweeper.getRowsNumber();
    }

    public int getColumnsNumber() {
        return minesweeper.getColumnsNumber();
    }

    public ArrayList<Winner> getHighScores(String level) throws FileNotFoundException {
        return new HighScores(level).getData();
    }

    private String getLevel() {
        return minesweeper.getLevel();
    }
}
