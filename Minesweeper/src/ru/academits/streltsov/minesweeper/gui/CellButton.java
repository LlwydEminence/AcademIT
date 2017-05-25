package ru.academits.streltsov.minesweeper.gui;

import javax.swing.*;

class CellButton extends JButton {
    private int row;
    private int column;

    CellButton(int row, int column) {
        this.row = row + 1;
        this.column = column + 1;
    }

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }
}
