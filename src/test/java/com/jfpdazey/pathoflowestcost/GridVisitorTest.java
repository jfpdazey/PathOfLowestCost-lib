package com.jfpdazey.pathoflowestcost;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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

        assertThat(subject.getPathState().getTotalCost(), equalTo(0));
    }

    @Test
    public void accumulatesTotalCostForOneRound() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visitRow(1);

        assertThat(subject.getPathState().getTotalCost(), equalTo(1));
    }

    @Test
    public void accumulatesTotalCostForTwoRounds() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visitRow(1);
        subject.visitRow(1);

        assertThat(subject.getPathState().getTotalCost(), equalTo(3));
    }

    @Test
    public void accumulatesTotalCostAcrossEntireRow() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visitRow(1);
        subject.visitRow(1);
        subject.visitRow(1);
        subject.visitRow(1);
        subject.visitRow(1);

        assertThat(subject.getPathState().getTotalCost(), equalTo(15));
    }

    @Test
    public void knowsWhenThereAreMoreVisits() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visitRow(1);
        subject.visitRow(1);

        assertThat(subject.canVisitRow(1), is(true));
    }

    @Test
    public void knowsWhenThereAreNoMoreVisits() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visitRow(1);
        subject.visitRow(1);
        subject.visitRow(1);
        subject.visitRow(1);
        subject.visitRow(1);

        assertThat(subject.canVisitRow(1), is(false));
    }

    @Test
    public void cannotVisitAtAllWhenFirstVisitCostWouldExceedMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST + 1, 0, 0, 0, 0 } });
        GridVisitor subject = new GridVisitor(grid);

        assertThat(subject.canVisitRow(1), is(false));
    }

    @Test
    public void cannotVisitFurtherWhenTotalCostWouldExceedMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST - 1, 1, 1, 0, 0 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visitRow(1);
        assertThat(subject.canVisitRow(1), is(true));

        subject.visitRow(1);
        assertThat(subject.canVisitRow(1), is(false));
    }

    @Test
    public void firstVisitDoesNotAccumumlateCostIfItExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST + 1, 1, 1, 0, 0 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visitRow(1);
        assertThat(subject.getPathState().getTotalCost(), equalTo(0));
    }

    @Test
    public void furtherVisitsDoNotAccumulateCostWhenTotalCostExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST, 1, 1, 1, 1 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visitRow(1);
        assertThat(subject.getPathState().getTotalCost(), equalTo(50));

        subject.visitRow(1);
        assertThat(subject.getPathState().getTotalCost(), equalTo(50));
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

        subject.visitRow(1);
        assertThat(subject.getCurrentColumn(), equalTo(1));
    }

    @Test
    public void doesNotIncrementColumnAfterVisitExceedsMaximumCost() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST, 1, 1, 1, 1 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visitRow(1);
        subject.visitRow(1);
        assertThat(subject.getCurrentColumn(), equalTo(1));
    }

    @Test
    public void startsWithEmptyPath() {
        Grid grid = new Grid(new int[][]{ { 2, 2, 2, 2, 2 } });
        GridVisitor subject = new GridVisitor(grid);
        assertThat(subject.getPathState().getRowsTraversed().size(), equalTo(0));
    }

    @Test
    public void addsRowToPathAfterVisiting() {
        Grid grid = new Grid(new int[][]{ { 2, 2, 2, 2, 2 } });
        GridVisitor subject = new GridVisitor(grid);
        List<Integer> expectedPath = new ArrayList<Integer>();

        subject.visitRow(1);
        expectedPath.add(1);
        assertThat(subject.getPathState().getRowsTraversed(), equalTo(expectedPath));

        subject.visitRow(1);
        expectedPath.add(1);
        assertThat(subject.getPathState().getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void isNotSuccessfulBeforeStarting() {
        Grid grid = new Grid(new int[][]{ { 2, 2, 2, 2, 2 } });
        GridVisitor subject = new GridVisitor(grid);

        assertThat(subject.getPathState().successful, is(false));
    }

    @Test
    public void pathIsSuccessfulIfGridIsCompletelyTraversed() {
        Grid grid = new Grid(new int[][]{ { 1, 1, 1, 1, PathState.MAXIMUM_COST - 4 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visitRow(1);
        subject.visitRow(1);
        subject.visitRow(1);
        subject.visitRow(1);
        subject.visitRow(1);

        assertThat(subject.getPathState().successful, is(true));
    }

    @Test
    public void isNotSuccessfulIfGridIsPartiallyTraversed() {
        Grid grid = new Grid(new int[][]{ { 2, 2, 2, 2, 2 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visitRow(1);
        subject.visitRow(1);
        subject.visitRow(1);
        subject.visitRow(1);

        assertThat(subject.getPathState().successful, is(false));
    }

    @Test
    public void pathIsNotSuccessfulIfLastVisitCausesTotalCostToExceedMaximumCost() {
        Grid grid = new Grid(new int[][]{ { 0, 0, 0, 0, PathState.MAXIMUM_COST + 1 } });
        GridVisitor subject = new GridVisitor(grid);

        subject.visitRow(1);
        subject.visitRow(1);
        subject.visitRow(1);
        subject.visitRow(1);
        subject.visitRow(1);

        assertThat(subject.getPathState().successful, is(false));
    }

    @Test
    public void visitsOtherRowsInGrid() {
        Grid twoRowGrid = new Grid(new int[][]{
            { 2, 2, 2, 2, 2 }, { 1, 1, 1, 1, 1 }
        });
        GridVisitor subject = new GridVisitor(twoRowGrid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 2, 2, 2, 2, 2 })
        );

        subject.visitRow(2);
        subject.visitRow(2);
        subject.visitRow(2);
        subject.visitRow(2);
        subject.visitRow(2);

        assertThat(subject.getPathState().getTotalCost(), equalTo(5));
        assertThat(subject.getPathState().getRowsTraversed(), equalTo(expectedPath));
        assertThat(subject.getPathState().successful, is(true));
    }

    @Test
    public void canVisitHandlesOtherRows() {
        Grid grid = new Grid(new int[][]{
            { 1, 1, 1, 1, 1 }, { PathState.MAXIMUM_COST - 1, 1, 1, 0, 0, 0 }
        });
        GridVisitor subject = new GridVisitor(grid);

        assertThat(subject.canVisitRow(2), is(true));

        subject.visitRow(2);
        assertThat(subject.canVisitRow(2), is(true));

        subject.visitRow(2);
        assertThat(subject.canVisitRow(2), is(false));
    }
}
