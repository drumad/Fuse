package com.audition;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MinesweeperTest {

    Minesweeper sweeper;

    @Before
    public void setUp() {

        sweeper = new Minesweeper();
    }

    @Test
    public void playMineSweeper() throws IOException {

        sweeper.playMinesweeper();
    }

    @Test
    public void sweepMines_nullCells_returnEmpty() {

        String actual = sweeper.sweepMines(null);

        assertEquals("", actual);
    }

    @Test
    public void sweepMines_emptyCells_returnEmpty() {

        String actual = sweeper.sweepMines(new char[0][0]);

        assertEquals("", actual);
    }

    @Test
    public void sweepMines_emptyXCells_returnEmpty() {

        String actual = sweeper.sweepMines(new char[1][0]);

        assertEquals("", actual);
    }

    @Test
    public void sweepMines_control1Test_returnExpected() {

        char[][] cells = {
            {'*','.','.','.'},
            {'.','.','.','.'},
            {'.','*','.','.'},
            {'.','.','.','.'}
        };

        String expected =
            "*100\n" +
            "2210\n" +
            "1*10\n" +
            "1110";

        String actual = sweeper.sweepMines(cells);

        assertEquals(expected, actual);
    }

    @Test
    public void sweepMines_control2Test_returnExpected() {

        char[][] cells = {
            {'*','*','.','.','.'},
            {'.','.','.','.','.'},
            {'.','*','.','.','.'}
        };

        String expected =
            "**100\n" +
            "33200\n" +
            "1*100";

        String actual = sweeper.sweepMines(cells);

        assertEquals(expected, actual);
    }

    @Test
    public void sweepMines_5x5_OneMineTest_returnExpected() {

        char[][] cells = {
            {'.','.','.','.','.'},
            {'.','.','.','.','.'},
            {'.','.','*','.','.'},
            {'.','.','.','.','.'},
            {'.','.','.','.','.'}
        };

        String expected =
            "00000\n" +
            "01110\n" +
            "01*10\n" +
            "01110\n" +
            "00000";

        String actual = sweeper.sweepMines(cells);

        assertEquals(expected, actual);
    }
}