package com.jfpdazey.pathoflowestcost;

import java.util.Comparator;

public class PathStateComparator implements Comparator<PathState> {

    @Override
    public int compare(PathState leftPath, PathState rightPath) {
        int comparedLength = compareLengths(leftPath, rightPath);
        return (comparedLength != 0) ? comparedLength : compareCosts(leftPath, rightPath);
    }

    private int compareLengths(PathState leftPath, PathState rightPath) {
        int leftLength = getLengthFromPath(leftPath);
        int rightLength = getLengthFromPath(rightPath);

        return (leftLength > rightLength) ? -1 : (leftLength == rightLength) ? 0 : 1;
    }

    private int compareCosts(PathState leftPath, PathState rightPath) {
        int leftCost = getCostFromPath(leftPath);
        int rightCost = getCostFromPath(rightPath);

        return (leftCost < rightCost) ? -1 : (leftCost == rightCost) ? 0 : 1;
    }

    private int getLengthFromPath(PathState path) {
        if (path != null) {
            return path.getPathLength();
        } else {
            return Integer.MIN_VALUE;
        }
    }

    private int getCostFromPath(PathState path) {
        if (path != null) {
            return path.getTotalCost();
        } else {
            return Integer.MAX_VALUE;
        }
    }
}
