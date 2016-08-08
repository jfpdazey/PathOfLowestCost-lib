package com.jfpdazey.pathoflowestcost;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.junit.Assert.*;

public class GridVisitorTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeConstructedWithoutAGrid() {
        GridVisitor subject = new GridVisitor(null);
    }

    @Test
    public void visitPathsForRowAccumulatesCostAcrossEntireRow() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathsForRow(1).get(0);

        assertThat(result.getTotalCost(), equalTo(15));
    }

    @Test
    public void visitPathsForRowDoesNotAccumumlateAnyCostIfFirstVisitExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST + 1, 1, 1, 0, 0 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathsForRow(1).get(0);

        assertThat(result.getTotalCost(), equalTo(0));
    }

    @Test
    public void visitPathsForRowDoesNotAccumulateCostAfterTotalCostExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST, 1, 1, 1, 1 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathsForRow(1).get(0);

        assertThat(result.getTotalCost(), equalTo(50));
    }

    @Test
    public void visitPathsForRowTraversesEntireRow() {
        Grid grid = new Grid(new int[][]{ { 0, 0, 0, 0, 0 } });
        GridVisitor subject = new GridVisitor(grid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 1, 1, 1, 1 })
        );

        PathState result = subject.visitPathsForRow(1).get(0);

        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void visitPathsForRowDoesNotTraverseAnyRowsIfFirstVisitExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST + 1, 1, 1, 0, 0 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathsForRow(1).get(0);

        assertThat(result.getRowsTraversed().size(), equalTo(0));
    }

    @Test
    public void visitPathsForRowDoesNotTraverseRowsAfterTotalCostExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST, 1, 1, 1, 1 } });
        GridVisitor subject = new GridVisitor(grid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1 })
        );

        PathState result = subject.visitPathsForRow(1).get(0);

        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void visitPathsForRowIsSuccessfulIfGridIsCompletelyTraversed() {
        Grid grid = new Grid(new int[][]{ { 1, 1, 1, 1, PathState.MAXIMUM_COST - 4 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathsForRow(1).get(0);

        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void visitPathsForRowIsNotSuccessfulIfGridIsNotTraversedAtAll() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST + 1, 0, 0, 0, 0 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathsForRow(1).get(0);

        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void visitPathsForRowIsNotSuccessfulIfGridIsPartiallyTraversed() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST, 1, 1, 1, 1 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathsForRow(1).get(0);

        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void visitPathsForRowIsNotSuccessfulIfLastVisitCausesTotalCostToExceedMaximumCost() {
        Grid grid = new Grid(new int[][]{ { 0, 0, 0, 0, PathState.MAXIMUM_COST + 1 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathsForRow(1).get(0);

        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void visitPathsForRowHandlesNegativeNumbers() {
        Grid grid = new Grid(new int[][]{ { -5, -5, -5, -5, -5 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathsForRow(1).get(0);

        assertThat(result.getTotalCost(), equalTo(-25));
        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void visitPathsForRowVisitsOtherRowsInGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 5, 6, 7, 8, 9 }, { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(twoRowGrid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 2, 2, 2, 2, 2 })
        );

        PathState result = subject.visitPathsForRow(2).get(0);

        assertThat(result.getTotalCost(), equalTo(15));
        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void visitPathsForRowHandlesMaximumCostForOtherRowsInGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { PathState.MAXIMUM_COST - 1, 2, 1, 1, 1 } });
        GridVisitor subject = new GridVisitor(twoRowGrid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 2 })
        );

        PathState result = subject.visitPathsForRow(2).get(0);

        assertThat(result.getTotalCost(), equalTo(49));
        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void visitPathsForRowReturnsAllPathsFromThatRowThroughFullTwoRowGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, 2, 1, 1, 1 } });
        GridVisitor subject = new GridVisitor(twoRowGrid);

        List<PathState> results = subject.visitPathsForRow(1);

        assertThat(results.size(), equalTo(16));
    }

    @Test
    public void visitPathsForRowReturnsFewerPathsThroughGridWithHighCosts() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, PathState.MAXIMUM_COST, 1, 1, 1 } });
        GridVisitor subject = new GridVisitor(twoRowGrid);

        List<PathState> results = subject.visitPathsForRow(1);

        assertThat(results.size(), equalTo(9));
    }

    @Test
    public void visitPathsForRowReturnsPathsInAscendingCostOrder() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 1, 1, 1, 1 }, { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(twoRowGrid);

        List<PathState> results = subject.visitPathsForRow(1);

        int priorCost = results.get(0).getTotalCost();
        for (int i = 1; i < results.size(); i++) {
            assertThat(priorCost, lessThanOrEqualTo(results.get(i).getTotalCost()));
        }

        assertThat(results.get(0).getTotalCost(), equalTo(5));
        assertThat(results.get(15).getTotalCost(), equalTo(15));
    }

    @Test
    public void visitPathsForRowReturnsAllPathsFromThatRowThroughFullThreeRowGrid() {
        Grid threeRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, 2, 2, 2, 2 }, { 0, 3, 3, 3, 3 } });
        GridVisitor subject = new GridVisitor(threeRowGrid);

        List<PathState> results = subject.visitPathsForRow(1);

        assertThat(results.size(), equalTo(81));
        assertThat(results.get(0).getTotalCost(), equalTo(9));
    }
}
