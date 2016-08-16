package com.jfpdazey.pathoflowestcost;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RowVisitorTest {

    private PathStateCollector realCollector;
    private PathStateCollector mockCollector;
    @Before
    public void setUp() {
        realCollector = new PathStateCollector();
        mockCollector = mock(PathStateCollector.class);

    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeConstructedWithoutAGrid() {
        RowVisitor subject = new RowVisitor(1, null, new PathStateCollector());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeConstructedWithoutACollector() {
        RowVisitor subject = new RowVisitor(1, new Grid(new int[][]{ { 1, 2, 3, 4, 5 } }), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeConstructedWithRowNumberLessThanGridRows() {
        RowVisitor subject = new RowVisitor(0, new Grid(new int[][]{ { 1, 2, 3, 4, 5 } }), new PathStateCollector());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeConstructedWithRowNumberGreaterThanGridRows() {
        RowVisitor subject = new RowVisitor(2, new Grid(new int[][]{ { 1, 2, 3, 4, 5 } }), new PathStateCollector());
    }

    @Test
    public void getBestPathForRowAccumulatesCostAcrossEntireRow() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        RowVisitor subject = new RowVisitor(1, grid, realCollector);

        PathState result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(15));
    }

    @Test
    public void getBestPathForRowDoesNotAccumumlateAnyCostIfFirstVisitExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST + 1, 1, 1, 0, 0 } });
        RowVisitor subject = new RowVisitor(1, grid, realCollector);

        PathState result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(0));
    }

    @Test
    public void getBestPathForRowDoesNotAccumulateCostAfterTotalCostExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST, 1, 1, 1, 1 } });
        RowVisitor subject = new RowVisitor(1, grid, realCollector);

        PathState result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(50));
    }

    @Test
    public void getBestPathForRowTraversesEntireRow() {
        Grid grid = new Grid(new int[][]{ { 0, 0, 0, 0, 0 } });
        RowVisitor subject = new RowVisitor(1, grid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 1, 1, 1, 1 })
        );

        PathState result = subject.getBestPathForRow();

        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void getBestPathForRowDoesNotTraverseAnyRowsIfFirstVisitExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST + 1, 1, 1, 0, 0 } });
        RowVisitor subject = new RowVisitor(1, grid, realCollector);

        PathState result = subject.getBestPathForRow();

        assertThat(result.getRowsTraversed().size(), equalTo(0));
    }

    @Test
    public void getBestPathForRowDoesNotTraverseRowsAfterTotalCostExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST, 1, 1, 1, 1 } });
        RowVisitor subject = new RowVisitor(1, grid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1 })
        );

        PathState result = subject.getBestPathForRow();

        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void getBestPathForRowIsSuccessfulIfGridIsCompletelyTraversed() {
        Grid grid = new Grid(new int[][]{ { 1, 1, 1, 1, PathState.MAXIMUM_COST - 4 } });
        RowVisitor subject = new RowVisitor(1, grid, realCollector);

        PathState result = subject.getBestPathForRow();

        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void getBestPathForRowIsNotSuccessfulIfGridIsNotTraversedAtAll() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST + 1, 0, 0, 0, 0 } });
        RowVisitor subject = new RowVisitor(1, grid, realCollector);

        PathState result = subject.getBestPathForRow();

        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void getBestPathForRowIsNotSuccessfulIfGridIsPartiallyTraversed() {
        Grid grid = new Grid(new int[][]{ { PathState.MAXIMUM_COST, 1, 1, 1, 1 } });
        RowVisitor subject = new RowVisitor(1, grid, realCollector);

        PathState result = subject.getBestPathForRow();

        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void getBestPathForRowIsNotSuccessfulIfLastVisitCausesTotalCostToExceedMaximumCost() {
        Grid grid = new Grid(new int[][]{ { 0, 0, 0, 0, PathState.MAXIMUM_COST + 1 } });
        RowVisitor subject = new RowVisitor(1, grid, realCollector);

        PathState result = subject.getBestPathForRow();

        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void getBestPathForRowHandlesNegativeNumbers() {
        Grid grid = new Grid(new int[][]{ { -5, -5, -5, -5, -5 } });
        RowVisitor subject = new RowVisitor(1, grid, realCollector);

        PathState result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(-25));
        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void getBestPathForRowVisitsOtherRowsInGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 5, 6, 7, 8, 9 }, { 1, 2, 3, 4, 5 } });
        RowVisitor subject = new RowVisitor(2, twoRowGrid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 2, 2, 2, 2, 2 })
        );

        PathState result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(15));
        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void getBestPathForRowHandlesMaximumCostForOtherRowsInGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { PathState.MAXIMUM_COST - 1, 2, 1, 1, 1 } });
        RowVisitor subject = new RowVisitor(2, twoRowGrid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 2 })
        );

        PathState result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(49));
        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void getBestPathForRowVisitsAllPathsFromThatRowThroughFullTwoRowGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, 2, 1, 1, 1 } });
        RowVisitor subject = new RowVisitor(1, twoRowGrid, mockCollector);

        subject.getBestPathForRow();

        verify(mockCollector, times(16)).addPath(any(PathState.class));
    }

    @Test
    public void getBestPathForRowVisitsFewerPathsThroughGridWithHighCosts() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, PathState.MAXIMUM_COST, 1, 1, 1 } });
        RowVisitor subject = new RowVisitor(1, twoRowGrid, mockCollector);

        subject.getBestPathForRow();

        verify(mockCollector, times(9)).addPath(any(PathState.class));
    }

    @Test
    public void getBestPathForRowReturnsLongerPathsAheadOfShorterPathsWithLowerCost() {
        Grid twoRowGrid = new Grid(new int[][]{
                { 1, 1, PathState.MAXIMUM_COST, PathState.MAXIMUM_COST, 1 },
                { 1, 10, 10, PathState.MAXIMUM_COST, 5 } });
        RowVisitor subject = new RowVisitor(1, twoRowGrid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 1, 2 })
        );

        PathState result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(12));
        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void getBestPathForRowVisitsAllPathsFromThatRowThroughFullThreeRowGrid() {
        Grid threeRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, 2, 2, 2, 2 }, { 0, 3, 3, 3, 3 } });
        RowVisitor subject = new RowVisitor(1, threeRowGrid, mockCollector);

        subject.getBestPathForRow();

        verify(mockCollector, times(81)).addPath(any(PathState.class));
    }

    @Test
    public void getBestPathForRowCanWrapToFourthRowInFullFourRowGrid() {
        Grid fourRowGrid = new Grid(new int[][]{ { 1, 5, 5, 5, 5 }, { 0, 2, 2, 2, 2 }, { 0, 3, 3, 3, 3 }, { 0, 1, 1, 1, 1 } });
        RowVisitor subject = new RowVisitor(1, fourRowGrid, realCollector);

        PathState result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(5));
    }

    @Test
    public void getBestPathForRowVisitsAllPossiblePathsFromThatRowThroughFullFourRowGrid() {
        Grid fourRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, 2, 2, 2, 2 }, { 0, 3, 3, 3, 3 }, { 0, 4, 4, 4, 4 } });
        RowVisitor subject = new RowVisitor(1, fourRowGrid, mockCollector);

        subject.getBestPathForRow();

        verify(mockCollector, times(81)).addPath(any(PathState.class));
    }
}
