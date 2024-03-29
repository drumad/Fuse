package com.audition;

import com.audition.util.FileReaderUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Code kata for Bowling
 *
 * @author andrewmadrazo
 */
public class Bowling {

    private Map<Character, Integer> scoreMap;

    public Bowling() {

        scoreMap = new HashMap<>();

        scoreMap.put('-', 0);
        scoreMap.put('1', 1);
        scoreMap.put('2', 2);
        scoreMap.put('3', 3);
        scoreMap.put('4', 4);
        scoreMap.put('5', 5);
        scoreMap.put('6', 6);
        scoreMap.put('7', 7);
        scoreMap.put('8', 8);
        scoreMap.put('9', 9);
        scoreMap.put('X', 10);
        scoreMap.put('/', 10);
    }

    /**
     * Calculates the score of a game.
     *
     * @param rolls The array of rolls to calculate.
     * @return The int value of the total score.
     */
    public int getScore(int[][] rolls) {

        boolean spare = false;
        boolean strike = false;

        int strikeCount = 0;
        int[] score = new int[10];

        for (int frame = 0; frame < rolls.length; frame++) {

            score[frame] = 0;

            for (int i = 0; i < rolls[frame].length; i++) {

                int roll = rolls[frame][i];
                score[frame] += roll;

                switch (i) {

                    case 0:
                        if (roll == 10) {

                            strikeCount++;
                            if (strike || spare) {
                                score[frame - 1] += roll;
                                if (strikeCount > 2) {
                                    // Turkey!
                                    score[frame - 2] += roll;
                                }
                            }
                            strike = true;
                        } else if (strike || spare) {

                            spare = false;
                            score[frame - 1] += roll;

                            if (strikeCount >= 2) {
                                // Chicken!
                                score[frame - 2] += roll;
                            }
                        }
                        break;
                    case 1:
                        if (score[frame] == 10) {

                            if (strike) {
                                strike = false;
                                if (frame < 9 || strikeCount > 2) {
                                    // If turkey or not the last,
                                    // add score to the previous frame.
                                    score[frame - 1] += roll;
                                }
                                strikeCount = 0;
                            }
                            spare = true;
                        } else if (roll == 10) {

                            if (strike) {
                                strikeCount++;
                                if (strikeCount > 2) {
                                    // Turkey!
                                    if (frame == 9) {
                                        score[frame - 1] += roll;
                                    }
                                }
                            }

                        } else if (strike) {

                            score[frame - 1] += roll;
                            strike = false;
                            strikeCount = 0;
                        }
                        break;
                    default:
                        break;

                }
            }
        }

        return Arrays.stream(score).sum();
    }

    /**
     * Converts a game from the file into a double int array.
     *
     * @param game A game of bowling.
     * @return double int array to be scored.
     */
    public int[][] parseRolls(String game) {

        int[][] parsedRolls;

        String[] frames = game.toUpperCase().split(" ");
        int framesCount;
        if (frames.length > 10) {
            // There was a strike/spare on the last frame.
            frames[9] += frames[10] + (frames.length == 12 ? frames[11] : "");
            framesCount = 10;
        } else {
            framesCount = frames.length;
        }
        int index = 0;

        parsedRolls = new int[framesCount][];

        for (int i = 0; i < framesCount; i++) {

            String frame = frames[i].trim();
            int pins;
            boolean spare = false;

            int[] parsedRoll = new int[frame.length()];
            char c;

            switch (frame.length()) {

                case 3:
                    // Last frame!
                    c = frame.charAt(2);
                    pins = scoreMap.get(c);
                    if (pins == 10 && c == '/') {
                        spare = true;
                    } else {
                        parsedRoll[2] = pins;
                    }
                case 2:
                    c = frame.charAt(1);
                    pins = scoreMap.get(c);
                    if (spare) {
                        parsedRoll[2] = 10 - pins;
                        spare = false;
                    } else if (pins == 10 && c == '/') {
                        spare = true;
                    }
                    parsedRoll[1] = pins;
                case 1:
                    pins = scoreMap.get(frame.charAt(0));
                    if (spare) {
                        parsedRoll[1] = 10 - pins;
                    }
                    parsedRoll[0] = pins;
                default:
                    parsedRolls[index++] = parsedRoll;
                    break;
            }
        }

        return parsedRolls;
    }

    public void bowl() throws IOException {

        String input = FileReaderUtil.readInputFile();
        String[] games = input.split("\n");

        for (String game : games) {

            int score = getScore(parseRolls(game));
            System.out.println(game + " = " + score);
        }
    }
}
