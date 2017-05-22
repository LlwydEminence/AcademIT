package ru.academits.streltsov.minesweeper.model;

import ru.academits.streltsov.minesweeper.model.exceptions.GameOverException;
import ru.academits.streltsov.minesweeper.model.exceptions.VictoryException;

import javax.naming.OperationNotSupportedException;
import java.io.*;
import java.util.*;

public class Minesweeper {
    static final String BEGINNER = "Новичок";
    static final String AMATEUR = "Любитель";
    static final String EXPERT = "Эксперт";
    public static final String USER = "Пользовательский";
    public static final String[] LEVELS = new String[]{BEGINNER, AMATEUR, EXPERT, USER};

    private static final int ROWS_NUMBER_FOR_BEGINNER = 9;
    private static final int COLUMNS_NUMBER_FOR_BEGINNER = 9;
    private static final int MINES_NUMBER_FOR_BEGINNER = 10;

    private static final int ROWS_NUMBER_FOR_AMATEUR = 16;
    private static final int COLUMNS_NUMBER_FOR_AMATEUR = 16;
    private static final int MINES_NUMBER_FOR_AMATEUR = 40;

    private static final int ROWS_NUMBER_FOR_EXPERT = 30;
    private static final int COLUMNS_NUMBER_FOR_EXPERT = 16;
    private static final int MINES_NUMBER_FOR_EXPERT = 99;

    private static final int MIN_ROWS_NUMBER = 9;
    private static final int MAX_ROWS_NUMBER = 30;
    private static final int MIN_COLUMNS_NUMBER = 9;
    private static final int MAX_COLUMNS_NUMBER = 24;
    private static final int MIN_MINES_NUMBER = 10;

    private static final String INCREASE_OPERATION = "+";
    private static final String DECREASE_OPERATION = "-";
    private static final Random r = new Random(System.currentTimeMillis());

    private int rowsNumber;
    private int columnsNumber;
    private int minesNumber;
    private int openCellsNumber;
    private int cellsWithoutMineNumber;
    private int marksNumber;
    private Cell[][] cells;
    private HighScores highScores;

    public Minesweeper() {
    }

    public void initField(String level) {
        switch (level) {
            case BEGINNER: {
                initParameters(ROWS_NUMBER_FOR_BEGINNER, COLUMNS_NUMBER_FOR_BEGINNER, MINES_NUMBER_FOR_BEGINNER);
                fillCells();
                break;
            }

            case AMATEUR: {
                initParameters(ROWS_NUMBER_FOR_AMATEUR, COLUMNS_NUMBER_FOR_AMATEUR, MINES_NUMBER_FOR_AMATEUR);
                fillCells();
                break;
            }

            case EXPERT: {
                initParameters(ROWS_NUMBER_FOR_EXPERT, COLUMNS_NUMBER_FOR_EXPERT, MINES_NUMBER_FOR_EXPERT);
                fillCells();
                break;
            }
        }

        highScores = new HighScores(level);
    }

    public int getMinesNumber() {
        return minesNumber;
    }

    public void initRowsNumber(int rowsNumber) {
        if (rowsNumber < MIN_ROWS_NUMBER || rowsNumber > MAX_ROWS_NUMBER) {
            throw new IllegalArgumentException("Число строк должно быть не менее " + MIN_ROWS_NUMBER +
                    " и не более " + MAX_ROWS_NUMBER);
        }

        this.rowsNumber = rowsNumber;
    }

    public void initColumnsNumber(int columnsNumber) {
        if (columnsNumber < MIN_COLUMNS_NUMBER || columnsNumber > MAX_COLUMNS_NUMBER) {
            throw new IllegalArgumentException("Число столбцов должно быть не менее " + MIN_COLUMNS_NUMBER +
                    "и не более " + MAX_COLUMNS_NUMBER);
        }

        this.columnsNumber = columnsNumber;
    }

    public void initMinesNumber(int minesNumber) {
        if (minesNumber < MIN_MINES_NUMBER) {
            throw new IllegalArgumentException("Минимальное число мин - " + MIN_MINES_NUMBER);
        }

        int maxMinesNumber = (rowsNumber - 1) * (columnsNumber - 1);
        if (minesNumber > maxMinesNumber) {
            throw new IllegalArgumentException("Максимальное число мин при таких размерах - " + maxMinesNumber);
        }
    }

    public void initField() {
        initParameters();
        fillCells();
    }

    private void initParameters(int rowsNumber, int columnsNumber, int minesNumber) {
        this.rowsNumber = rowsNumber;
        this.columnsNumber = columnsNumber;
        this.minesNumber = minesNumber;
        cellsWithoutMineNumber = rowsNumber * columnsNumber - minesNumber;
        openCellsNumber = 0;
        cells = new Cell[rowsNumber][columnsNumber];
        for (int i = 0; i < rowsNumber; ++i) {
            for (int j = 0; j < columnsNumber; ++j) {
                cells[i][j] = new Cell();
            }
        }
    }

    private void initParameters() {
        cellsWithoutMineNumber = rowsNumber * columnsNumber - minesNumber;
        openCellsNumber = 0;
        cells = new Cell[rowsNumber][columnsNumber];
        for (int i = 0; i < rowsNumber; ++i) {
            for (int j = 0; j < columnsNumber; ++j) {
                cells[i][j] = new Cell();
            }
        }
    }

    private void putMines() {
        for (int i = 1; i <= minesNumber; ++i) {
            int row;
            int column;
            do {
                row = r.nextInt(rowsNumber);
                column = r.nextInt(columnsNumber);
            } while (cells[row][column].isMine());

            cells[row][column].putMine();
        }
    }

    private boolean isExist(int i, int j) {
        return i >= 0 && i < rowsNumber && j >= 0 && j < columnsNumber;
    }

    private void changeCellValue(int i, int j, String operation) {
        if (isExist(i, j)) {
            if (!cells[i][j].isMine()) {
                if (operation.equals(INCREASE_OPERATION)) {
                    cells[i][j].increaseValue();
                } else {
                    cells[i][j].decreaseValue();
                }
            }
        }
    }

    private void modifyNeighbors(int row, int column, String operation) {
        for (int i = row - 1; i <= row + 1; ++i) {
            for (int j = column - 1; j <= column + 1; ++j) {
                if (i == row && j == column) {
                    continue;
                }
                changeCellValue(i, j, operation);
            }
        }
    }

    private void fillCells() {
        putMines();
        for (int i = 0; i < rowsNumber; ++i) {
            for (int j = 0; j < columnsNumber; ++j) {
                if (cells[i][j].isMine()) {
                    modifyNeighbors(i, j, INCREASE_OPERATION);
                }
            }
        }
    }

    public Cell[][] getCells() {
        return Arrays.copyOf(cells, cells.length);
    }

    private class Position {
        private int row;
        private int column;

        private Position(int row, int column) {
            this.row = row;
            this.column = column;
        }

        private int getRow() {
            return row;
        }

        private int getColumn() {
            return column;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            Position other = (Position) obj;

            return row == other.getRow() && column == other.getColumn();
        }
    }

    private void openNeighbors(int row, int column) {
        Deque<Position> stack = new LinkedList<>();

        do {
            for (int i = row - 1; i <= row + 1; ++i) {
                for (int j = column - 1; j <= column + 1; ++j) {
                    if (isExist(i, j) && !cells[i][j].isOpened() && !cells[i][j].isMarked()
                            && !cells[i][j].isMine() && !cells[i][j].isQuestioned() && !stack.contains(new Position(i, j))) {
                        stack.addLast(new Position(i, j));
                    }
                }
            }

            if (stack.isEmpty()) {
                return;
            }

            Position currentPosition = stack.removeLast();
            row = currentPosition.getRow();
            column = currentPosition.getColumn();
            cells[row][column].open();
            ++openCellsNumber;

            while (true) {
                if (!cells[row][column].isNoMineNear()) {
                    if (stack.isEmpty()) {
                        return;
                    }

                    currentPosition = stack.removeLast();
                    row = currentPosition.getRow();
                    column = currentPosition.getColumn();
                    cells[row][column].open();
                    ++openCellsNumber;
                } else {
                    break;
                }
            }

        } while (true);
    }

    private void checkCoordinates(int row, int column) {
        if (!isExist(row, column)) {
            throw new IllegalArgumentException("Неверно введены координаты.");
        }
    }

    public void openFirstCell(int row, int column) {
        --row;
        --column;
        checkCoordinates(row, column);

        if (!cells[row][column].isMine()) {
            cells[row][column].open();
            ++openCellsNumber;
            if (cells[row][column].isNoMineNear()) {
                openNeighbors(row, column);
            }
        } else {
            int aRow;
            int aColumn;
            do {
                aRow = r.nextInt(rowsNumber);
                aColumn = r.nextInt(columnsNumber);
            } while (cells[aRow][aColumn].isMine());

            cells[aRow][aColumn].putMine();
            modifyNeighbors(aRow, aColumn, INCREASE_OPERATION);
            modifyNeighbors(row, column, DECREASE_OPERATION);
            cells[row][column].increaseValue();

            for (int i = row - 1; i <= row + 1; ++i) {
                for (int j = column - 1; j <= column + 1; ++j) {
                    if (i == row && j == column) {
                        continue;
                    }
                    if (isExist(i, j) && cells[i][j].isMine()) {
                        cells[row][column].increaseValue();
                    }
                }
            }
            cells[row][column].open();
            ++openCellsNumber;
            if (cells[row][column].isNoMineNear()) {
                openNeighbors(row, column);
            }
        }
    }

    public void openCell(int row, int column) throws GameOverException, OperationNotSupportedException, VictoryException {
        --row;
        --column;
        checkCoordinates(row, column);

        if (cells[row][column].isOpened()) {
            throw new OperationNotSupportedException("Ячейка уже открыта.");
        }

        if (cells[row][column].isMarked()) {
            throw new OperationNotSupportedException("Нельзя открыть помеченную ячейку.");
        }

        cells[row][column].open();
        ++openCellsNumber;

        if (cells[row][column].isMine()) {
            throw new GameOverException("Вы проиграли!");
        }

        if (cells[row][column].isNoMineNear()) {
            openNeighbors(row, column);
        }

        if (openCellsNumber >= cellsWithoutMineNumber) {
            throw new VictoryException("Победа!");
        }
    }

    public void setMark(int row, int column) throws OperationNotSupportedException {
        --row;
        --column;
        checkCoordinates(row, column);

        if (marksNumber >= minesNumber) {
            throw new OperationNotSupportedException("Пометок больше не осталось");
        }

        if (cells[row][column].isOpened()) {
            throw new OperationNotSupportedException("Нельзя пометить открытую ячейку.");
        }

        if (cells[row][column].isMarked()) {
            throw new OperationNotSupportedException("Ячейка уже помечена.");
        }

        cells[row][column].mark();
        ++marksNumber;
    }

    public void deleteMark(int row, int column) throws OperationNotSupportedException {
        --row;
        --column;
        checkCoordinates(row, column);

        if (cells[row][column].isOpened()) {
            throw new OperationNotSupportedException("На открытой ячейке не может быть метки.");
        }

        if (!cells[row][column].isMarked()) {
            throw new OperationNotSupportedException("Ячейка не была помечена.");
        }

        cells[row][column].deleteMark();
        --marksNumber;
    }

    public void setQuestion(int row, int column) throws OperationNotSupportedException {
        --row;
        --column;
        checkCoordinates(row, column);

        if (cells[row][column].isOpened()) {
            throw new OperationNotSupportedException("Ячейка уже открыта.");
        }

        if (cells[row][column].isQuestioned()) {
            throw new OperationNotSupportedException("Ячейка уже под вопросом.");
        }

        cells[row][column].question();
    }

    public void deleteQuestion(int row, int column) throws OperationNotSupportedException {
        --row;
        --column;
        checkCoordinates(row, column);

        if (cells[row][column].isOpened()) {
            throw new OperationNotSupportedException("Открытая ячейка не может быть под вопросом.");
        }

        if (!cells[row][column].isQuestioned()) {
            throw new OperationNotSupportedException("Ячейка не была под вопросом.");
        }

        cells[row][column].deleteQuestion();
    }

    public void addWinner(String name, long time, ArrayList<Winner> winners) throws FileNotFoundException {
        highScores.add(name, time, winners);
    }

    public ArrayList<Winner> getWinners() throws FileNotFoundException {
        return highScores.getData();
    }

    public void fastOpenNeighbors(int row, int column) throws OperationNotSupportedException, VictoryException, GameOverException {
        --row;
        --column;
        checkCoordinates(row, column);

        if (!cells[row][column].isOpened()) {
            throw new OperationNotSupportedException("Операция доступна только для открытой ячейки.");
        }

        int markedCellsNumber = 0;
        for (int i = row - 1; i <= row + 1; ++i) {
            for (int j = column - 1; j <= column + 1; ++j) {
                if (i == row && j == column) {
                    continue;
                }
                if (isExist(i, j) && cells[i][j].isMarked()) {
                    ++markedCellsNumber;
                }
            }
        }

        if (markedCellsNumber < cells[row][column].getValue()) {
            throw new IllegalArgumentException("Не хватает пометок рядом с ячейкой.");
        }

        for (int i = row - 1; i <= row + 1; ++i) {
            for (int j = column - 1; j <= column + 1; ++j) {
                if (i == row && j == column) {
                    continue;
                }
                if (isExist(i, j) && !cells[i][j].isMarked() && !cells[i][j].isQuestioned() && !cells[i][j].isOpened()) {
                    cells[i][j].open();
                    ++openCellsNumber;

                    if (openCellsNumber == cellsWithoutMineNumber) {
                        throw new VictoryException("Победа!");
                    }

                    if (cells[i][j].isMine()) {
                        throw new GameOverException("Вы проиграли");
                    }
                }
            }
        }
    }
}
