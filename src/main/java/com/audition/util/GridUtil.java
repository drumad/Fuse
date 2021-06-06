package com.audition.util;

public class GridUtil {

    public static int countCharacterAroundCell(char toFind, char[][] cells, int i, int j) {

        int counter = 0;

        counter += checkCharacter(toFind, cells, i-1, j) ? 1 : 0;
        counter += checkCharacter(toFind, cells, i-1, j+1) ? 1 : 0;
        counter += checkCharacter(toFind, cells, i, j+1) ? 1 : 0;
        counter += checkCharacter(toFind, cells, i+1, j+1) ? 1 : 0;
        counter += checkCharacter(toFind, cells, i+1, j) ? 1 : 0;
        counter += checkCharacter(toFind, cells, i+1, j-1) ? 1 : 0;
        counter += checkCharacter(toFind, cells, i, j-1) ? 1 : 0;
        counter += checkCharacter(toFind, cells, i-1, j-1) ? 1 : 0;

        return counter;
    }

    public static boolean checkCharacter(char toFind, char[][] cells, int i, int j) {

        if (i < 0 || i >= cells.length || j < 0 || j >= cells[i].length) {
            return false;
        }

        return cells[i][j] == toFind;
    }

    public static boolean isValid(char[][] cells) {

        if (cells == null) {
            return false;
        }

        if (cells.length == 0) {
            return false;
        }

        if (cells[0].length == 0) {
            return false;
        }

        return true;
    }

    public static char[][] convertToGrid(String[] split, int index) {

        int y = Integer.parseInt(split[index++]);
        int x = Integer.parseInt(split[index++]);

        if (y == 0 || x == 0) {
            return null;
        }

        char[][] cells = new char[y][];
        char[] row;

        for (int i = 0; i < y; i++) {
            cells[i] = new char[x];
            row = split[index++].toCharArray();

            // If a row in the text field has more than the expected characters,
            // it should only take the /x/ number of characters.
            for (int j = 0; j < x; j++) {
                cells[i][j] = row[j];
            }
        }

        return cells;
    }
}
