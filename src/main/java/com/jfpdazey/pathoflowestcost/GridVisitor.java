package com.jfpdazey.pathoflowestcost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GridVisitor {

    private Grid grid;
    private PathStateComparator pathComparator;
    private PathStateCollector pathCollector;

    public GridVisitor(Grid grid) {
        if (grid == null) {
            throw new IllegalArgumentException("A visitor requires a grid");
        }

        this.grid = grid;
        pathComparator = new PathStateComparator();
    }

    public PathState getBestPathForAllRows() {
        List<PathState> allPaths = new ArrayList<PathState>();
        for (int row = 1; row <= grid.getRowCount(); row++) {
            allPaths.add(getBestPathForRow(row));
        }
        Collections.sort(allPaths, pathComparator);

        return allPaths.get(0);
    }

    public PathState getBestPathForRow(int row) {
        pathCollector = new PathStateCollector();
        PathState initialPath = new PathState(grid.getColumnCount());

        visitPathsForRow(row, initialPath);

        return pathCollector.getBestPath();
    }

    private void visitPathsForRow(int row, PathState path) {
        if (canVisitRowOnPath(row, path)) {
            visitRowOnPath(row, path);
        }

        List<Integer> adjacentRows = grid.getRowsAdjacentTo(row);
        boolean currentPathAdded = false;

        for (int adjacentRow : adjacentRows) {
            if (canVisitRowOnPath(adjacentRow, path)) {
                PathState pathCopy = new PathState(path);
                visitPathsForRow(adjacentRow, pathCopy);
            } else {
                pathCollector.addPath(path);
            }
        }
    }

    private boolean canVisitRowOnPath(int row, PathState path) {
        return !path.isComplete() && !nextVisitOnPathWouldExceedMaximumCost(row, path);
    }

    private void visitRowOnPath(int row, PathState path) {
        int columnToVisit = path.getPathLength() + 1;
        path.addRowWithCost(row, grid.getValueForRowAndColumn(row, columnToVisit));
    }

    private boolean nextVisitOnPathWouldExceedMaximumCost(int row, PathState path) {
        int nextColumn = path.getPathLength() + 1;
        return (path.getTotalCost() + grid.getValueForRowAndColumn(row, nextColumn)) > PathState.MAXIMUM_COST;
    }
}
