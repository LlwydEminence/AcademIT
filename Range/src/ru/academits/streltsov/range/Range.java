package ru.academits.streltsov.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
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
        this.from = from;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double calculateLength() {
        return to - from;
    }

    public boolean isInside(double point) {
        return point >= from  && point <= to;
    }

    public Range getIntersection(Range range) {
        if ((this.from <= range.from) && (this.to >= range.from)) {
            return new Range(range.from, this.to);
        } else if ((range.from <= this.from) && (range.to >= this.from)) {
            return new Range(this.from, range.to);
        } else {
            return null;
        }
    }

    public Range getMerger(Range range) {
        if ((this.from <= range.from) && (this.to >= range.from)) {
            return new Range(this.from, range.to);
        } else if ((range.from <= this.from) && (range.to >= this.from)) {
            return new Range(range.from, this.to);
        } else {
            return null;
        }
    }

    public Range[] getDifference(Range range) {
        Range[] difference = new Range[2];
        if ((this.from < range.from) && (this.to > range.to)) {
            difference[0] = new Range(this.from, range.from);
            difference[1] = new Range(range.to, this.to);
            return difference;
        } else if ((this.from == range.from) && (range.to < this.to)) {
            difference[0] = new Range(range.to, this.to);
            return difference;
        } else if ((this.from < range.from) && (this.to == range.to)) {
            difference[0] = new Range(this.from, range.from);
            return difference;
        } else if ((range.from < this.from) && (this.to > range.to)) {
            difference[0] = new Range(range.from, this. from);
            difference[1] = new Range(range.to, this.to);
            return difference;
        } else if ((range.from < this.from) && (range.to == this.to)) {
            difference[0] = new Range(range.from, this.from);
            return difference;
        } else if ((range.from == this.from) && (range.to > this.to)) {
            difference[0] = new Range(this.to, range.to);
            return difference;
        } else if ((this.to > range.from) && (range.to > this.to)) {
            difference[0] = new Range(this.from, range.from);
            difference[1] = new Range(this.to, range.to);
            return difference;
        } else if ((range.from < this.from) && (this.to < range.to)) {
            difference[0] = new Range(range.from, this.from);
            difference[1] = new Range(this.to, range.from);
            return difference;
        } else {
            return null;
        }
    }
}

