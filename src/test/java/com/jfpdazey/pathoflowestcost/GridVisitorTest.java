package com.jfpdazey.pathoflowestcost;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class GridVisitorTest {

    @Test
    public void startsOffWithNoScore() {
        GridVisitor subject = new GridVisitor();
        assertThat(subject.getScore(), equalTo(0));
    }

    @Test
    public void accumulatesScoreForOneRound() {
        GridVisitor subject = new GridVisitor();
        Grid grid = new Grid(new int[][]{{1, 2, 3, 4, 5}});

        subject.visit(grid);

        assertThat(subject.getScore(), equalTo(1));
    }

    @Test
    public void accumulatesScoreForTwoRounds() {
        GridVisitor subject = new GridVisitor();
        Grid grid = new Grid(new int[][]{{1, 2, 3, 4, 5}});

        subject.visit(grid);
        subject.visit(grid);

        assertThat(subject.getScore(), equalTo(3));
    }

    @Test
    public void accumulatesScoreAcrossEntireRow() {
        GridVisitor subject = new GridVisitor();
        Grid grid = new Grid(new int[][]{{1, 2, 3, 4, 5}});

        subject.visit(grid);
        subject.visit(grid);
        subject.visit(grid);
        subject.visit(grid);
        subject.visit(grid);

        assertThat(subject.getScore(), equalTo(15));
    }
}
