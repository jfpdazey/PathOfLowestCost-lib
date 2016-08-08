package com.jfpdazey.pathoflowestcost;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GridIntegrationTest {

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

        List<PathState> results = visitor.visitPathsForAllRows();

        PathState solution = results.get(0);
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

        List<PathState> results = visitor.visitPathsForAllRows();

        PathState solution = results.get(0);
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

        List<PathState> results = visitor.visitPathsForAllRows();

        PathState solution = results.get(0);
        assertThat(solution.isSuccessful(), is(false));
        assertThat(solution.getTotalCost(), equalTo(48));
        assertThat(solution.getRowsTraversed(), equalTo(expectedPath));
    }
}