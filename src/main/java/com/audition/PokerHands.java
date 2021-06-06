package com.audition;

import com.audition.util.FileReaderUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PokerHands {

    protected enum Hand {

        HIGH_CARD(0, "high card"),
        PAIR(1, "pair"),
        TWO_PAIR(2, "two pair"),
        THREE_KIND(3, "three of a kind"),
        STRAIGHT(4, "straight"),
        FLUSH(5, "flush"),
        FULL_HOUSE(6, "full house"),
        FOUR_KIND(7, "four of a kind"),
        STRAIGHT_FLUSH(8, "straight flush");

        public int rank;

        public String formatted;

        Hand(int rank, String formatted) {

            this.rank = rank;
            this.formatted = formatted;
        }
    }


    private Map<Character, Integer> pokerMap;

    private Map<Character, String> suitNames;

    private Map<Integer, String> cardNames;

    private char[] suits;

    public PokerHands() {

        pokerMap = new HashMap<>(13);
        pokerMap.put('2', 2);
        pokerMap.put('3', 3);
        pokerMap.put('4', 4);
        pokerMap.put('5', 5);
        pokerMap.put('6', 6);
        pokerMap.put('7', 7);
        pokerMap.put('8', 8);
        pokerMap.put('9', 9);
        pokerMap.put('T', 10);
        pokerMap.put('J', 11);
        pokerMap.put('Q', 12);
        pokerMap.put('K', 13);
        pokerMap.put('A', 14);

        suitNames = new HashMap<>(13);
        suitNames.put('C', "Clubs");
        suitNames.put('D', "Diamonds");
        suitNames.put('H', "Hearts");
        suitNames.put('S', "Spades");

        cardNames = new HashMap<>(13);
        cardNames.put(2, "2");
        cardNames.put(3, "3");
        cardNames.put(4, "4");
        cardNames.put(5, "5");
        cardNames.put(6, "6");
        cardNames.put(7, "7");
        cardNames.put(8, "8");
        cardNames.put(9, "9");
        cardNames.put(10, "10");
        cardNames.put(11, "Jack");
        cardNames.put(12, "Queen");
        cardNames.put(13, "King");
        cardNames.put(14, "Ace");

        suits = new char[] {'C', 'D', 'H', 'S'};
    }

    public void playPoker() throws IOException {

        String input = FileReaderUtil.readInputFile().toUpperCase();

        String[] lines = input.split("\n");
        String[] blackCards = new String[5];
        String[] whiteCards = new String[5];
        boolean isBlack = false;
        int winningCard;

        for (String line : lines) {

            int i = 0;
            int j = 0;
            winningCard = 0;

            for (String token : line.split(" ")) {

                if (token.isBlank()) {
                    continue;
                }

                if (token.equalsIgnoreCase("black:")) {
                    isBlack = true;
                    continue;
                } else if (token.equalsIgnoreCase("white:")) {
                    isBlack = false;
                    continue;
                }

                if (isBlack) {
                    blackCards[i++] = token.trim();
                } else {
                    whiteCards[j++] = token.trim();
                }
            }

            Hand bHand = determineHand(blackCards);
            Hand wHand = determineHand(whiteCards);
            Hand winningHand = null;
            String winner = null;
            String[] winningCards = null;

            if (bHand.rank > wHand.rank) {
                winner = "Black";
                winningHand = bHand;
                winningCards = blackCards;
            } else if (wHand.rank > bHand.rank) {
                winner = "White";
                winningHand = wHand;
                winningCards = whiteCards;
            } else {
                winningCard = breakTie(blackCards, whiteCards);
                if (winningCard < 0) {
                    winner = "Black";
                    winningHand = bHand;
                    winningCards = blackCards;
                    winningCard = Math.abs(winningCard);
                } else if (winningCard > 0) {
                    winner = "White";
                    winningHand = wHand;
                    winningCards = whiteCards;
                }
            }

            if (winner == null && winningHand == null) {
                System.out.println("Tie.");
            } else {
                System.out
                    .println(winner + " wins. - with " + winningHand.formatted + ": " + wordifyWin(winningHand, winningCards, winningCard));
            }
        }
    }

    public Hand determineHand(String[] cards) {

        Hand hand;

        if (isStraightFlush(cards)) {
            hand = Hand.STRAIGHT_FLUSH;
        } else if (isFourOfAKind(cards)) {
            hand = Hand.FOUR_KIND;
        } else if (isFullHouse(cards)) {
            hand = Hand.FULL_HOUSE;
        } else if (isFlush(cards)) {
            hand = Hand.FLUSH;
        } else if (isStraight(cards)) {
            hand = Hand.STRAIGHT;
        } else if (isThreeOfAKind(cards)) {
            hand = Hand.THREE_KIND;
        } else if (isTwoPair(cards)) {
            hand = Hand.TWO_PAIR;
        } else if (isPair(cards)) {
            hand = Hand.PAIR;
        } else {
            hand = Hand.HIGH_CARD;
        }

        return hand;
    }

    /**
     * Breaks the tie if both have the same rank of cards.
     *
     * @param black Array of cards of one player.
     * @param white Array of cards of the other player.
     * @return Will return the value of the winning card.
     * A negative value means the first player won;
     * second player if positive.
     * If value is 0, it's still a tie.
     */
    public int breakTie(String[] black, String[] white) {

        int highestBlack = -1;
        int highestWhite = -1;

        int[] sumsBlack = organizeCards(black);
        int[] sumsWhite = organizeCards(white);

        for (int i = 0; i < sumsBlack.length; i++) {
            if (sumsBlack[i] > sumsWhite[i]) {
                return -sumsBlack[i];
            } else if (sumsWhite[i] > sumsBlack[i]) {
                return sumsWhite[i];
            }
        }

        if (highestBlack > highestWhite) {
            return -highestBlack;
        } else if (highestWhite > highestBlack) {
            return highestWhite;
        }

        return 0;
    }

    private String wordifyWin(Hand winningHand, String[] cards, int winningCard) {

        StringBuffer buffer = new StringBuffer();
        Comparator<Map.Entry<Integer, Long>> compareByValue = Comparator.comparing(Map.Entry::getValue);
        String link = " over ";
        List<Integer> values;

        if (winningCard > 14) {
            // It's a sum of a kind, and will need to be divided based on their type.
            int divisor;
            switch (winningHand) {

                case PAIR:
                    divisor = 2;
                    break;
                case THREE_KIND:
                    divisor = 3;
                    break;
                case FOUR_KIND:
                    divisor = 4;
                    break;
                default:
                    divisor = 1;
                    break;
            }

            winningCard /= divisor;
        } else {
            winningCard = winningCard > 1 ? winningCard : getHighestCard(cards);
        }
        String cardName = cardNames.get(winningCard);

        switch (winningHand) {
            case TWO_PAIR:
                link = " and ";
            case FULL_HOUSE:
                Map<Integer, Long> map = generateValueMap(cards);
                values = map.entrySet().stream().sorted(compareByValue.thenComparing(Map.Entry::getKey).reversed()).map(Map.Entry::getKey)
                            .collect(Collectors.toList());
                buffer.append(cardNames.get(values.get(0))).append(link).append(cardNames.get(values.get(1)));
                break;
            case STRAIGHT_FLUSH:
            case FLUSH:
                link = " of ";
                String suit = suitNames.get(cards[0].charAt(1));
                buffer.append(cardName).append(link).append(suit);
                break;
            case FOUR_KIND:
            case STRAIGHT:
            case THREE_KIND:
            default:
                buffer.append(cardName);
                break;

        }

        return buffer.toString();
    }

    /**
     * Checks a hand is a straight flush
     *
     * @param cards The hand to check.
     * @return True if it's a straight flush. False if otherwise.
     */
    public boolean isStraightFlush(String[] cards) {

        return isStraight(cards) && isFlush(cards);
    }

    /**
     * Checks a hand has a four of a kind.
     *
     * @param cards The hand to check.
     * @return True if it has a four of a kind. False if otherwise.
     */
    public boolean isFourOfAKind(String[] cards) {

        return verifyOfAKind(cards, 2, 1);
    }

    /**
     * Checks a hand is a full house.
     *
     * @param cards The hand to check.
     * @return True if a full house. False if otherwise.
     */
    public boolean isFullHouse(String[] cards) {

        return verifyOfAKind(cards, 2, 2);
    }

    /**
     * Checks a hand is a flush.
     *
     * @param cards The hand to check.
     * @return True if a flush. False if otherwise.
     */
    public boolean isFlush(String[] cards) {

        char firstSuit = cards[0].charAt(1);

        return Arrays.stream(cards).allMatch(c -> c.charAt(1) == firstSuit);
    }

    /**
     * Checks a hand is a straight.
     *
     * @param cards The hand to check.
     * @return True if a straight. False if otherwise.
     */
    public boolean isStraight(String[] cards) {

        // We are counting the number of distinct cards.
        long count = Arrays.stream(cards).mapToInt(c -> pokerMap.get(c.charAt(0))).distinct().count();

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (String card : cards) {

            int val = pokerMap.get(card.charAt(0));
            min = Integer.min(min, val);
            max = Integer.max(max, val);
        }

        if (count == cards.length && max - min + 1 == count) {
            return true;
        }
        return false;
    }

    /**
     * Checks a hand has a three of a kind.
     *
     * @param cards The hand to check.
     * @return True if hand has a three of a kind. False if otherwise.
     */
    public boolean isThreeOfAKind(String[] cards) {

        return verifyOfAKind(cards, 3, 1);
    }

    /**
     * Checks a hand has a two pair.
     *
     * @param cards The hand to check.
     * @return True if it has a two-pair. False if otherwise.
     */
    public boolean isTwoPair(String[] cards) {

        return verifyOfAKind(cards, 3, 2);
    }

    /**
     * Checks if the hand is a pair.
     *
     * @param cards The hand to check.
     * @return True if it has a pair. False if otherwise.
     */
    public boolean isPair(String[] cards) {

        return verifyOfAKind(cards, 4, 1);
    }

    /**
     * Gets the values of the cards.
     *
     * @param cards The cards to determine the values.
     * @return The int array of the values will be returned.
     */
    private int[] getValues(String[] cards) {

        return Arrays.stream(cards).mapToInt(c -> pokerMap.get(c.charAt(0))).toArray();
    }

    /**
     * Generates a value map based on the cards passed.
     *
     * @param cards The cards to determine map.
     * @return The returned map will have the value as key, and its occurrences as its value.
     */
    private Map<Integer, Long> generateValueMap(String[] cards) {

        int[] values = getValues(cards);
        return Arrays.stream(values).boxed().collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));

    }

    /**
     * Verifies the type of card combination the hand is. This will be used to check for four of a kind, full house,
     * three of a kind, two pair, and pair.
     *
     * @param cards       The hand to analyze.
     * @param groupedSize Expected size after being grouped by value.
     *                    For example, if we're expecting a pair: a hand of 5, should have 4 groups.
     * @param expected    The expected number of groups after ignoring the single-occurrence cards.
     * @return True if groupedSize and expected sizes are met.
     */
    private boolean verifyOfAKind(String[] cards, int groupedSize, int expected) {

        Map<Integer, Long> map = generateValueMap(cards);

        if (map.size() == groupedSize) {

            // Remove single-occurrence cards
            map.entrySet().removeIf(e -> e.getValue() == 1);

            if (map.size() == expected) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets the highest card from the array of String cards.
     *
     * @param cards The array of cards to analyze.
     * @return The value of the highest card.
     */
    private int getHighestCard(String[] cards) {

        int[] values = getValues(cards);

        return Arrays.stream(values).max().getAsInt();
    }

    /**
     * The organization is as follows:<br>
     * 1. Order by most occurrences to least, and<br>
     * 2. Arrange by their values.<br>
     *
     * @param cards The cards to reorganize.
     * @return An int array of the organized cards, without duplicates.
     */
    private int[] organizeCards(String[] cards) {

        Map<Integer, Long> map = generateValueMap(cards);

        // To achieve comparison by value first, then comparing by key.
        Comparator<Map.Entry<Integer, Long>> compareByValue = Comparator.comparing(Map.Entry::getValue);

        return map.entrySet().stream().sorted(compareByValue.thenComparing(Map.Entry::getKey).reversed()).map(e -> e.getKey())
                  .mapToInt(Integer::intValue).toArray();
    }

}
