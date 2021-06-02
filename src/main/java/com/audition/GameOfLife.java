package com.audition;

import com.audition.util.FileReaderUtil;

import java.io.IOException;

public class GameOfLife {

    /**
     * Analyzes the cells
     * @param cells
     * @return
     */
    public String gameOfLife(char[][] cells) {

        if (cells == null) {
            return "";
        }

        if (cells.length == 0) {
            return "";
        }

        if (cells[0].length == 0) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {

                int live = 0;

                char c = cells[i][j];

                // Check neighbor cells
                if (isAlive(cells, i-1, j)) {
                    live++;
                }
                if (isAlive(cells, i-1, j+1)) {
                    live++;
                }
                if (isAlive(cells, i, j+1)) {
                    live++;
                }
                if (isAlive(cells, i+1, j+1)) {
                    live++;
                }
                if (isAlive(cells, i+1, j)) {
                    live++;
                }
                if (isAlive(cells, i+1, j-1)) {
                    live++;
                }
                if (isAlive(cells, i, j-1)) {
                    live++;
                }
                if (isAlive(cells, i-1, j-1)) {
                    live++;
                }

                // Will the cell survive
                if (isAlive(cells, i, j)) {

                    if (live == 2 || live == 3) {
                        // still alive
                        buffer.append(c);
                    } else {
                        // dies
                        buffer.append('.');
                    }
                // Will the cell resurrect
                } else {

                    if (live == 3) {
                        // will live
                        buffer.append('*');
                    } else {
                        // stay dead
                        buffer.append(c);
                    }
                }
            }
            buffer.append('\n');
        }

        return buffer.toString().trim();
    }

    private boolean isAlive(char[][] cells, int i, int j) {

        if (i < 0 || i >= cells.length || j < 0 || j >= cells[i].length) {
            return false;
        }

        return cells[i][j] == '*';
    }

    /**
     * Starts the game of life. Reads through GameOfLife.txt.
     *
     * @return Returns the next generation after checking all the neighbors.
     * @throws IOException
     */
    public String playGameOfLife() throws IOException {

        String input = FileReaderUtil.readInputFile();

        System.out.println("Generation 1:");
        System.out.println(input);

        String[] split = input.split("[ \n]");
        char[][] cells;

        int y = Integer.parseInt(split[0]);
        int x = Integer.parseInt(split[1]);

        cells = new char[y][];
        char[] row;

        for (int i = 0; i < y; i++) {
            cells[i] = new char[x];
            row = split[i+2].toCharArray();

            // If a row in the text field has more than the expected characters, it should only take the /x/ number of rows.
            for (int j = 0; j < x; j++) {
                cells[i][j] = row[j];
            }
        }

        String nextgen = "Generation 2:\n" + y + " " + x + "\n" + gameOfLife(cells);
        System.out.println(nextgen);

        return nextgen;
    }
}
