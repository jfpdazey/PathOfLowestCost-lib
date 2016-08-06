package com.jfpdazey.pathoflowestcost;

public class GridVisitor {

    private int totalCost;
    private int currentColumn = 1;

    public int getTotalCost() {
        return totalCost;
    }

    public void visit(Grid grid) {
        totalCost += grid.getValueForColumn(currentColumn);
        currentColumn++;
    }

    public boolean canVisit(Grid grid) {
        return currentColumn <= grid.getColumnCount();
    }
}
