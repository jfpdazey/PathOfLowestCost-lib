package com.jfpdazey.pathoflowestcost;

import java.util.ArrayList;
import java.util.List;

public class GridVisitor {

    private Grid grid;

    public GridVisitor(Grid grid) {
        if (grid == null) {
            throw new IllegalArgumentException("A visitor requires a grid");
        }

        this.grid = grid;
    }

    public List<PathState> visitPathsForRow(int row) {
        List<PathState> paths = new ArrayList<PathState>();
        PathState path = new PathState(grid.getColumnCount());

        while(canVisitRowOnPath(row, path)) {
            visitRowOnPath(row, path);
        }

        paths.add(path);

        return paths;
    }

    private void visitRowOnPath(int row, PathState path) {
        int columnToVisit = path.getPathLength() + 1;
        path.addRowWithCost(row, grid.getValueForRowAndColumn(row, columnToVisit));
    }

    private boolean canVisitRowOnPath(int row, PathState path) {
        return !path.isComplete() && !nextVisitOnPathWouldExceedMaximumCost(row, path);
    }

    private boolean nextVisitOnPathWouldExceedMaximumCost(int row, PathState path) {
        int nextColumn = path.getPathLength() + 1;
        return (path.getTotalCost() + grid.getValueForRowAndColumn(row, nextColumn)) > PathState.MAXIMUM_COST;
    }
}
