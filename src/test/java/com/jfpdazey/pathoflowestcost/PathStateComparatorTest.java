package com.jfpdazey.pathoflowestcost;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class PathStateComparatorTest {

    private PathStateComparator subject;
    private PathState lowerCostPath;
    private PathState higherCostPath;

    @Before
    public void setUp() {
        subject = new PathStateComparator();

        lowerCostPath = new PathState(1);
        lowerCostPath.addRowWithCost(1, 1);

        higherCostPath = new PathState(1);
        higherCostPath.addRowWithCost(1, 10);
    }

    @Test
    public void returnsNegativeOneIfFirstPathHasLowerCostThanSecond() {
        assertThat(subject.compare(lowerCostPath, higherCostPath), equalTo(-1));
    }

    @Test
    public void returnsNegativeOneIfLastPathIsNull() {
        assertThat(subject.compare(lowerCostPath, null), equalTo(-1));
    }

    @Test
    public void returnsZeroIfPathsHaveSameCost() {
        assertThat(subject.compare(lowerCostPath, lowerCostPath), equalTo(0));
    }

    @Test
    public void returnsZeroIfBothPathsAreNull() {
        assertThat(subject.compare(null, null), equalTo(0));
    }

    @Test
    public void returnsPositiveOneIfFirstPathHasLowerCostThanSecond() {
        assertThat(subject.compare(higherCostPath, lowerCostPath), equalTo(1));
    }

    @Test
    public void returnsPositiveOneIfFirstPathIsNull() {
        assertThat(subject.compare(null, lowerCostPath), equalTo(1));
    }
}