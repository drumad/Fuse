package com.audition;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class YahtzeeTest {

    private Yahtzee yahtzee;

    @Before
    public void setUp() {
        yahtzee = new Yahtzee();
        yahtzee.rollAllDice();
    }

    @Test
    public void test_getSumCategory() {

        assertEquals(4, yahtzee.getCategoryScore(new int[]{1, 1, 1, 1, 5}, 1));
    }

    @Test
    public void test_getAllDice() {

        System.out.println(yahtzee.getDiceList());
    }

    @Test
    public void test_getAvailableCategories() {

        System.out.println(yahtzee.getCategoriesList());
    }
}