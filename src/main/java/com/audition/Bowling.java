package com.audition;

import java.util.Arrays;

/**
 * Code kata for Bowling
 *
 * @author andrewmadrazo
 */
public class Bowling {

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

                if (frame < 9) {

                    switch (i) {

                        case 0:
                            if (score[frame] == 10) {

                                strikeCount++;
                                if (strike) {
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

                                if (strikeCount > 2) {
                                    // Chicken!
                                    score[frame - 2] += roll;
                                }
                            }
                            break;
                        case 1:
                            if (score[frame] == 10) {

                                if (strike) {
                                    strike = false;
                                    strikeCount = 0;
                                    score[frame - 1] += roll;
                                }
                                spare = true;
                            } else if (strike) {
                                score[frame - 1] += roll;
                                strike = false;

                            }
                            break;
                        default:
                            break;

                    }
                } else {
                    // Last frame logic!
                    switch (i) {

                        case 0:
                            if (roll == 10) {

                                strikeCount++;
                                if (strike) {
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

                                if (strikeCount > 2) {
                                    // Chicken!
                                    score[frame - 2] += roll;
                                }
                            }
                            score[frame] += roll;
                            break;
                        case 1:
                            if (roll == 10) {

                                if (strike) {
                                    strike = false;
                                    strikeCount = 0;
                                    score[frame - 1] += roll;
                                }
                                spare = true;
                            } else if (strike) {
                                score[frame - 1] += roll;
                                spare = false;
                                strike = false;
                                strikeCount = 0;
                            }
                            score[frame] += roll;
                            break;
                        case 2:
                            // Last roll
                            if (roll == 10) {
                                if (strike || spare) {
                                    score[frame - 1] += roll;
                                }
                                if (strike) {
                                    strikeCount++;
                                    if (strikeCount > 2) {
                                        // Turkey!
                                        score[frame - 2] += roll;
                                    }
                                }
                            }
                            break;
                        default:
                            break;

                    }
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

        String[] frames = game.split(",");
        int frameCount = frames.length;
        int index = 0;

        parsedRolls = new int[frameCount][];

        for (String frame : frames) {

            String[] rolls = frame.split(" ");
            int[] parsedRoll = new int[rolls.length];

            switch (rolls.length) {

                case 3:
                    parsedRoll[2] = Integer.parseInt(rolls[2]);
                case 2:
                    parsedRoll[1] = Integer.parseInt(rolls[1]);
                case 1:
                    parsedRoll[0] = Integer.parseInt(rolls[0]);
                default:
                    parsedRolls[index++] = parsedRoll;
                    break;
            }
        }

        return parsedRolls;
    }
}
