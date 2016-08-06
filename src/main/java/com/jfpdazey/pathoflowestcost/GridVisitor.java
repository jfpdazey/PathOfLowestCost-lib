package com.jfpdazey.pathoflowestcost;

public class GridVisitor {

    private int totalCost;
    private int currentColumn = 0;

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
        }
    }

    public boolean canVisit(Grid grid) {
        return (currentColumn < grid.getColumnCount())
                && (totalCost < 50);
    }
}
