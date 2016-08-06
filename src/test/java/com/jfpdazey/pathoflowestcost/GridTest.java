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
    public void retrievesValueForColumn() {
        int values[][] = new int[][]{ { 1, 3, 5, 7, 9 } };
        Grid subject = new Grid(values);

        assertThat(subject.getValueForColumn(1), equalTo(1));
        assertThat(subject.getValueForColumn(3), equalTo(5));
    }

    @Test
    public void returnsColumnCount() {
        Grid fiveColumnGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        Grid sevenColumnGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5, 6, 7 } });

        assertThat(fiveColumnGrid.getColumnCount(), equalTo(5));
        assertThat(sevenColumnGrid.getColumnCount(), equalTo(7));
    }
}