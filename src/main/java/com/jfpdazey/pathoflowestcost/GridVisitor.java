package com.jfpdazey.pathoflowestcost;

public class GridVisitor {

    private Grid grid;
    private int currentColumn;
    private PathState pathState;

    public GridVisitor(Grid grid) {
        if (grid == null) {
            throw new IllegalArgumentException("A visitor requires a grid");
        }

        this.grid = grid;
        this.pathState = new PathState();
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public PathState getPathState() { return pathState; }

    public void visit() {
        if (canVisit()) {
            currentColumn++;
            pathState.addRow(1, grid.getValueForColumn(currentColumn));
            pathState.successful = isSuccessful();
        }
    }

    public boolean canVisit() {
        return (currentColumn < grid.getColumnCount()) && !nextVisitWouldExceedMaximumCost();
    }

    public boolean isSuccessful() {
        return (pathState.getRowsTraversed().size() == grid.getColumnCount()) && !pathState.isOverCost();
    }

    private boolean nextVisitWouldExceedMaximumCost() {
        return (pathState.getTotalCost() + grid.getValueForColumn(currentColumn + 1)) > PathState.MAXIMUM_COST;
    }
}
