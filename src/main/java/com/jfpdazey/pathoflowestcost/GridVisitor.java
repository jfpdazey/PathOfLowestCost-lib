package com.jfpdazey.pathoflowestcost;

public class GridVisitor {

    private int score;
    private int currentColumn = 1;

    public int getScore() {
        return score;
    }

    public void visit(Grid grid) {
        score += grid.getValueForColumn(currentColumn);
        currentColumn++;
    }

    public boolean canVisit(Grid grid) {
        return currentColumn <= grid.getColumnCount();
    }
}
