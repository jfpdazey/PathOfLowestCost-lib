package com.jfpdazey.pathoflowestcost;

import java.util.Comparator;

public class PathStateComparator implements Comparator<PathState> {

    @Override
    public int compare(PathState leftPath, PathState rightPath) {
        int leftCost = getCostFromPath(leftPath);
        int rightCost = getCostFromPath(rightPath);

        return (leftCost < rightCost) ? -1 : (leftCost == rightCost) ? 0 : 1;
    }

    private int getCostFromPath(PathState path) {
        if (path != null) {
            return path.getTotalCost();
        } else {
            return Integer.MAX_VALUE;
        }
    }
}
