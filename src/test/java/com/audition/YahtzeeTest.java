package com.audition;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

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
    public void test_areCategoriesAvailable_default() {

        assertTrue(yahtzee.areCategoriesAvailable());
    }

    @Test
    public void test_areCategoriesAvailable_scorecardHasItems() {

        yahtzee.scorecard.put(Yahtzee.Category.CHANCE, 5);
        yahtzee.scorecard.put(Yahtzee.Category.YAHTZEE, 50);

        assertTrue(yahtzee.areCategoriesAvailable());
    }

    @Test
    public void test_areCategoriesAvailable_scorecardFilled() {

        yahtzee.scorecard.put(Yahtzee.Category.ONES, 0);
        yahtzee.scorecard.put(Yahtzee.Category.TWOS, 0);
        yahtzee.scorecard.put(Yahtzee.Category.THREES, 0);
        yahtzee.scorecard.put(Yahtzee.Category.FOURS, 0);
        yahtzee.scorecard.put(Yahtzee.Category.FIVES, 0);
        yahtzee.scorecard.put(Yahtzee.Category.SIXES, 0);
        yahtzee.scorecard.put(Yahtzee.Category.PAIR, 0);
        yahtzee.scorecard.put(Yahtzee.Category.THREE_KIND, 0);
        yahtzee.scorecard.put(Yahtzee.Category.FOUR_KIND, 0);
        yahtzee.scorecard.put(Yahtzee.Category.SMALL_STRAIGHT, 0);
        yahtzee.scorecard.put(Yahtzee.Category.LARGE_STRAIGHT, 0);
        yahtzee.scorecard.put(Yahtzee.Category.FULL_HOUSE, 0);
        yahtzee.scorecard.put(Yahtzee.Category.CHANCE, 0);
        yahtzee.scorecard.put(Yahtzee.Category.YAHTZEE, 0);

        assertFalse(yahtzee.areCategoriesAvailable());
    }

    @Test
    public void test_isCategoryAvailable_notAvailable() {

        yahtzee.scorecard.put(Yahtzee.Category.CHANCE, 0);

        assertFalse(yahtzee.isCategoryAvailable(Yahtzee.Category.CHANCE));
    }

    @Test
    public void test_isCategoryAvailable_isAvailable() {

        yahtzee.scorecard.put(Yahtzee.Category.CHANCE, 0);

        assertTrue(yahtzee.isCategoryAvailable(Yahtzee.Category.ONES));
    }

    @Test
    public void test_getDieCharacter_allCharacters() {

        char one = '\u2680';
        char two = '\u2681';
        char three = '\u2682';
        char four = '\u2683';
        char five = '\u2684';
        char six = '\u2685';

        assertEquals(one, yahtzee.getDieCharacter(1));
        assertEquals(two, yahtzee.getDieCharacter(2));
        assertEquals(three, yahtzee.getDieCharacter(3));
        assertEquals(four, yahtzee.getDieCharacter(4));
        assertEquals(five, yahtzee.getDieCharacter(5));
        assertEquals(six, yahtzee.getDieCharacter(6));
    }

    @Test
    public void test_getCategoryScore_ones() {

        assertEquals(4, yahtzee.getSumOfValue(new int[]{1, 1, 1, 1, 5}, 1));
    }

    @Test
    public void test_getCategoryScore_twos() {

        assertEquals(2, yahtzee.getSumOfValue(new int[]{2, 1, 1, 1, 5}, 2));
    }

    @Test
    public void test_getCategoryScore_threes() {

        assertEquals(6, yahtzee.getSumOfValue(new int[]{2, 1, 3, 3, 5}, 3));
    }

    @Test
    public void test_getCategoryScore_fours() {

        assertEquals(20, yahtzee.getSumOfValue(new int[]{4, 4, 4, 4, 4}, 4));
    }

    @Test
    public void test_getCategoryScore_fives() {

        assertEquals(10, yahtzee.getSumOfValue(new int[]{4, 4, 5, 4, 5}, 5));
    }

    @Test
    public void test_getCategoryScore_sixes() {

        assertEquals(6, yahtzee.getSumOfValue(new int[]{6, 4, 5, 4, 5}, 6));
    }

    @Test
    public void test_getChanceScore() {

        assertEquals(21, yahtzee.getChanceScore(new int[]{6, 2, 3, 6, 4}));
    }

    @Test
    public void test_getRolls_OneRoll() {

        assertEquals(1, yahtzee.getRolls());
    }

    @Test
    public void test_getRolls_twoRolls() {

        yahtzee.rollAllDice();
        assertEquals(2, yahtzee.getRolls());
    }

    @Test
    public void test_getRolls_threeRolls() {

        yahtzee.rollAllDice();
        yahtzee.rollAllDice();
        assertEquals(3, yahtzee.getRolls());
    }

    @Test
    public void test_getRolls_fourRolls() {

        // Unlikely but for testing

        yahtzee.rollAllDice();
        yahtzee.rollAllDice();
        yahtzee.rollAllDice();
        assertEquals(4, yahtzee.getRolls());
    }

    @Test
    public void test_getScore_ones() {

        yahtzee.dice = new int[] {1, 1, 1, 4, 5};
        assertEquals(3, yahtzee.determineScore(Yahtzee.Category.ONES));
    }


    @Test
    public void test_getScore_twos() {

        yahtzee.dice = new int[] {1, 2, 2, 4, 5};
        assertEquals(4, yahtzee.determineScore(Yahtzee.Category.TWOS));
    }

    @Test
    public void test_getScore_threes() {

        yahtzee.dice = new int[] {1, 3, 3, 3, 3};
        assertEquals(12, yahtzee.determineScore(Yahtzee.Category.THREES));
    }

    @Test
    public void test_getScore_fours() {

        yahtzee.dice = new int[] {4, 2, 4, 4, 4};
        assertEquals(16, yahtzee.determineScore(Yahtzee.Category.FOURS));
    }

    @Test
    public void test_getScore_fives() {

        yahtzee.dice = new int[] {5, 5, 5, 5, 5};
        assertEquals(25, yahtzee.determineScore(Yahtzee.Category.FIVES));
    }

    @Test
    public void test_getScore_sixes() {

        yahtzee.dice = new int[]{6, 2, 3, 4, 5};
        assertEquals(6, yahtzee.determineScore(Yahtzee.Category.SIXES));
    }

    @Test
    public void test_getScore_pair_noPair() {

        yahtzee.dice = new int[] {1, 3, 2, 4, 5};
        assertEquals(0, yahtzee.determineScore(Yahtzee.Category.PAIR));
    }

    @Test
    public void test_getScore_pair_onePair() {

        yahtzee.dice = new int[] {1, 1, 2, 4, 5};
        assertEquals(2, yahtzee.determineScore(Yahtzee.Category.PAIR));
    }

    @Test
    public void test_getScore_pair_three_countOnePair() {

        yahtzee.dice = new int[] {1, 1, 1, 2, 5};
        assertEquals(2, yahtzee.determineScore(Yahtzee.Category.PAIR));
    }

    @Test
    public void test_getScore_pair_four_countTwoPair() {

        yahtzee.dice = new int[] {1, 1, 1, 1, 5};
        assertEquals(4, yahtzee.determineScore(Yahtzee.Category.PAIR));
    }

    @Test
    public void test_getScore_pair_five_countTwoPair() {

        yahtzee.dice = new int[]{1, 1, 1, 1, 1};
        assertEquals(4, yahtzee.determineScore(Yahtzee.Category.PAIR));
    }

    @Test
    public void test_getScore_pair_fullHouse_countTwoPair() {

        yahtzee.dice = new int[]{1, 1, 2, 2, 1};
        assertEquals(6, yahtzee.determineScore(Yahtzee.Category.PAIR));
    }

    @Test
    public void test_getScore_pair_differingPairs_countTwoPair() {

        yahtzee.dice = new int[] {2, 2, 5, 5, 5};
        assertEquals(14, yahtzee.determineScore(Yahtzee.Category.PAIR));
    }

    @Test
    public void test_getScore_threeKind_one_noMatch() {

        yahtzee.dice = new int[] {1, 3, 2, 4, 5};
        assertEquals(0, yahtzee.determineScore(Yahtzee.Category.THREE_KIND));
    }

    @Test
    public void test_getScore_threeKind_two_noMatch() {

        yahtzee.dice = new int[] {1, 2, 2, 4, 5};
        assertEquals(0, yahtzee.determineScore(Yahtzee.Category.THREE_KIND));
    }

    @Test
    public void test_getScore_threeKind_three_countOneMatch() {

        yahtzee.dice = new int[] {1, 2, 2, 2, 5};
        assertEquals(6, yahtzee.determineScore(Yahtzee.Category.THREE_KIND));
    }

    @Test
    public void test_getScore_threeKind_four_countOneMatch() {

        yahtzee.dice = new int[] {1, 2, 2, 2, 2};
        assertEquals(6, yahtzee.determineScore(Yahtzee.Category.THREE_KIND));
    }

    @Test
    public void test_getScore_threeKind_five_countOneMatch() {

        yahtzee.dice = new int[]{2, 2, 2, 2, 2};
        assertEquals(6, yahtzee.determineScore(Yahtzee.Category.THREE_KIND));
    }

    @Test
    public void test_getScore_fourKind_one_noMatch() {

        yahtzee.dice = new int[] {1, 3, 2, 4, 5};
        assertEquals(0, yahtzee.determineScore(Yahtzee.Category.FOUR_KIND));
    }

    @Test
    public void test_getScore_fourKind_two_noMatch() {

        yahtzee.dice = new int[] {1, 3, 3, 4, 5};
        assertEquals(0, yahtzee.determineScore(Yahtzee.Category.FOUR_KIND));
    }

    @Test
    public void test_getScore_fourKind_three_noMatch() {

        yahtzee.dice = new int[] {1, 3, 3, 3, 5};
        assertEquals(0, yahtzee.determineScore(Yahtzee.Category.FOUR_KIND));
    }

    @Test
    public void test_getScore_fourKind_four_countOneMatch() {

        yahtzee.dice = new int[] {3, 2, 3, 3, 3};
        assertEquals(12, yahtzee.determineScore(Yahtzee.Category.FOUR_KIND));
    }

    @Test
    public void test_getScore_fourKind_five_countOneMatch() {

        yahtzee.dice = new int[]{3, 3, 3, 3, 3};
        assertEquals(12, yahtzee.determineScore(Yahtzee.Category.FOUR_KIND));
    }

    @Test
    public void test_getScore_fullHouse_one_noMatch() {

        yahtzee.dice = new int[] {1, 3, 2, 4, 5};
        assertEquals(0, yahtzee.determineScore(Yahtzee.Category.FULL_HOUSE));
    }

    @Test
    public void test_getScore_fullHouse_two_noMatch() {

        yahtzee.dice = new int[] {1, 3, 3, 4, 5};
        assertEquals(0, yahtzee.determineScore(Yahtzee.Category.FULL_HOUSE));
    }

    @Test
    public void test_getScore_fullHouse_three_noMatch() {

        yahtzee.dice = new int[] {1, 3, 3, 3, 5};
        assertEquals(0, yahtzee.determineScore(Yahtzee.Category.FULL_HOUSE));
    }

    @Test
    public void test_getScore_fullHouse_four_noMatch() {

        yahtzee.dice = new int[] {3, 2, 3, 3, 3};
        assertEquals(0, yahtzee.determineScore(Yahtzee.Category.FULL_HOUSE));
    }

    @Test
    public void test_getScore_fullHouse_five_noMatch() {

        yahtzee.dice = new int[] {3, 3, 3, 3, 3};
        assertEquals(0, yahtzee.determineScore(Yahtzee.Category.FULL_HOUSE));
    }

    @Test
    public void test_getScore_fullHouse_countOneMatch() {

        yahtzee.dice = new int[] {3, 2, 2, 3, 3};
        assertEquals(13, yahtzee.determineScore(Yahtzee.Category.FULL_HOUSE));
    }

    @Test
    public void test_getScore_smallStraight_match() {

        yahtzee.dice = new int[] {1, 3, 2, 4, 5};
        assertEquals(15, yahtzee.determineScore(Yahtzee.Category.SMALL_STRAIGHT));
    }

    @Test
    public void test_getScore_smallStraight_noMatch() {

        yahtzee.dice = new int[] {2, 5, 4, 6, 3};
        assertEquals(0, yahtzee.determineScore(Yahtzee.Category.SMALL_STRAIGHT));
    }

    @Test
    public void test_getScore_largeStraight_match() {

        yahtzee.dice = new int[] {2, 5, 4, 6, 3};
        assertEquals(20, yahtzee.determineScore(Yahtzee.Category.LARGE_STRAIGHT));
    }

    @Test
    public void test_getScore_largeStraight_noMatch() {

        yahtzee.dice = new int[] {1, 3, 2, 4, 5};
        assertEquals(0, yahtzee.determineScore(Yahtzee.Category.LARGE_STRAIGHT));
    }

    @Test
    public void test_getScore_chance() {

        yahtzee.dice = new int[] {2, 3, 5, 4, 5};
        assertEquals(19, yahtzee.determineScore(Yahtzee.Category.CHANCE));
    }

    @Test
    public void test_setScore() {

        yahtzee.setScore(Yahtzee.Category.THREE_KIND, 15);

        assertEquals(15, yahtzee.getScore(Yahtzee.Category.THREE_KIND).intValue());
    }

    @Test
    public void test_swapDice() {

        int[] newDice = yahtzee.dice.clone();
        int temp = newDice[4];
        newDice[4] = newDice[1];
        newDice[1] = temp;

        yahtzee.swapDice(4, 1);

        assertEquals(Arrays.toString(newDice), Arrays.toString(yahtzee.dice));
    }

    @Test
    public void test_resetRolls() {

        yahtzee.keeps = 1;
        yahtzee.rolls = 1;

        yahtzee.resetRolls();

        assertEquals(0, yahtzee.keeps);
        assertEquals(0, yahtzee.rolls);
    }

    @Test
    public void test_getTotalScore_noCategory() {

        assertEquals(0, yahtzee.getTotalScore());
    }

    @Test
    public void test_getTotalScore_withCategory() {

        yahtzee.setScore(Yahtzee.Category.LARGE_STRAIGHT, 20);
        yahtzee.setScore(Yahtzee.Category.FULL_HOUSE, 8);

        assertEquals(28, yahtzee.getTotalScore());
    }

    @Test
    public void test_getAllDice() {

        assertNotNull(yahtzee.getDiceList());
    }

    @Test
    public void test_getAvailableCategories() {

        assertNotNull(yahtzee.getCategoriesList());
    }
}