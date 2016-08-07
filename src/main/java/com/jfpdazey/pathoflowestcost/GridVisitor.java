package com.jfpdazey.pathoflowestcost;

public class GridVisitor {

    private Grid grid;
    private PathState pathState;

    public GridVisitor(Grid grid) {
        if (grid == null) {
            throw new IllegalArgumentException("A visitor requires a grid");
        }

        this.grid = grid;
        this.pathState = new PathState();
    }

    public PathState getPathState() { return pathState; }

    public void visitRow(int row) {
        if (canVisitRow(row)) {
            int column = pathState.getPathLength() + 1;
            pathState.addRowWithCost(row, grid.getValueForRowAndColumn(row, column));
            pathState.successful = isSuccessful();
        }
    }

    public boolean canVisitRow(int row) {
        return !pathIsComplete() && !nextVisitWouldExceedMaximumCost(row);
    }

    private boolean pathIsComplete() {
        return pathState.getPathLength() == grid.getColumnCount();
    }

    private boolean nextVisitWouldExceedMaximumCost(int row) {
        int nextColumn = pathState.getPathLength() + 1;
        return (pathState.getTotalCost() + grid.getValueForRowAndColumn(row, nextColumn)) > PathState.MAXIMUM_COST;
    }

    private boolean isSuccessful() {
        return pathIsComplete() && !pathState.isOverCost();
    }
}
