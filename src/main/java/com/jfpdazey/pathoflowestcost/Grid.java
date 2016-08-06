package com.jfpdazey.pathoflowestcost;

public class Grid {
    int[][] values;

    Grid(int[][] values) {
        if (values.length < 1) {
            throw new IllegalArgumentException("At least one row of values is expected");
        } else if (values[0].length < 5) {
            throw new IllegalArgumentException("At least five columns of values are expected");
        }

        this.values = values;
}

    public int getValueForColumn(int column) {
        return values[0][column - 1];
    }

    public int getColumnCount() {
        return values[0].length;
    }
}
