package com.jfpdazey.pathoflowestcost;

import java.util.ArrayList;
import java.util.List;

public class PathState {

    public static int MAXIMUM_COST = 50;

    private List<Integer> rowsTraversed = new ArrayList<Integer>();
    private int totalCost = 0;
    private int expectedLength = 0;

    PathState(int expectedLength) {
        this.expectedLength = expectedLength;
    }

    public List<Integer> getRowsTraversed() {
        return rowsTraversed;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getPathLength() {
        return rowsTraversed.size();
    }

    public void addRowWithCost(int row, int cost) {
        rowsTraversed.add(row);
        totalCost += cost;
    }

    public boolean isComplete() {
        return rowsTraversed.size() == expectedLength;
    }

    public boolean isSuccessful() {
        return isComplete() && !isOverCost();
    }

    public boolean isOverCost() {
        return totalCost > MAXIMUM_COST;
    }
}
