package com.audition;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Yahtzee {

    protected enum Category {
        ONES(1),
        TWOS(2),
        THREES(3),
        FOURS(4),
        FIVES(5),
        SIXES(6),
        PAIR(2),
        THREE_KIND(3),
        FOUR_KIND(4),
        SMALL_STRAIGHT(0),
        LARGE_STRAIGHT(0),
        FULL_HOUSE(0),
        YAHTZEE(0),
        CHANCE(0);

        public final int value;

        Category(int value) {

            this.value = value;
        }
    }


    protected Map<Category, Integer> scorecard;

    protected int[] dice;

    protected int keeps;

    protected int rolls;

    public Yahtzee() {

        scorecard = new HashMap<>(Category.values().length);
        dice = new int[5];
        rolls = 0;
        keeps = 0;
    }

    public boolean areCategoriesAvailable() {

        return scorecard.size() < Category.values().length;
    }

    public boolean isCategoryAvailable(Category category) {

        return scorecard.get(category) == null;
    }

    public char getDieCharacter(int value) {

        switch (value) {
            case 1:
                return '\u2680';
            case 2:
                return '\u2681';
            case 3:
                return '\u2682';
            case 4:
                return '\u2683';
            case 5:
                return '\u2684';
            case 6:
                return '\u2685';
        }

        return '\0';
    }

    public int getCategoryScore(int[] dice, int toFind) {

        return Arrays.stream(dice).filter(i -> i == toFind).sum();
    }

    public int getChanceScore(int[] diceRolls) {

        return Arrays.stream(diceRolls).sum();
    }

    public int getRolls() {

        return rolls;
    }

    public int getScore(Category category) {

        int score = 0;

        switch (category) {

            case ONES:
            case TWOS:
            case THREES:
            case FOURS:
            case FIVES:
            case SIXES:
                score = getCategoryScore(dice, category.value);
                break;
            case PAIR:
            case THREE_KIND:
            case FOUR_KIND:
            case FULL_HOUSE:
            case YAHTZEE:
                Map<Integer, Integer> map =
                    Arrays.stream(dice).boxed().collect(Collectors.groupingBy(e -> e, Collectors.reducing(0, e -> 1, Integer::sum)));

                if (category == Category.FULL_HOUSE) {
                    if (map.size() == 2 && map.containsValue(3) && map.containsValue(2)) {
                        score = getScoreOfAKind(map, 3);

                        // Remove the three of a kind match so the pair search won't double count.
                        map.entrySet().removeIf(e -> e.getValue() == 3);

                        score += getScoreOfAKind(map, 2);
                    }
                } else if (category == Category.YAHTZEE) {
                    score = map.size() == 1 ? 50 : 0;
                } else {
                    score = getScoreOfAKind(map, category.value);
                }
                break;
            case SMALL_STRAIGHT:
                if (Arrays.stream(dice).distinct().count() == 5) {
                    score = getSmallStraightScore(dice);
                }
                break;
            case LARGE_STRAIGHT:
                if (Arrays.stream(dice).distinct().count() == 5) {
                    score = getLargeStraightScore(dice);
                }
                break;
            case CHANCE:
                score = getChanceScore(dice);
                break;
            default:
                break;

        }

        return score;
    }

    public int getTotalScore() {

        return scorecard.values().stream().mapToInt(Integer::intValue).sum();
    }

    public Integer getCategoryScore(Category category) {

        Integer score = scorecard.get(category);

        return score == null ? 0 : score;
    }

    public String getCategoriesList() {

        Category[] categories = Category.values();

        return IntStream.range(0, categories.length).mapToObj(
            i -> (isCategoryAvailable(categories[i]) ? (i + 1) : "") + "\t- " + categories[i] + generateDashes(categories[i].name(), 16)
                + "| Score: " + getCategoryScore(categories[i])).collect(Collectors.joining("\n"));
    }

    public String getDiceList() {

        return IntStream.range(0, 5).mapToObj(i -> (i > keeps - 1 ? (i + 1) : " ") + " : " + getDieStringFromIndex(i))
                        .collect(Collectors.joining("\n"));
    }

    public String getDieStringFromIndex(int index) {

        return getDieCharacter(dice[index]) + " (" + dice[index] + ")";
    }

    public void incrementKeeps() {

        keeps++;
    }

    public void resetRolls() {

        rolls = 0;
        keeps = 0;
    }

    public void rollAllDice() {

        rollDice(5);
    }

    public void rollDice(int numOfDice) {

        rolls++;
        int[] temp = new Random().ints(numOfDice, 1, 7).toArray();
        int index = dice.length - numOfDice;

        for (int i = 0; i < temp.length; i++) {
            dice[index++] = temp[i];
        }
    }

    public void setScore(Category category, int score) {

        scorecard.put(category, score);
    }

    public void swapDice(int from, int to) {

        if (from != to) {
            int temp = dice[from];
            dice[from] = dice[to];
            dice[to] = temp;
        }
        incrementKeeps();
    }

    private int getScoreOfAKind(Map<Integer, Integer> map, int kind) {

        return map.entrySet().stream().filter(e -> e.getValue() >= kind).mapToInt(e -> e.getKey() * kind * (e.getValue() / kind)).sum();
    }

    private int getSmallStraightScore(int[] diceRolls) {

        Integer sum = getChanceScore(diceRolls);

        return sum == 15 ? sum : 0;
    }

    private int getLargeStraightScore(int[] diceRolls) {

        Integer sum = getChanceScore(diceRolls);

        return sum == 20 ? sum : 0;
    }

    private String generateDashes(String s, int maxSpaces) {

        int reps = maxSpaces - s.length();

        return " " + IntStream.range(0, reps).mapToObj(i -> "-").collect(Collectors.joining());
    }

    public static void main(String[] args) throws NumberFormatException, InterruptedException {

        Yahtzee yahtzee = new Yahtzee();
        String response;

        Scanner scanner = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println("                YAHTZEE!");
        System.out.println("Input 'X' at any time to end application. ");
        System.out.println("=========================================");

        while (yahtzee.areCategoriesAvailable()) {
            System.out.println("\nCATEGORIES:\n" + yahtzee.getCategoriesList());
            System.out.println("\nTOTAL SCORE: " + yahtzee.getTotalScore());
            System.out.print("\nPlease select category: ");
            response = scanner.nextLine();

            if (response.trim().equalsIgnoreCase("x")) {
                return;
            }
            if (response.isBlank()) {
                continue;
            }

            int selection;

            try {
                selection = Integer.parseInt(response);
            } catch (NumberFormatException e) {
                System.err.println("Invalid selection\n");
                continue;
            }
            Category[] categories = Category.values();

            // Error checking category selection
            if (selection < 1 || selection > categories.length) {
                System.err.println("Invalid selection\n");
                continue;
            }
            Category category = categories[selection - 1];

            if (yahtzee.isCategoryAvailable(category)) {

                yahtzee.rollAllDice();
                int swapIndex = 0;
                while (yahtzee.getRolls() < 3) {

                    Thread.sleep(250);

                    while (swapIndex < 5) {
                        System.out
                            .println("\nRoll#" + yahtzee.getRolls() + ":" + " (Category: " + category + ")\n" + yahtzee.getDiceList());
                        System.out.print("\nKeep which dice (" + (swapIndex + 1) + "-5) [Enter to re-roll]: ");
                        response = scanner.nextLine();

                        if (response.trim().equalsIgnoreCase("x")) {
                            return;
                        } else if (response.equals("")) {
                            System.out.println("\nRe-rolling " + (5 - swapIndex) + ((5 - swapIndex) == 1 ? " die" : " dice") + "..");
                            Thread.sleep(250);
                            break;
                        } else {
                            try {
                                selection = Integer.parseInt(response);
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid selection\n");
                                continue;
                            }

                            if (selection < 1 || selection > 5) {
                                System.err.println("Invalid selection\n");
                                continue;
                            } else {
                                // swapping dice for kept die
                                if (selection - 1 == swapIndex) {
                                    yahtzee.incrementKeeps();
                                    swapIndex++;
                                } else if (selection - 1 > swapIndex && selection <= 5) {
                                    yahtzee.swapDice(swapIndex, selection - 1);
                                    swapIndex++;
                                } else {
                                    // Invalid selection
                                    System.err.println(
                                        "Already keeping = " + selection + " : " + yahtzee.getDieStringFromIndex(selection - 1) + "\n");
                                    continue;
                                }
                            }
                        }
                    }

                    int rerollCount = 5 - swapIndex;
                    if (rerollCount == 0) {
                        // exit the loop. We're keeping all dice.
                        break;
                    }

                    yahtzee.rollDice(rerollCount);
                }

                // Get the score!
                System.out.println("Final Roll:\n" + yahtzee.getDiceList());

                int score = yahtzee.getScore(category);
                yahtzee.setScore(category, score);
                System.out.println("Score for " + category + " category: " + score);
                Thread.sleep(1000);
                yahtzee.resetRolls();

            } else {
                System.err.println("Invalid selection: " + selection + ". Category already has a score.\n");
                continue;
            }
        }

        // GAME IS DONE!
        System.out.println(yahtzee.getCategoriesList());
        System.out.println("===================================================");
        System.out.println("CONGRATULATIONS! YOU HAVE FINISHED PLAYING YAHTZEE!");
        System.out.println("               Your final score is");
        System.out.println("                          " + yahtzee.getTotalScore());
        System.out.println("===================================================");

    }
}
