package com.audition;

import com.audition.util.FileReaderUtil;
import com.audition.util.GridUtil;

import java.io.IOException;

public class GameOfLife {

    /**
     * Analyzes the cells
     *
     * @param cells
     * @return
     */
    public String gameOfLife(char[][] cells) {

        if (!GridUtil.isValid(cells)) {
            return "";
        }

        int live;
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {

                char c = cells[i][j];

                live = GridUtil.countCharacterAroundCell('*', cells, i, j);

                // Will the cell survive
                if (GridUtil.checkCharacter('*', cells, i, j)) {

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
        int index = 0;
        int generation = 1;
        String output = "";

        while (true) {
            cells = GridUtil.convertToGrid(split, index);

            if (cells == null) {
                break;
            }

            index += cells.length + 2;

            output = "Generation " + ++generation + ":\n" + cells.length + " " + cells[0].length + "\n" + gameOfLife(cells);
            System.out.println(output + "\n");
        }

        return output;
    }
}
