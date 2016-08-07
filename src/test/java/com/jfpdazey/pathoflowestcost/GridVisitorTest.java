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
    public void visitPathForRowAccumulatesCostAcrossEntireRow() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathForRow(1);

        assertThat(result.getTotalCost(), equalTo(15));
    }

    @Test
    public void visitPathForRowDoesNotAccumumlateAnyCostIfFirstVisitExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST + 1, 1, 1, 0, 0 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathForRow(1);

        assertThat(result.getTotalCost(), equalTo(0));
    }

    @Test
    public void visitPathForRowDoNotAccumulateCostAfterTotalCostExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST, 1, 1, 1, 1 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathForRow(1);

        assertThat(result.getTotalCost(), equalTo(50));
    }

    @Test
    public void visitPathForRowTraversesEntireRow() {
        Grid grid = new Grid(new int[][]{ { 0, 0, 0, 0, 0 } });
        GridVisitor subject = new GridVisitor(grid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 1, 1, 1, 1 })
        );

        PathState result = subject.visitPathForRow(1);

        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void visitPathForRowDoesNotTraverseAnyRowsIfFirstVisitExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST + 1, 1, 1, 0, 0 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathForRow(1);

        assertThat(result.getRowsTraversed().size(), equalTo(0));
    }

    @Test
    public void visitPathForRowDoesNotTraverseRowsAfterTotalCostExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST, 1, 1, 1, 1 } });
        GridVisitor subject = new GridVisitor(grid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1 })
        );

        PathState result = subject.visitPathForRow(1);

        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void visitPathForRowIsSuccessfulIfGridIsCompletelyTraversed() {
        Grid grid = new Grid(new int[][]{ { 1, 1, 1, 1, PathState.MAXIMUM_COST - 4 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathForRow(1);

        assertThat(result.successful, is(true));
    }

    @Test
    public void visitPathForRowIsNotSuccessfulIfGridIsNotTraversedAtAll() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST + 1, 0, 0, 0, 0 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathForRow(1);

        assertThat(result.successful, is(false));
    }

    @Test
    public void visitPathForRowIsNotSuccessfulIfGridIsPartiallyTraversed() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST, 1, 1, 1, 1 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathForRow(1);

        assertThat(result.successful, is(false));
    }

    @Test
    public void visitPathForRowIsNotSuccessfulIfLastVisitCausesTotalCostToExceedMaximumCost() {
        Grid grid = new Grid(new int[][]{ { 0, 0, 0, 0, PathState.MAXIMUM_COST + 1 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathForRow(1);

        assertThat(result.successful, is(false));
    }

    @Test
    public void visitPathForRowHandlesNegativeNumbers() {
        Grid grid = new Grid(new int[][]{ { -5, -5, -5, -5, -5 } });
        GridVisitor subject = new GridVisitor(grid);

        PathState result = subject.visitPathForRow(1);

        assertThat(result.getTotalCost(), equalTo(-25));
        assertThat(result.successful, is(true));
    }

    @Test
    public void visitPathForRowVisitsOtherRowsInGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 5, 5, 5, 5, 5 }, { 1, 2, 3, 4, 5 } });
        GridVisitor subject = new GridVisitor(twoRowGrid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 2, 2, 2, 2, 2 })
        );

        PathState result = subject.visitPathForRow(2);

        assertThat(result.getTotalCost(), equalTo(15));
        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
        assertThat(result.successful, is(true));
    }

    @Test
    public void visitPathForRowHandlesMaximumCostForOtherRowsInGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { PathState.MAXIMUM_COST - 1, 2, 1, 1, 1 } });
        GridVisitor subject = new GridVisitor(twoRowGrid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 2 })
        );

        PathState result = subject.visitPathForRow(2);

        assertThat(result.getTotalCost(), equalTo(49));
        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
        assertThat(result.successful, is(false));
    }
}
