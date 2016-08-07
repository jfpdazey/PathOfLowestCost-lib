package com.jfpdazey.pathoflowestcost;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GridVisitorTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeConstructedWithoutAGrid() {
        GridVisitor subject = new GridVisitor(null);
    }

    @Test
    public void startsOffWithNoTotalCost() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(grid);

        assertThat(subject.getTotalCost(), equalTo(0));
    }

    @Test
    public void accumulatesTotalCostForOneRound() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visit();

        assertThat(subject.getTotalCost(), equalTo(1));
    }

    @Test
    public void accumulatesTotalCostForTwoRounds() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visit();
        subject.visit();

        assertThat(subject.getTotalCost(), equalTo(3));
    }

    @Test
    public void accumulatesTotalCostAcrossEntireRow() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visit();
        subject.visit();
        subject.visit();
        subject.visit();
        subject.visit();

        assertThat(subject.getTotalCost(), equalTo(15));
    }

    @Test
    public void knowsWhenThereAreMoreVisits() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visit();
        subject.visit();

        assertThat(subject.canVisit(), is(true));
    }

    @Test
    public void knowsWhenThereAreNoMoreVisits() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visit();
        subject.visit();
        subject.visit();
        subject.visit();
        subject.visit();

        assertThat(subject.canVisit(), is(false));
    }

    @Test
    public void cannotVisitAtAllWhenFirstVisitCostWouldExceedMaximum() {
        Grid grid = new Grid(new int[][]{ { GridVisitor.MAXIMUM_COST + 1, 0, 0, 0, 0 } });
        GridVisitor subject = new GridVisitor(grid);

        assertThat(subject.canVisit(), is(false));
    }

    @Test
    public void cannotVisitFurtherWhenTotalCostWouldExceedMaximum() {
        Grid grid = new Grid(new int[][]{ { GridVisitor.MAXIMUM_COST - 1, 1, 1, 0, 0 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visit();
        assertThat(subject.canVisit(), is(true));

        subject.visit();
        assertThat(subject.canVisit(), is(false));
    }

    @Test
    public void firstVisitDoesNotAccumumlateCostIfItExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { GridVisitor.MAXIMUM_COST + 1, 1, 1, 0, 0 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visit();
        assertThat(subject.getTotalCost(), equalTo(0));
    }

    @Test
    public void furtherVisitsDoNotAccumulateCostWhenTotalCostExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { GridVisitor.MAXIMUM_COST, 1, 1, 1, 1 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visit();
        assertThat(subject.getTotalCost(), equalTo(50));

        subject.visit();
        assertThat(subject.getTotalCost(), equalTo(50));
    }

    @Test
    public void startsAtColumnZero() {
        Grid grid = new Grid(new int[][]{ { 2, 2, 2, 2, 2 } });
        GridVisitor subject = new GridVisitor(grid);

        assertThat(subject.getCurrentColumn(), equalTo(0));
    }

    @Test
    public void incrementsColumnAfterVisiting() {
        Grid grid = new Grid(new int[][]{ { 2, 2, 2, 2, 2 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visit();
        assertThat(subject.getCurrentColumn(), equalTo(1));
    }

    @Test
    public void doesNotIncrementColumnAfterVisitExceedsMaximumCost() {
        Grid grid = new Grid(new int[][]{ { GridVisitor.MAXIMUM_COST, 1, 1, 1, 1 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visit();
        subject.visit();
        assertThat(subject.getCurrentColumn(), equalTo(1));
    }

    @Test
    public void startsWithEmptyPath() {
        Grid grid = new Grid(new int[][]{ { 2, 2, 2, 2, 2 } });
        GridVisitor subject = new GridVisitor(grid);
        assertThat(subject.getPath().size(), equalTo(0));
    }

    @Test
    public void addsRowToPathAfterVisiting() {
        Grid grid = new Grid(new int[][]{ { 2, 2, 2, 2, 2 } });
        GridVisitor subject = new GridVisitor(grid);
        List<Integer> expectedPath = new ArrayList<Integer>();

        subject.visit();
        expectedPath.add(1);
        assertThat(subject.getPath(), equalTo(expectedPath));

        subject.visit();
        expectedPath.add(1);
        assertThat(subject.getPath(), equalTo(expectedPath));
    }

    @Test
    public void isNotSuccessfulBeforeStarting() {
        Grid grid = new Grid(new int[][]{ { 2, 2, 2, 2, 2 } });
        GridVisitor subject = new GridVisitor(grid);

        assertThat(subject.isSuccessful(), is(false));
    }

    @Test
    public void isSuccessfulIfGridIsCompletelyTraversed() {
        Grid grid = new Grid(new int[][]{ { 1, 1, 1, 1, GridVisitor.MAXIMUM_COST - 4 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visit();
        subject.visit();
        subject.visit();
        subject.visit();
        subject.visit();

        assertThat(subject.isSuccessful(), is(true));
    }

    @Test
    public void isNotSuccessfulIfGridIsPartiallyTraversed() {
        Grid grid = new Grid(new int[][]{ { 2, 2, 2, 2, 2 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visit();
        subject.visit();
        subject.visit();
        subject.visit();

        assertThat(subject.isSuccessful(), is(false));
    }

    @Test
    public void isNotSuccessfulIfLastVisitCausesTotalCostToExceedMaximumCost() {
        Grid grid = new Grid(new int[][]{ { 0, 0, 0, 0, GridVisitor.MAXIMUM_COST + 1 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visit();
        subject.visit();
        subject.visit();
        subject.visit();
        subject.visit();

        assertThat(subject.isSuccessful(), is(false));
    }
}
