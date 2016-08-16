package com.jfpdazey.pathoflowestcost;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class PathStateCollectorTest {

    private PathStateCollector collector;

    @Before
    public void setUp() {
        collector = new PathStateCollector();
    }

    @Test
    public void collectorReturnsNullIfNoPathStatesHaveBeenAdded() {
        assertThat(collector.getBestPath(), is(nullValue()));
    }

    @Test
    public void collectorReturnsPathAddedIfOnlyOnePathAdded() {
        PathState expectedPath = new PathState(5);
        expectedPath.addRowWithCost(1, 5);

        collector.addPath(expectedPath);

        assertThat(collector.getBestPath(), equalTo(expectedPath));
    }

    @Test
    public void collectorReturnsLongerPathOfTwoPathsAdded() {
        PathState longerPath = new PathState(5);
        longerPath.addRowWithCost(1, 5);
        longerPath.addRowWithCost(1, 5);
        PathState shorterPath = new PathState(5);
        shorterPath.addRowWithCost(1, 10);

        collector.addPath(longerPath);
        collector.addPath(shorterPath);

        assertThat(collector.getBestPath(), equalTo(longerPath));
    }

    @Test
    public void collectorReturnsLowerCostPathOfTwoEqualLengthPathsAdded() {
        PathState lowCostPath = new PathState(5);
        lowCostPath.addRowWithCost(1, 5);
        lowCostPath.addRowWithCost(1, 5);
        PathState highCostPath = new PathState(5);
        highCostPath.addRowWithCost(1, 10);
        highCostPath.addRowWithCost(1, 20);

        collector.addPath(lowCostPath);
        collector.addPath(highCostPath);

        assertThat(collector.getBestPath(), equalTo(lowCostPath));
    }

    @Test
    public void collectorPrefersExistingPathIfPathsAreEqual() {
        PathState firstPath = new PathState(5);
        firstPath.addRowWithCost(1, 5);
        firstPath.addRowWithCost(1, 5);
        PathState secondPath = new PathState(5);
        secondPath.addRowWithCost(1, 5);
        secondPath.addRowWithCost(1, 5);

        collector.addPath(firstPath);
        collector.addPath(secondPath);

        assertThat(collector.getBestPath(), equalTo(firstPath));
    }
}