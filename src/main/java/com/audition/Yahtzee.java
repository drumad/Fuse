package com.audition;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The Yahtzee class for the code-kata.
 *
 * @author andrewmadrazo
 */
public class Yahtzee {

    protected enum Category {
        ONES(1, "Ones"),
        TWOS(2, "Twos"),
        THREES(3, "Threes"),
        FOURS(4, "Fours"),
        FIVES(5, "Fives"),
        SIXES(6, "Sixes"),
        PAIR(2, "Pair"),
        THREE_KIND(3, "Three of a Kind"),
        FOUR_KIND(4, "Four of a Kind"),
        SMALL_STRAIGHT(0, "Small Straight"),
        LARGE_STRAIGHT(0, "Large Straight"),
        FULL_HOUSE(0, "Full House"),
        YAHTZEE(0, "Yahtzee"),
        CHANCE(0, "Chance");

        public final int value;

        public final String formatted;

        Category(int value, String formatted) {

            this.value = value;
            this.formatted = formatted;
        }
    }


    protected Map<Category, Integer> scorecard;

    protected int[] dice;

    protected int keeps;

    protected int rolls;

    /**
     * Constructor. Initializes the scorecard, the dice, and sets the rolls and keeps to zero.
     */
    public Yahtzee() {

        scorecard = new HashMap<>(Category.values().length);
        dice = new int[5];
        rolls = 0;
        keeps = 0;
    }

    /**
     * Checks if any categories are available.
     *
     * @return True if any are available, false if none are available.
     */
    public boolean areCategoriesAvailable() {

        return scorecard.size() < Category.values().length;
    }

    /**
     * Checks if a category has no score yet.
     *
     * @param category The category key.
     * @return True if available, false if otherwise.
     */
    public boolean isCategoryAvailable(Category category) {

        return scorecard.get(category) == null;
    }

    /**
     * Gets the die character based on the specific value.
     *
     * @param value Values 1 to 6 only.
     * @return The special character for the die.
     */
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

    /**
     * Finds the specified int value and sums them up.
     *
     * @param dice   The dice roll.
     * @param toFind The value to match.
     * @return The int value of the sum.
     */
    public int getSumOfValue(int[] dice, int toFind) {

        return Arrays.stream(dice).filter(i -> i == toFind).sum();
    }

    /**
     * Sums up all the dice.
     *
     * @param diceRolls Any dice roll.
     * @return The int value of the sum.
     */
    public int getChanceScore(int[] diceRolls) {

        return Arrays.stream(diceRolls).sum();
    }

    /**
     * Gets the count of rolls.
     *
     * @return The int value of the rolls.
     */
    public int getRolls() {

        return rolls;
    }

    /**
     * Determines the score based on the current dice roll and the specified category,
     *
     * @param category The category to determine the score.
     * @return The int value of the score.
     */
    public int determineScore(Category category) {

        int score = 0;

        switch (category) {

            case ONES:
            case TWOS:
            case THREES:
            case FOURS:
            case FIVES:
            case SIXES:
                score = getSumOfValue(dice, category.value);
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

    /**
     * Gets the total score.
     *
     * @return The int value of the total score.
     */
    public int getTotalScore() {

        return scorecard.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Get the score of the specified category.
     *
     * @param category The category key.
     * @return The Integer value of the score.
     */
    public Integer getScore(Category category) {

        Integer score = scorecard.get(category);

        return score == null ? 0 : score;
    }

    /**
     * Lists all the categories. Also performs an evaluation if any of the categories have already been scored,
     * as well as the score. Proper spacing is also being followed for readability.
     *
     * @return String of the list of categories.
     */
    public String getCategoriesList() {

        Category[] categories = Category.values();

        return IntStream.range(0, categories.length).mapToObj(
            i -> (isCategoryAvailable(categories[i]) ? (i + 1) : "") + "\t- " + categories[i].formatted + generateDashes(
                categories[i].formatted, 16) + "| Score: " + getScore(categories[i])).collect(Collectors.joining("\n"));
    }

    /**
     * Lists the current dice. Also performs an evaluation if any of the dice are being kept.
     *
     * @return String of the list of current rolled dice.
     */
    public String getDiceList() {

        return IntStream.range(0, 5).mapToObj(i -> (i > keeps - 1 ? (i + 1) : " ") + " : " + getDieStringFromIndex(i))
                        .collect(Collectors.joining("\n"));
    }

    /**
     * For display purposes. This will return the die character and its value.
     *
     * @param index The index of the die.
     * @return A String of the die character and its value.
     */
    public String getDieStringFromIndex(int index) {

        return getDieCharacter(dice[index]) + " (" + dice[index] + ")";
    }

    /**
     * Increments the number of kept dice.
     */
    public void incrementKeeps() {

        keeps++;
    }

    /**
     * Resets the rolls and the keeps to 0.
     */
    public void resetRolls() {

        rolls = 0;
        keeps = 0;
    }

    /**
     * Roll all the dice at once. A convenience function.
     */
    public void rollAllDice() {

        rollDice(5);
    }

    /**
     * A number of dice that will be rolled. This method also increments the number of rolls.
     *
     * @param numOfDice The number of dice to roll.
     */
    public void rollDice(int numOfDice) {

        rolls++;
        int[] temp = new Random().ints(numOfDice, 1, 7).toArray();
        int index = dice.length - numOfDice;

        for (int die : temp) {
            dice[index++] = die;
        }
    }

    /**
     * Puts the category score into the map.
     *
     * @param category The category key.
     * @param score    The score value.
     */
    public void setScore(Category category, int score) {

        scorecard.put(category, score);
    }

    /**
     * Swap 2 dice.
     *
     * @param from The source index
     * @param to   The new index
     */
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

        int sum = getChanceScore(diceRolls);

        return sum == 15 ? sum : 0;
    }

    private int getLargeStraightScore(int[] diceRolls) {

        int sum = getChanceScore(diceRolls);

        return sum == 20 ? sum : 0;
    }

    /**
     * Generate a number of dashes based on the length of the string.
     *
     * @param s         The string to base the dashes from.
     * @param maxSpaces The maximum number of dashes
     * @return A string of dashes.
     */
    private String generateDashes(String s, int maxSpaces) {

        int reps = maxSpaces - s.length();

        return " " + IntStream.range(0, reps).mapToObj(i -> "-").collect(Collectors.joining());
    }

    /**
     * Entry point to play yahtzee. Default wait time is 250 milliseconds.
     */
    public void start() {

        try {
            this.start(250);
        } catch (InterruptedException e) {
            // Do nothing.
        }
    }

    /**
     * Can specify a wait time for user-friendliness when playing.
     *
     * @param waitTime The wait time. It will be used while re-rolling, and 4x its value when a category is finished.
     * @throws InterruptedException For the Thread.wait() calls.
     */
    protected void start(int waitTime) throws InterruptedException {

        String response;

        Scanner scanner = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println("                YAHTZEE!");
        System.out.println("Input 'X' at any time to end application. ");
        System.out.println("=========================================");

        while (this.areCategoriesAvailable()) {
            System.out.println("\nCATEGORIES:\n" + this.getCategoriesList());
            System.out.println("\nTOTAL SCORE: " + this.getTotalScore());
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
                System.err.println("Invalid selection: " + response + "\n");
                continue;
            }
            Category[] categories = Category.values();

            // Error checking category selection
            if (selection < 1 || selection > categories.length) {
                System.err.println("Invalid selection: " + response + "\n");
                continue;
            }
            Category category = categories[selection - 1];

            if (this.isCategoryAvailable(category)) {

                this.rollAllDice();
                int swapIndex = 0;
                while (this.getRolls() < 3) {

                    Thread.sleep(waitTime);

                    while (swapIndex < 5) {
                        System.out
                            .println("\nRoll#" + this.getRolls() + ":" + " (Category: " + category.formatted + ")\n" + this.getDiceList());
                        System.out.print("\nKeep which dice (" + (swapIndex + 1) + "-5) [Leave blank to re-roll]: ");
                        response = scanner.nextLine();

                        if (response.trim().equalsIgnoreCase("x")) {
                            return;
                        } else if (response.equals("")) {
                            System.out.println("\nRe-rolling " + (5 - swapIndex) + ((5 - swapIndex) == 1 ? " die" : " dice") + "..");
                            Thread.sleep(waitTime);
                            break;
                        } else {
                            try {
                                selection = Integer.parseInt(response);
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid selection: " + response + "\n");
                                continue;
                            }

                            if (selection < 1 || selection > 5) {
                                System.err.println("Invalid selection: " + response + "\n");
                                continue;
                            } else {
                                // swapping dice for kept die
                                if (selection - 1 == swapIndex) {
                                    this.incrementKeeps();
                                    swapIndex++;
                                } else if (selection - 1 > swapIndex) {
                                    this.swapDice(swapIndex, selection - 1);
                                    swapIndex++;
                                } else {
                                    // Invalid selection
                                    System.err.println(
                                        "Already keeping = " + selection + " : " + this.getDieStringFromIndex(selection - 1) + "\n");
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

                    this.rollDice(rerollCount);
                }

                // Get the score!
                System.out.println("Final Roll:\n" + this.getDiceList());

                int score = this.determineScore(category);
                this.setScore(category, score);
                System.out.println("Score for " + category.formatted + " category: " + score);
                Thread.sleep(waitTime * 4);
                this.resetRolls();

            } else {
                System.err.println("Invalid selection: " + selection + ". Category already has a score.\n");
                continue;
            }
        }

        // GAME IS DONE!
        System.out.println(this.getCategoriesList());
        System.out.println("===================================================");
        System.out.println("CONGRATULATIONS! YOU HAVE FINISHED PLAYING YAHTZEE!");
        System.out.println("               Your final score is");
        System.out.println("                      " + this.getTotalScore());
        System.out.println("===================================================");
    }


    /**
     * You can play if you want. :)
     *
     * @param args
     */
    public static void main(String[] args) {

        Yahtzee yahtzee = new Yahtzee();

        yahtzee.start();
    }
}
