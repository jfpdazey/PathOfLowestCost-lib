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
    public void getBestPathForAllRowsReturnsLongerPathsAheadOfShorterPathsWithLowerCost() {
        Grid twoRowGrid = new Grid(new int[][]{
                { 2, 1, PathState.MAXIMUM_COST, PathState.MAXIMUM_COST, 1 },
                { 1, 10, 10, PathState.MAXIMUM_COST, 5 } });
        GridVisitor subject = new GridVisitor(twoRowGrid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 2, 1, 2 })
        );

        PathState result = subject.getBestPathForGrid();

        assertThat(result.getTotalCost(), equalTo(12));
        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void findsPathThroughExampleOne() {
        Grid grid = new Grid(new int[][]{
                { 3, 4, 1, 2, 8, 6 },
                { 6, 1, 8, 2, 7, 4 },
                { 5, 9, 3, 9, 9, 5 },
                { 8, 4, 1, 3, 2, 6 },
                { 3, 7, 2, 8, 6, 4 }
        });
        GridVisitor visitor = new GridVisitor(grid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 2, 3, 4, 4, 5 })
        );

        PathState solution = visitor.getBestPathForGrid();
        assertThat(solution.isSuccessful(), is(true));
        assertThat(solution.getTotalCost(), equalTo(16));
        assertThat(solution.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void findsPathThroughExampleTwo() {
        Grid grid = new Grid(new int[][]{
                { 3, 4, 1, 2, 8, 6 },
                { 6, 1, 8, 2, 7, 4 },
                { 5, 9, 3, 9, 9, 5 },
                { 8, 4, 1, 3, 2, 6 },
                { 3, 7, 2, 1, 2, 3 }
        });
        GridVisitor visitor = new GridVisitor(grid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 2, 1, 5, 4, 5 })
        );

        PathState solution = visitor.getBestPathForGrid();
        assertThat(solution.isSuccessful(), is(true));
        assertThat(solution.getTotalCost(), equalTo(11));
        assertThat(solution.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void findsIncompletePathThroughExampleThree() {
        Grid grid = new Grid(new int[][]{
                { 19, 10, 19, 10, 19 },
                { 21, 23, 20, 19, 12 },
                { 20, 12, 20, 11, 10 }
        });
        GridVisitor visitor = new GridVisitor(grid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 1, 1 })
        );

        PathState solution = visitor.getBestPathForGrid();
        assertThat(solution.isSuccessful(), is(false));
        assertThat(solution.getTotalCost(), equalTo(48));
        assertThat(solution.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void findsPathThroughMaximumGrid() {
        int[][] gridArray = new int[10][100];
        for (int row = 0; row < gridArray.length; row++) {
            for (int column = 0; column < gridArray[0].length; column++) {
                if (row == 5 && column % 2 == 0) {
                    gridArray[row][column] = 1;
                } else if (row == 5) {
                    gridArray[row][column] = 0;
                } else {
                    gridArray[row][column] = 25;
                }
            }
        }
        Integer[] expectedPathArray = new Integer[100];
        for (int row = 0; row < expectedPathArray.length; row++) {
            expectedPathArray[row] = 6;
        }

        Grid grid = new Grid(gridArray);
        GridVisitor visitor = new GridVisitor(grid);
        List<Integer> expectedPath = new ArrayList<Integer>(Arrays.asList(expectedPathArray));

        PathState solution = visitor.getBestPathForGrid();
        assertThat(solution.isSuccessful(), is(true));
        assertThat(solution.getTotalCost(), equalTo(50));
        assertThat(solution.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void findsPathThroughGridGeneratingManyPaths() throws Exception {
        int[][] gridArray = new int[][] {
                { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5 },
                { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };

        Grid grid = new Grid(gridArray);
        GridVisitor visitor = new GridVisitor(grid);

        PathState solution = visitor.getBestPathForGrid();
        assertThat(solution.isSuccessful(), is(true));
        assertThat(solution.getTotalCost(), equalTo(1));
    }
}
