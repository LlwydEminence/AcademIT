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
        } else if (this.from <= range.from && this.to <= range.to) {
            Range[] difference = new Range[1];
            difference[0] = new Range(this.from, range.from);
            return difference;
        } else if (this.from <= range.from && this.to >= range.to) {
            Range[] difference = new Range[2];
            difference[0] = new Range(this.from, range.from);
            difference[1] = new Range(range.to, this.to);
            return difference;
        } else if (range.from <= this.from && this.to >= range.to) {
            Range[] difference = new Range[1];
            difference[0] = new Range(range.to, this.to);
            return difference;
        } else {
            return new Range[0];
        }
    }

    public static String toString(Range[] range) {
        if (range == null) {
            return "null";
        }
        int iMax = range.length - 1;
        if (iMax == -1) {
            return "[]";
        }

        StringBuilder a = new StringBuilder();
        a.append('[');
        for (int i = 0; ; ++i) {
            a.append('(');
            a.append(range[i].from);
            a.append(", ");
            a.append(range[i].to);
            a.append(')');
            if (i == iMax) {
                return a.append(']').toString();
            }
            a.append(", ");
        }
    }
}

