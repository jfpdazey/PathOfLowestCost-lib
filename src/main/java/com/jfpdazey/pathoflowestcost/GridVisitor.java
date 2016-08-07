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

    public void visitRow(int row) {
        if (canVisitRow(row)) {
            currentColumn++;
            pathState.addRow(row, grid.getValueForRowAndColumn(row, currentColumn));
            pathState.successful = isSuccessful();
        }
    }

    public boolean canVisitRow(int row) {
        return (currentColumn < grid.getColumnCount()) && !nextVisitWouldExceedMaximumCost(row);
    }

    private boolean nextVisitWouldExceedMaximumCost(int row) {
        return (pathState.getTotalCost() + grid.getValueForRowAndColumn(row, currentColumn + 1)) > PathState.MAXIMUM_COST;
    }

    private boolean isSuccessful() {
        return (pathState.getRowsTraversed().size() == grid.getColumnCount()) && !pathState.isOverCost();
    }
}
