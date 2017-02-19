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

    private boolean isIntersection(Range range) {
        return  ((this.from <= range.from && this.to >= range.from) ||(range.from <= this.from && range.to >= this.from));
    }

    public Range getIntersection(Range range) {
        if (!this.isIntersection(range)) {
            return null;
        } else if (this.from <= range.from && this.to >= range.from && this.to <= range.to) {
            return new Range(range.from, this.to);
        } else if (this.from <= range.from && this.to >= range.from && range.to <= this.to) {
            return new Range(range.from, range.to);
        } else if (range.from <= this.from && range.to >= this.from && range.to <= this.to) {
            return new Range(this.from, range.to);
        } else {
            return new Range(this.from, this.to);
        }
    }

    public Range[] getUnion(Range range) {
        if (!this.isIntersection(range)) {
            Range[] union = new Range[2];
            union[0] = new Range(this.from, this.to);
            union[1] = new Range(range.from, range.to);
            return union;
        } else {
            Range[] union = new Range[1];
            if (this.from <= range.from && this.to >= range.to) {
                union[0] = new Range(this.from, this.to);
                return union;
            } else if (this.from <= range.from && range.to >= this.to) {
                union[0] = new Range(this.from, range.to);
                return union;
            } else if (range.from <= this.from && range.to >= this.to) {
                union[0] = new Range(range.from, range.to);
                return union;
            } else {
                union[0] = new Range(range.from, this.to);
                return union;
            }
        }
    }

    public Range[] getDifference(Range range) {
        Range[] difference = new Range[0];
        if (!this.isIntersection(range)) {
            difference = new Range[1];
            difference[0] = new Range(this.from, this.to);
            return difference;
        } else if (this.from <= range.from && this.to >= range.from && this.to <= range.to) {
            difference = new Range[1];
            difference[0] = new Range(this.from, range.from);
            return difference;
        } else if (this.from >= range.from && range.to <= this.to) {
            difference = new Range[1];
            difference[0] = new Range(range.to, this.to);
            return difference;
        } else if (this.from <= range.from && this.to >= range.to) {
            difference = new Range[2];
            difference[0] = new Range(this.from, range.from);
            difference[1] = new Range(range.to, this.to);
            return difference;
        } else {
            return difference;
        }
    }
}

