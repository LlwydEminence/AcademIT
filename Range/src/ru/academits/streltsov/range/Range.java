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
        return  (Math.min(this.to, range.to) - Math.max(this.from, range.from) > 0);
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
            Range[] union = new Range[2];
            union[0] = new Range(this.from, this.to);
            union[1] = new Range(range.from, range.to);
            return union;
        } else {
            Range[] union = new Range[1];
            union[0] = new Range(Math.min(this.from, range.from), Math.max(this.to, range.to));
            return union;
        }
    }

    public Range[] getDifference(Range range) {
        if (!this.isIntersection(range)) {
            Range[] difference = new Range[1];
            difference[0] = new Range(this.from, this.to);
            return difference;
        } else if (this.from <= range.from && this.to >= range.to) {
            Range[] difference = new Range[2];
            difference[0] = new Range(this.from, range.from);
            difference[1] = new Range(range.to, this.to);
            return difference;
        } else if (this.from <= range.from && this.to <= range.to) {
            Range[] difference = new Range[1];
            difference[0] = new Range(this.from, range.from);
            return difference;
        } else if (range.from <= this.from && this.to >= range.to) {
            Range[] difference = new Range[1];
            difference[0] = new Range(range.to, this.to);
            return difference;
        } else {
            return new Range[0];
        }
    }
}

