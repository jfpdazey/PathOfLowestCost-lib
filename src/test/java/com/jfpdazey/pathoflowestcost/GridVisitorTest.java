package com.jfpdazey.pathoflowestcost;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GridVisitorTest {

    @Test
    public void startsOffWithNoTotalCost() {
        GridVisitor subject = new GridVisitor();
        assertThat(subject.getTotalCost(), equalTo(0));
    }

    @Test
    public void accumulatesTotalCostForOneRound() {
        GridVisitor subject = new GridVisitor();
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });

        subject.visit(grid);

        assertThat(subject.getTotalCost(), equalTo(1));
    }

    @Test
    public void accumulatesTotalCostForTwoRounds() {
        GridVisitor subject = new GridVisitor();
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });

        subject.visit(grid);
        subject.visit(grid);

        assertThat(subject.getTotalCost(), equalTo(3));
    }

    @Test
    public void accumulatesTotalCostAcrossEntireRow() {
        GridVisitor subject = new GridVisitor();
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });

        subject.visit(grid);
        subject.visit(grid);
        subject.visit(grid);
        subject.visit(grid);
        subject.visit(grid);

        assertThat(subject.getTotalCost(), equalTo(15));
    }

    @Test
    public void knowsWhenThereAreMoreVisits() {
        GridVisitor subject = new GridVisitor();
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });

        subject.visit(grid);
        subject.visit(grid);

        assertThat(subject.canVisit(grid), is(true));
    }

    @Test
    public void knowsWhenThereAreNoMoreVisits() {
        GridVisitor subject = new GridVisitor();
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });

        subject.visit(grid);
        subject.visit(grid);
        subject.visit(grid);
        subject.visit(grid);
        subject.visit(grid);

        assertThat(subject.canVisit(grid), is(false));
    }
}
