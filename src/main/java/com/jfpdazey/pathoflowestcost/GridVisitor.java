package com.jfpdazey.pathoflowestcost;

public class GridVisitor {

    public int score;
    public int currentColumn = 1;

    public void visit(Grid grid) {
        score += grid.getValueForColumn(currentColumn);
        currentColumn++;
    }
}
