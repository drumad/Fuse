package com.audition;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class GameOfLifeTest {

    private GameOfLife gol;

    @Before
    public void setUp() {
        gol = new GameOfLife();
    }

    @Test
    public void test_gameOfLife_nullCells_returnEmpty() {

        String result = gol.gameOfLife(null);

        assertEquals("", result);
    }

    @Test
    public void test_gameOfLife_emptyCells_returnEmpty() {

        char[][] cells = new char[0][0];

        String result = gol.gameOfLife(cells);

        assertEquals("", result);
    }

    @Test
    public void test_gameOfLife_emptyYCells_returnEmpty() {

        char[][] cells = new char[1][0];

        String result = gol.gameOfLife(cells);

        assertEquals("", result);
    }

    @Test
    public void test_gameOfLife_controlTest_returnExpected() {

        char[][] cells = {
            {'.','.','.','.','.','.','.','.'},
            {'.','.','.','.','*','.','.','.'},
            {'.','.','.','*','*','.','.','.'},
            {'.','.','.','.','.','.','.','.'}
        };

        String expected =
            "........\n" +
            "...**...\n" +
            "...**...\n" +
            "........";

        String result = gol.gameOfLife(cells);

        assertEquals(expected, result);
    }

    @Test
    public void test_gameOfLife_2x2_fourDeadCells_returnDead() {

        char[][] cells = {
            {'.','.',},
            {'.','.',}
        };

        String expected =
            "..\n" +
            "..";

        String result = gol.gameOfLife(cells);

        assertEquals(expected, result);
    }

    @Test
    public void test_gameOfLife_2x2_oneLiveCell_returnDead() {

        char[][] cells = {
            {'*','.',},
            {'.','.',}
        };

        String expected =
            "..\n" +
            "..";

        String result = gol.gameOfLife(cells);

        assertEquals(expected, result);
    }

    @Test
    public void test_gameOfLife_2x2_twoLiveCells_returnDead() {

        char[][] cells = {
            {'*','.',},
            {'.','*',}
        };

        String expected =
            "..\n" +
            "..";

        String result = gol.gameOfLife(cells);

        assertEquals(expected, result);
    }

    @Test
    public void test_gameOfLife_2x2_fourLiveCells_returnLive() {

        char[][] cells = {
            {'*','*',},
            {'*','*',}
        };

        String expected =
            "**\n" +
            "**";

        String result = gol.gameOfLife(cells);

        assertEquals(expected, result);
    }

    @Test
    public void test_gameOfLife_2x2_threeLiveCells_returnLive() {

        char[][] cells = {
            {'*','*',},
            {'*','.',}
        };

        String expected =
            "**\n" +
            "**";

        String result = gol.gameOfLife(cells);

        assertEquals(expected, result);
    }

    @Test
    public void test_gameOfLife_4x4_cornerDeaths() {

        char[][] cells = {
            {'*','.','.','*'},
            {'.','.','.','.'},
            {'.','.','.','.'},
            {'*','.','.','*'}
        };

        String expected =
            "....\n" +
            "....\n" +
            "....\n" +
            "....";

        String result = gol.gameOfLife(cells);

        assertEquals(expected, result);
    }

    @Test
    public void test_gameOfLife_4x4_cornerLives() {

        char[][] cells = {
            {'.','*','*','.'},
            {'*','*','*','*'},
            {'*','*','*','*'},
            {'.','*','*','.'}
        };

        String expected =
            "*..*\n" +
            "....\n" +
            "....\n" +
            "*..*";

        String result = gol.gameOfLife(cells);

        assertEquals(expected, result);
    }

    @Test
    public void test_playGameOfLife() throws IOException {

        String result = gol.playGameOfLife();

        String expected = "Generation 2:\n" +
            "4 8\n" +
            "**....**\n" +
            "..*..*..\n" +
            "..*..*..\n" +
            "**....**";

        assertEquals(expected, result);
    }
}