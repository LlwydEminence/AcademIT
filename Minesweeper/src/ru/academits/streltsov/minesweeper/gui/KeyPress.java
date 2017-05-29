package ru.academits.streltsov.minesweeper.gui;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

class KeyPress {
    static boolean isLeftMouseButtonPressed(MouseEvent e) {
        return (e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0;
    }

    static boolean isMiddleMouseButtonPressed(MouseEvent e) {
        return (e.getModifiersEx() & InputEvent.BUTTON2_DOWN_MASK) != 0;
    }

    static boolean isRightMouseButtonPressed(MouseEvent e) {
        return (e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0;
    }

    static boolean isRightAndLeftMouseButtonsPressed(MouseEvent e) {
        return ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) &&
        ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0);
    }
}
