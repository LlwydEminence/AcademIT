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

    public void needInitField(String level) {
        minesweeper.initField(level);
    }

    public void needInitRowsNumber(int rowsNumber) {
        minesweeper.initRowsNumber(rowsNumber);
    }

    public void needInitColumnsNumber(int columnsNumber) {
        minesweeper.initColumnsNumber(columnsNumber);
    }

    public void needInitMinesNumber(int minesNumber) {
        minesweeper.initMinesNumber(minesNumber);
    }

    public void needInitField() {
        minesweeper.initField();
    }

    public void needPrintField() {
        view.printField(minesweeper.getCells());
    }

    public void needPrintOpenedField() {
        view.printOpenedField(minesweeper.getCells());
    }

    public void needOpenCell(int row, int column) throws OperationNotSupportedException {
        minesweeper.openCell(row, column);
    }

    public void needSetMark(int row, int column) throws OperationNotSupportedException {
        minesweeper.setMark(row, column);
    }

    public void needDeleteMark(int row, int column) throws OperationNotSupportedException {
        minesweeper.deleteMark(row, column);
    }

    public void needSetQuestion(int row, int column) throws OperationNotSupportedException {
        minesweeper.setQuestion(row, column);
    }

    public void needDeleteQuestion(int row, int column) throws OperationNotSupportedException {
        minesweeper.deleteQuestion(row, column);
    }

    public void needAddWinner(String name, long time, ArrayList<Winner> winners) throws FileNotFoundException {
        minesweeper.addWinner(name, time, winners);
    }

    public void needFastOpen(int row, int column) throws OperationNotSupportedException {
        minesweeper.fastOpenNeighbors(row, column);
    }

    public ArrayList<Winner> needHighScores() throws FileNotFoundException {
        return minesweeper.getWinners();
    }

    public int needMinesNumber() {
        return minesweeper.getMinesNumber();
    }

    public int needRowsNumber() {
        return minesweeper.getRowsNumber();
    }

    public int needColumnsNumber() {
        return minesweeper.getColumnsNumber();
    }

    public ArrayList<Winner> needHighScores(String level) throws FileNotFoundException {
        return new HighScores(level).getData();
    }

    public boolean needCheckVictory() throws FileNotFoundException {
        if (minesweeper.isVictory()) {
            view.onVictory();
            return true;
        } else {
            return false;
        }
    }

    public boolean needCheckGameOver() {
        if (minesweeper.isGameOver()) {
            view.onGameOver();
            return true;
        } else {
            return false;
        }
    }

    public long needFinishTime() {
        return minesweeper.getFinishTime();
    }

    public String needLevel() {
        return minesweeper.getLevel();
    }
}
