package ru.academits.streltsov.minesweeper.gui;

import java.awt.*;

class GBC extends GridBagConstraints {
    GBC(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    GBC setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    GBC setFill(int fill) {
        this.fill = fill;
        return this;
    }

    GBC setWeight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    GBC setInsets(int distance) {
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }

    GBC setInsets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    GBC setIpad(int ipadx, int ipady) {
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }
}
