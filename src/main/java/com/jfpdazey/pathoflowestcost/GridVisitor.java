package com.jfpdazey.pathoflowestcost;

import java.util.ArrayList;
import java.util.List;

public class GridVisitor {

    private Grid grid;
    private PathState pathState;

    public GridVisitor(Grid grid) {
        if (grid == null) {
            throw new IllegalArgumentException("A visitor requires a grid");
        }

        this.grid = grid;
        this.pathState = new PathState(grid.getColumnCount());
    }

    public List<PathState> visitPathsForRow(int row) {
        List<PathState> pathStates = new ArrayList<PathState>();
        while(canVisitRow(row)) {
            visitRow(row);
        }

        pathStates.add(this.pathState);

        return pathStates;
    }

    private void visitRow(int row) {
        if (canVisitRow(row)) {
            int columnToVisit = pathState.getPathLength() + 1;
            pathState.addRowWithCost(row, grid.getValueForRowAndColumn(row, columnToVisit));
        }
    }

    private boolean canVisitRow(int row) {
        return !pathState.isComplete() && !nextVisitWouldExceedMaximumCost(row);
    }

    private boolean nextVisitWouldExceedMaximumCost(int row) {
        int nextColumn = pathState.getPathLength() + 1;
        return (pathState.getTotalCost() + grid.getValueForRowAndColumn(row, nextColumn)) > PathState.MAXIMUM_COST;
    }
}
