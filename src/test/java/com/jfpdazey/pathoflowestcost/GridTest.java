package com.jfpdazey.pathoflowestcost;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class GridTest {

    @Test(expected = IllegalArgumentException.class)
    public void aGridRequiresAtLeastOneRow() {
        int values[][] = new int[0][0];
        Grid subject = new Grid(values);
    }

    @Test(expected = IllegalArgumentException.class)
    public void aGridRequiresAtLeastFiveColumns() {
        int values[][] = new int[1][4];
        Grid subject = new Grid(values);
    }

    @Test
    public void retrievesValueForRowAndColumn() {
        int values[][] = new int[][]{ { 1, 3, 5, 7, 9 }, { 2, 4, 6, 8, 10 } };
        Grid subject = new Grid(values);

        assertThat(subject.getValueForRowAndColumn(2, 1), equalTo(2));
        assertThat(subject.getValueForRowAndColumn(1, 5), equalTo(9));
    }

    @Test
    public void returnsColumnCount() {
        Grid fiveColumnGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        Grid sevenColumnGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5, 6, 7 } });

        assertThat(fiveColumnGrid.getColumnCount(), equalTo(5));
        assertThat(sevenColumnGrid.getColumnCount(), equalTo(7));
    }
}