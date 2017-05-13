package ru.academits.streltsov.minesweeper.model;

import javax.naming.OperationNotSupportedException;
import java.io.*;
import java.util.*;

public class Minesweeper {
    private static final String BEGINNER = "Новичок";
    private static final String AMATEUR = "Любитель";
    private static final String EXPERT = "Эксперт";
    private static final String[] LEVELS = new String[]{BEGINNER, AMATEUR, EXPERT};

    private static final int ROWS_NUMBER_FOR_BEGINNER = 9;
    private static final int COLUMNS_NUMBER_FOR_BEGINNER = 9;
    private static final int MINES_NUMBER_FOR_BEGINNER = 10;

    private static final int ROWS_NUMBER_FOR_AMATEUR = 16;
    private static final int COLUMNS_NUMBER_FOR_AMATEUR = 16;
    private static final int MINES_NUMBER_FOR_AMATEUR = 40;

    private static final int ROWS_NUMBER_FOR_EXPERT = 30;
    private static final int COLUMNS_NUMBER_FOR_EXPERT = 16;
    private static final int MINES_NUMBER_FOR_EXPERT = 99;

    private static final int MINE = -1;
    private static final int WITHOUT_MINE_NEIGHBOURS_CELL_VALUE = 0;
    private static final String INCREASE_OPERATION = "+";
    private static final String DECREASE_OPERATION = "-";
    private static final Random r = new Random(System.currentTimeMillis());
    private static final int HIGH_SCORES_POSITION_NUMBERS = 10;

    private int rowsNumber;
    private int columnsNumber;
    private int minesNumber;
    private int openCellsNumber;
    private int cellsWithoutMineNumber;
    private int marksNumber;
    private Cell[][] cells;

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
    }

    public String[] getLevels() {
        return LEVELS;
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
                cells[i][j] = new Cell(0);
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
            } while (cells[row][column].getValue() == MINE);

            cells[row][column].setValue(MINE);
        }
    }

    private boolean isExist(int i, int j) {
        return i >= 0 && i < rowsNumber && j >= 0 && j < columnsNumber;
    }

    private boolean isMine(int cellValue) {
        return cellValue == MINE;
    }

    private void modify(int i, int j, String operation) {
        if (isExist(i, j)) {
            int cellValue = cells[i][j].getValue();

            if (!isMine(cellValue)) {
                if (operation.equals(INCREASE_OPERATION)) {
                    ++cellValue;
                } else {
                    --cellValue;
                }
                cells[i][j].setValue(cellValue);
            }
        }
    }

    private void modifyNeighbors(int row, int column, String operation) {
        for (int i = row - 1; i <= row + 1; ++i) {
            for (int j = column - 1; j <= column + 1; ++j) {
                if (i == row && j == column) {
                    continue;
                }
                modify(i, j, operation);
            }
        }
    }

    private void fillCells() {
        putMines();
        for (int i = 0; i < rowsNumber; ++i) {
            for (int j = 0; j < columnsNumber; ++j) {
                if (cells[i][j].getValue() == MINE) {
                    modifyNeighbors(i, j, INCREASE_OPERATION);
                }
            }
        }
    }

    public Cell[][] getCells() {
        return Arrays.copyOf(cells, cells.length);
    }

    private void openNeighbors(int row, int column) {
        class Position {
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

        Deque<Position> stack = new LinkedList<>();

        do {
            for (int i = row - 1; i <= row + 1; ++i) {
                for (int j = column - 1; j <= column + 1; ++j) {
                    if (isExist(i, j) && !cells[i][j].isOpened() && !cells[i][j].isMarked()
                            && cells[i][j].getValue() != MINE && !stack.contains(new Position(i, j))) {
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
                if (cells[row][column].getValue() != WITHOUT_MINE_NEIGHBOURS_CELL_VALUE) {
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

        int cellValue = cells[row][column].getValue();
        if (!isMine(cellValue)) {
            cells[row][column].open();
            ++openCellsNumber;
            if (cells[row][column].getValue() == WITHOUT_MINE_NEIGHBOURS_CELL_VALUE) {
                openNeighbors(row, column);
            }
        } else {
            int aRow;
            int aColumn;
            do {
                aRow = r.nextInt(rowsNumber);
                aColumn = r.nextInt(columnsNumber);
            } while (isMine(cells[aRow][aColumn].getValue()));

            cells[aRow][aColumn].setValue(MINE);
            modifyNeighbors(aRow, aColumn, INCREASE_OPERATION);
            modifyNeighbors(row, column, DECREASE_OPERATION);

            cellValue = 0;
            for (int i = row - 1; i <= row + 1; ++i) {
                for (int j = column - 1; j <= column + 1; ++j) {
                    if (i == row && j == column) {
                        continue;
                    }
                    if (isExist(i, j) && isMine(cells[i][j].getValue())) {
                        ++cellValue;
                    }
                }
            }
            cells[row][column].setValue(cellValue);
            cells[row][column].open();
            ++openCellsNumber;
            if (cells[row][column].getValue() == WITHOUT_MINE_NEIGHBOURS_CELL_VALUE) {
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

        if (isMine(cells[row][column].getValue())) {
            throw new GameOverException("Вы проиграли!");
        }

        if (cells[row][column].getValue() == WITHOUT_MINE_NEIGHBOURS_CELL_VALUE) {
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

        if (cells[row][column].isOpened()) {
            throw new OperationNotSupportedException("На открытой ячейке не может быть метки.");
        }

        if (!cells[row][column].isMarked()) {
            throw new OperationNotSupportedException("Ячейка не была помечена.");
        }

        cells[row][column].deleteMark();
        --marksNumber;
    }

    public void addWinner(String name, long time, ArrayList<Winner> winners) throws FileNotFoundException {
        if (winners.size() >= HIGH_SCORES_POSITION_NUMBERS) {
            winners.remove(winners.size() - 1);
        }

        winners.add(new Winner(name, time));
        winners.sort(new Comparator<Winner>() {
            @Override
            public int compare(Winner o1, Winner o2) {
                long o1Time = o1.getTime();
                long o2Time = o2.getTime();
                if (o1Time == o2Time) {
                    return 0;
                } else if (o1Time < o2Time) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        try (PrintWriter printWriter =
                     new PrintWriter(new OutputStreamWriter(new FileOutputStream("Minesweeper/src/ru/academits/streltsov/minesweeper/resources/HighScores.txt")))) {

            for (Winner winner : winners) {
                printWriter.println(winner);
            }
        }

    }

    public static ArrayList<Winner> getWinners() throws FileNotFoundException {
        ArrayList<Winner> winners = new ArrayList<>();
        try (Scanner scanner =
                     new Scanner(new FileInputStream("Minesweeper/src/ru/academits/streltsov/minesweeper/resources/HighScores.txt"))) {
            while (scanner.hasNext()) {
                winners.add(new Winner(scanner.next(), scanner.nextLong()));
            }
        }
        return winners;
    }
}
