package com.audition;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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

        assertNotSame(newDice, yahtzee.dice);
        assertArrayEquals(newDice, yahtzee.dice);
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

    @Test
    public void test_start_happyPath_allRerolls() throws InterruptedException{

        String input = "1\n\n2\n\n\n3\n\n\n4\n\n\n5\n\n\n6\n\n\n7\n\n\n8\n\n\n9\n\n\n10\n\n\n11\n\n\n12\n\n\n13\n\n\n14\n\n\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        yahtzee.start(0);

        System.setIn(System.in);

        assertEquals(14, yahtzee.scorecard.size());
    }

    @Test
    public void test_start_happyPath_endPrematurely_smallx() throws InterruptedException{

        String input = "1\n\n2\n\n\nx";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        yahtzee.start(0);

        System.setIn(System.in);

        assertEquals(2, yahtzee.scorecard.size());
    }

    @Test
    public void test_start_happyPath_endPrematurely_capitalX() throws InterruptedException{

        String input = "1\n\n2\n\n\nX";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        yahtzee.start(0);

        System.setIn(System.in);

        assertEquals(2, yahtzee.scorecard.size());
    }

    @Test
    public void test_start_happyPath_rerolls_endHalfway() throws InterruptedException{

        String input = "7\n\n\n8\n\n\n9\n\n\n10\n\n\n1\n\n\n3\n\n\n13\n\n\n14\n\n\nx";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        yahtzee.start(0);

        System.setIn(System.in);

        assertEquals(8, yahtzee.scorecard.size());
    }

    @Test
    public void test_start_happyPath_endDuringReroll_smallx() throws InterruptedException{

        String input = "1\n\n2\n\nx";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        yahtzee.start(0);

        System.setIn(System.in);

        assertEquals(1, yahtzee.scorecard.size());
    }

    @Test
    public void test_start_happyPath_endDuringReroll_capitalX() throws InterruptedException{

        String input = "1\n\n2\n\nX";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        yahtzee.start(0);

        System.setIn(System.in);

        assertEquals(1, yahtzee.scorecard.size());
    }

    @Test
    public void test_start_selectAlreadySelected() throws InterruptedException{

        String input = "1\n\n1\nx";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        yahtzee.start(0);

        System.setIn(System.in);

        assertEquals(1, yahtzee.scorecard.size());
    }

    @Test
    public void test_start_allEnters() throws InterruptedException{

        String input = "\n\n\n\n\n\n\n\n\n\nx";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        yahtzee.start(0);

        System.setIn(System.in);

        assertEquals(0, yahtzee.scorecard.size());
    }



    @Test
    public void test_start_keepDice_allDice() throws InterruptedException{

        String input = "1\n1\r2\r3\r4\r5\rx";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        yahtzee.start(0);

        System.setIn(System.in);

        assertEquals(1, yahtzee.scorecard.size());
    }

    @Test
    public void test_start_keepDice_justOneAndEnd() throws InterruptedException{

        String input = "1\n1\nx";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        yahtzee.start(0);

        System.setIn(System.in);

        assertEquals(0, yahtzee.scorecard.size());
    }

    @Test
    public void test_start_keepDice_alreadyKeeping() throws InterruptedException{

        String input = "1\n2\n1\nx";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        yahtzee.start(0);

        System.setIn(System.in);

        assertEquals(0, yahtzee.scorecard.size());
    }

    @Test
    public void test_start_keepDice_invalidSelection() throws InterruptedException{

        String input = "a\n-1\n1\n12\nx";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        yahtzee.start(0);

        System.setIn(System.in);

        assertEquals(0, yahtzee.scorecard.size());
    }

    @Test
    public void test_start_invalidSelections() throws InterruptedException{

        String input = "a\n1\nasdf\nx";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        yahtzee.start(0);

        System.setIn(System.in);

        assertEquals(0, yahtzee.scorecard.size());
    }

    @Test
    public void test_start_happyPath_forCoverage() {

        // For more coverage. Test takes approximately 27 seconds due to Thread.sleep().
        String input = "1\n\n2\n\n\n3\n\n\n4\n\n\n5\n\n\n6\n\n\n7\n\n\n8\n\n\n9\n\n\n10\n\n\n11\n\n\n12\n\n\n13\n\n\n14\n\n\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        yahtzee.start();

        System.setIn(System.in);

        assertEquals(14, yahtzee.scorecard.size());
    }
}