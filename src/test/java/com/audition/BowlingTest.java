package com.audition;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class BowlingTest {

    private Bowling bowling;

    @Before
    public void setUp() {

        bowling = new Bowling();
    }

    @Test
    public void test_parseRolls_twoRollsEachFrame() {

        String game = "11 11 11 11 11 11 11 11 11 11";
        int[][] expected = {{1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}};
        int[][] rolls = bowling.parseRolls(game);

        assertArrayEquals(expected, rolls);
    }

    @Test
    public void test_parseRolls_goodPlayer() {

        String game = "6/ 13 27 72 36 X -/ 81 26 X X 5";
        int[][] expected = {{6, 4}, {1, 3}, {2, 7}, {7, 2}, {3, 6}, {10}, {0, 10}, {8, 1}, {2, 6}, {10, 10, 5}};
        int[][] rolls = bowling.parseRolls(game);

        assertArrayEquals(expected, rolls);
    }

    @Test
    public void test_parseRolls_perfectGame() {

        String game = "X X X X X X X X X X X X";
        int[][] expected = {{10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {10}, {10, 10, 10}};
        int[][] rolls = bowling.parseRolls(game);

        assertArrayEquals(expected, rolls);
    }

    @Test
    public void test_parseRolls_spareInLastRoll() {

        String game = "9/ X 5/ X X 2/ 8/ 1/ X X 5/";
        int[][] expected = {{9,1}, {10}, {5,5}, {10}, {10}, {2,8}, {8,2}, {1,9}, {10}, {10, 5, 5}};
        int[][] rolls = bowling.parseRolls(game);

        assertArrayEquals(expected, rolls);
    }

    @Test
    public void test_getScore_allOnes() {

        String game = "11 11 11 11 11 11 11 11 11 11";

        int[][] rolls = bowling.parseRolls(game);

        assertEquals(20, bowling.getScore(rolls));
    }

    @Test
    public void test_getScore_allSpares() {

        String game = "5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/5";

        int[][] rolls = bowling.parseRolls(game);

        assertEquals(150, bowling.getScore(rolls));
    }

    @Test
    public void test_getScore_perfectGame() {

        String game = "X X X X X X X X X X X X";

        int[][] rolls = bowling.parseRolls(game);

        assertEquals(300, bowling.getScore(rolls));
    }

    @Test
    public void test_getScore_imperfectGame() {

        String game = "6/ 13 27 72 36 X -/ 81 26 X X 5";

        int[][] rolls = bowling.parseRolls(game);

        assertEquals(122, bowling.getScore(rolls));
    }

    @Test
    public void test_getScore_9thFrameStrike() {

        String game = "6/ 13 27 72 36 X -/ 81 X X X 5";

        int[][] rolls = bowling.parseRolls(game);

        assertEquals(144, bowling.getScore(rolls));
    }

    @Test
    public void test_getScore_9thFrameSpare() {

        String game = "6/ 13 27 72 36 X -/ 81 4/ X X 5";

        int[][] rolls = bowling.parseRolls(game);

        assertEquals(134, bowling.getScore(rolls));
    }

    @Test
    public void test_getScore_spareInLastRoll() {

        String game = "9/ X 5/ X X 2/ 8/ 1/ X X 5/";

        int[][] rolls = bowling.parseRolls(game);

        assertEquals(196, bowling.getScore(rolls));
    }

    @Test
    public void test_getScore_all9s() {

        String game = "9- 9- 9- 9- 9- 9- 9- 9- 9- 9-";

        int [][] rolls = bowling.parseRolls(game);

        assertEquals(90, bowling.getScore(rolls));
    }

    @Test
    public void test_getScore_missSpare() {

        String game = "-/ -/ -/ -/ -/ -/ -/ -/ -/ -/-";

        int [][] rolls = bowling.parseRolls(game);

        assertEquals(100, bowling.getScore(rolls));
    }

    @Test
    public void test_getScore_chickenMidGame() {

        String game = "9- 52 -8 8/ X X 2/ 81 5- 45";

        int[][] rolls = bowling.parseRolls(game);

        assertEquals(127, bowling.getScore(rolls));
    }

    @Test
    public void test_getScore_9thFrameSpare_10thFrameTurkey() {

        String game = "6/ 13 27 72 36 X -/ 81 4/ X X X";

        int[][] rolls = bowling.parseRolls(game);

        assertEquals(139, bowling.getScore(rolls));
    }

    @Test
    public void test_bowl() throws IOException {

        // Open the file and
        bowling.bowl();
    }
}