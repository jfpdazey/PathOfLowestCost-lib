package com.jfpdazey.pathoflowestcost;

import java.util.ArrayList;
import java.util.List;

public class GridVisitor {

    public static int MAXIMUM_COST = 50;

    private int totalCost;
    private int currentColumn;
    private List<Integer> path = new ArrayList<Integer>();

    public int getTotalCost() {
        return totalCost;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public void visit(Grid grid) {
        if (canVisit(grid)) {
            currentColumn++;
            totalCost += grid.getValueForColumn(currentColumn);
            path.add(1);
        }
    }

    public boolean canVisit(Grid grid) {
        return (currentColumn < grid.getColumnCount())
                && (totalCost + grid.getValueForColumn(currentColumn + 1) <= MAXIMUM_COST);
    }

    public List<Integer> getPath() {
        return path;
    }

    public boolean isSuccessful(Grid grid) {
        return (path.size() == grid.getColumnCount())
                && (totalCost <= MAXIMUM_COST);
    }
}
