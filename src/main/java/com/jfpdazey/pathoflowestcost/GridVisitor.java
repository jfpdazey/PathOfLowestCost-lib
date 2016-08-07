package com.jfpdazey.pathoflowestcost;

import java.util.ArrayList;
import java.util.List;

public class GridVisitor {

    public static int MAXIMUM_COST = 50;

    private Grid grid;
    private int totalCost;
    private int currentColumn;
    private List<Integer> path;
    private PathState pathState;

    public GridVisitor(Grid grid) {
        if (grid == null) {
            throw new IllegalArgumentException("A visitor requires a grid");
        }

        this.grid = grid;
        this.path = new ArrayList<Integer>();
        this.pathState = new PathState();
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public List<Integer> getPath() {
        return path;
    }

    public PathState getPathState() { return pathState; }

    public void visit() {
        if (canVisit()) {
            currentColumn++;
            totalCost += grid.getValueForColumn(currentColumn);
            path.add(1);
            pathState.addRow(1, grid.getValueForColumn(currentColumn));
            pathState.successful = isSuccessful();
        }
    }

    public boolean canVisit() {
        return (currentColumn < grid.getColumnCount()) && !nextVisitWouldExceedMaximumCost();
    }

    public boolean isSuccessful() {
        return (path.size() == grid.getColumnCount())
                && (totalCost <= MAXIMUM_COST);
    }

    private boolean nextVisitWouldExceedMaximumCost() {
        return (totalCost + grid.getValueForColumn(currentColumn + 1)) > MAXIMUM_COST;
    }
}
