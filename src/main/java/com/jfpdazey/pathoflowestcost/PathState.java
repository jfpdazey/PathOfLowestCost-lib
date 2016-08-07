package com.jfpdazey.pathoflowestcost;

import java.util.ArrayList;
import java.util.List;

public class PathState {

    public boolean successful = false;
    private List<Integer> rowsTraversed = new ArrayList<Integer>();
    private int totalCost = 0;

    public List<Integer> getRowsTraversed() {
        return rowsTraversed;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void addRow(int row, int cost) {
        rowsTraversed.add(row);
        totalCost += cost;
    }
}
