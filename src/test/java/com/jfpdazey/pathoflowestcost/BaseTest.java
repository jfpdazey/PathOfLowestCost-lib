package com.jfpdazey.pathoflowestcost;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class BaseTest {

    @Test
    public void baseReturnsBase() {
        assertThat(new Base().base(), equalTo("Base"));
    }
}