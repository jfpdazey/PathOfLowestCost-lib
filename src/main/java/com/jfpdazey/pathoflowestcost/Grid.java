package com.jfpdazey.pathoflowestcost;

public class Grid {
    Grid(int[][] values) {
        if (values.length < 1) {
            throw new IllegalArgumentException("At least one row of values is expected");
        } else if (values[0].length < 5) {
            throw new IllegalArgumentException("At least five columns of values are expected");
        }
    }
}