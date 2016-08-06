package com.jfpdazey.pathoflowestcost;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GridVisitorTest {

    private GridVisitor subject;

    @Before
    public void setUp() {
        subject = new GridVisitor();
    }

    @Test
    public void startsOffWithNoTotalCost() {
        assertThat(subject.getTotalCost(), equalTo(0));
    }

    @Test
    public void accumulatesTotalCostForOneRound() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });

        subject.visit(grid);

        assertThat(subject.getTotalCost(), equalTo(1));
    }

    @Test
    public void accumulatesTotalCostForTwoRounds() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });

        subject.visit(grid);
        subject.visit(grid);

        assertThat(subject.getTotalCost(), equalTo(3));
    }

    @Test
    public void accumulatesTotalCostAcrossEntireRow() {
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
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });

        subject.visit(grid);
        subject.visit(grid);

        assertThat(subject.canVisit(grid), is(true));
    }

    @Test
    public void knowsWhenThereAreNoMoreVisits() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });

        subject.visit(grid);
        subject.visit(grid);
        subject.visit(grid);
        subject.visit(grid);
        subject.visit(grid);

        assertThat(subject.canVisit(grid), is(false));
    }

    @Test
    public void cannotVisitFurtherWhenTotalCostIsFiftyOrMore() {
        Grid grid = new Grid(new int[][]{ { 49, 1, 0, 0, 0 } });

        subject.visit(grid);
        assertThat(subject.canVisit(grid), is(true));

        subject.visit(grid);
        assertThat(subject.canVisit(grid), is(false));
    }

    @Test
    public void furtherVisitsDoNotAccumulateCostWhenTotalCostIsFiftyOrMore() {
        Grid grid = new Grid(new int[][]{ { 50, 1, 1, 1, 1 } });

        subject.visit(grid);
        assertThat(subject.getTotalCost(), equalTo(50));

        subject.visit(grid);
        assertThat(subject.getTotalCost(), equalTo(50));
    }

    @Test
    public void startsAtColumnZero() {
        Grid grid = new Grid(new int[][]{ { 1, 1, 1, 1, 1 } });

        assertThat(subject.getCurrentColumn(), equalTo(0));
    }

    @Test
    public void incrementsColumnAfterVisiting() {
        Grid grid = new Grid(new int[][]{ { 1, 1, 1, 1, 1 } });

        subject.visit(grid);
        assertThat(subject.getCurrentColumn(), equalTo(1));
    }

    @Test
    public void startsWithEmptyPath() {
        assertThat(subject.getPath().size(), equalTo(0));
    }

    @Test
    public void addsRowToPathAfterVisiting() {
        Grid grid = new Grid(new int[][]{ { 2, 2, 2, 2, 2 } });
        List<Integer> expectedPath = new ArrayList<Integer>();

        subject.visit(grid);
        expectedPath.add(1);
        assertThat(subject.getPath(), equalTo(expectedPath));

        subject.visit(grid);
        expectedPath.add(1);
        assertThat(subject.getPath(), equalTo(expectedPath));
    }
}
