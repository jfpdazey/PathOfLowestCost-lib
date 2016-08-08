package com.jfpdazey.pathoflowestcost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grid {
    int[][] values;

    Grid(int[][] values) {
        if (values.length < 1 || values.length > 10) {
            throw new IllegalArgumentException("Between one and ten rows of values are expected");
        } else if (values[0].length < 5 || values[0].length > 100) {
            throw new IllegalArgumentException("Between five and one hundred columns of values are expected");
        }

        this.values = values;
    }

    public int getValueForRowAndColumn(int row, int column) {
        return values[row - 1][column - 1];
    }

    public int getRowCount() {
        return values.length;
    }

    public int getColumnCount() {
        return values[0].length;
    }

    public List<Integer> getRowsAdjacentTo(int rowNumber) {
        Set<Integer> adjacentRows = new HashSet<Integer>();

        if (isValidRowNumber(rowNumber)) {
            adjacentRows.add(rowNumber);
            adjacentRows.add(getRowAbove(rowNumber));
            adjacentRows.add(getRowBelow(rowNumber));
        }

        return new ArrayList<Integer>(adjacentRows);
    }

    private boolean isValidRowNumber(int rowNumber) {
        return (rowNumber > 0) && (rowNumber <= values.length);
    }

    private int getRowAbove(int rowNumber) {
        int potentialRowNumber = rowNumber - 1;
        return (potentialRowNumber < 1) ? values.length : potentialRowNumber;
    }

    private int getRowBelow(int rowNumber) {
        int potentialRowNumber = rowNumber + 1;
        return (potentialRowNumber > values.length) ? 1 : potentialRowNumber;
    }
}
