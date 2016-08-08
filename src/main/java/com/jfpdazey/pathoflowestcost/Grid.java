package com.jfpdazey.pathoflowestcost;

import java.util.ArrayList;
import java.util.List;

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

    public int getValueForRowAndColumn(int row, int column) {
        return values[row - 1][column - 1];
    }

    public int getColumnCount() {
        return values[0].length;
    }

    public List<Integer> getRowsAdjacentTo(int rowNumber) {
        List<Integer> adjacentRows = new ArrayList<Integer>();

        if (isValidRowNumber(rowNumber)) {
            adjacentRows.add(1);
            if (values.length > 1) {
                adjacentRows.add(2);
            }
        }

        return adjacentRows;
    }

    private boolean isValidRowNumber(int rowNumber) {
        return rowNumber > 0 && rowNumber <= values.length;
    }
}
