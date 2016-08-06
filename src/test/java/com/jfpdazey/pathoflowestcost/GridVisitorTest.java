package com.jfpdazey.pathoflowestcost;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class GridVisitorTest {

    @Test
    public void startsOffWithNoScore() {
        GridVisitor subject = new GridVisitor();
        assertThat(subject.score, equalTo(0));
    }

    @Test
    public void accumulatesScoreForOneRound() {
        GridVisitor subject = new GridVisitor();
        Grid grid = new Grid(new int[][]{{1, 2, 3, 4, 5}});

        subject.visit(grid);

        assertThat(subject.score, equalTo(1));
    }

    @Test
    public void accumulatesScoreForTwoRounds() {
        GridVisitor subject = new GridVisitor();
        Grid grid = new Grid(new int[][]{{1, 2, 3, 4, 5}});

        subject.visit(grid);
        subject.visit(grid);

        assertThat(subject.score, equalTo(3));
    }
}
