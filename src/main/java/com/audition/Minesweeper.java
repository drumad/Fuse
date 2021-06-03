package com.audition;

import com.audition.util.FileReaderUtil;
import com.audition.util.GridUtil;

import java.io.IOException;

public class Minesweeper {

    public String sweepMines(char[][] cells) {

        if (!GridUtil.isValid(cells)) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();
        char c;
        int bomb;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {

                c = cells[i][j];

                if (c == '.') {
                    // Analyze its surroundings
                    // Check neighbor cells (clockwise)
                    bomb = GridUtil.countCharacterAroundCell('*', cells, i, j);
                    buffer.append(bomb);
                } else {
                    buffer.append(c);
                }
            }
            buffer.append("\n");
        }

        return buffer.toString().trim();
    }

    public void playMinesweeper() throws IOException {

        String input = FileReaderUtil.readInputFile();
        String[] split = input.split("[ \n]");
        char[][] cells;
        int fields = 0;
        int index = 0;
        int y;
        int x;

        while (true) {
            y = Integer.parseInt(split[index++]);
            x = Integer.parseInt(split[index++]);

            if (y == 0 || x == 0) {
                break;
            }

            cells = new char[y][];
            char[] row;

            for (int i = 0; i < y; i++) {
                cells[i] = new char[x];
                row = split[index++].toCharArray();

                // If a row in the text field has more than the expected characters, it should only take the /x/ number of rows.
                for (int j = 0; j < x; j++) {
                    cells[i][j] = row[j];
                }
            }

            String output = "Field #" + ++fields + ":\n" + y + " " + x + "\n" + sweepMines(cells);
            System.out.println(output + "\n");
        }
    }
}
