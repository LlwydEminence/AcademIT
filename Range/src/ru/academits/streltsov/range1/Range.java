package ru.academits.streltsov.range1;

public class Range {
    private double from;
    private double to;
    private static final double EPSILON = 0.01;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double calculateIntervalLength() {
        return to - from + 1;
    }

    public boolean isInside(double point) {
        return point >= from - EPSILON && point <= to + EPSILON;
    }
}
