package ru.academits.streltsov.range;

import ru.academits.streltsov.range.exceptions.InvalidIntervalException;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        if (from > to) {
            throw new InvalidIntervalException("Конец должен быть больше начала!");
        }
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }

    public void setFrom(double from) {
        if (from > this.to) {
            throw new InvalidIntervalException("Конец должен быть больше начала!");
        }
        this.from = from;
    }

    public void setTo(double to) {
        if (to < this.from) {
            throw new InvalidIntervalException("Конец должен быть больше начала!");
        }
        this.to = to;
    }

    public double calculateLength() {
        return to - from;
    }

    public boolean isInside(double point) {
        return point >= from  && point <= to;
    }

    private boolean isIntersection(Range range) {
        return  (this.from < range.to && this.to > range.from);
    }

    public Range getIntersection(Range range) {
        if (!this.isIntersection(range)) {
            return null;
        } else {
            return (new Range(Math.max(this.from, range.from), Math.min(this.to, range.to)));
        }
    }

    public Range[] getUnion(Range range) {
        if (!this.isIntersection(range)) {
            return new Range[]{new Range(this.from, this.to), new Range(range.from, range.to)};
        } else {
            return new Range[]{new Range(Math.min(this.from, range.from), Math.max(this.to, range.to))};
        }
    }

    public Range[] getDifference(Range range) {
        if (!this.isIntersection(range)) {
            return new Range[]{new Range(this.from, this.to)};
        } else if (this.from <= range.from && this.to <= range.to) {
            return new Range[]{new Range(this.from, range.from)};
        } else if (this.from <= range.from && this.to >= range.to) {
            return new Range[]{new Range(this.from, range.from), new Range(range.to, this.to)};
        } else if (range.from <= this.from && this.to >= range.to) {
            return new Range[]{new Range(range.to, this.to)};
        } else {
            return new Range[0];
        }
    }

    public String toString() {
        return "(" + this.from + ", " + this.to +")";
    }
}

