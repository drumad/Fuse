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

        while (true) {
            cells = GridUtil.convertToGrid(split, index);

            if (cells == null) {
                break;
            }

            index += cells.length + 2;



            String output = "Field #" + ++fields + ":\n" + cells.length + " " + cells[0].length + "\n" + sweepMines(cells);
            System.out.println(output + "\n");
        }
    }
}
