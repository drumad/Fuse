package com.audition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PokerHands {

    protected enum Hand {

        HIGH_CARD(0),
        PAIR(1),
        TWO_PAIR(2),
        THREE_KIND(3),
        STRAIGHT(4),
        FLUSH(5),
        FULL_HOUSE(6),
        FOUR_KIND(7),
        STRAIGHT_FLUSH(8);

        public int rank;

        Hand(int rank) {

            this.rank = rank;
        }
    }


    private Map<Character, Integer> pokerMap;

    private char[] suits;

    public PokerHands() {

        pokerMap = new HashMap<>();

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

        suits = new char[] {'C', 'D', 'H', 'S'};
    }

    public Hand determineHand(String[] cards) {

        Hand hand = Hand.HIGH_CARD;

        return hand;
    }

    public int breakTie(Hand hand, String[] black, String[] white) {

        return 0;
    }

    /**
     * @param cards
     * @return
     */
    public boolean isStraightFlush(String[] cards) {

        return isStraight(cards) && isFlush(cards);
    }

    public boolean isFourOfAKind(String[] cards) {

        return false;
    }

    public boolean isFullHouse(String[] cards) {

        return false;
    }

    public boolean isFlush(String[] cards) {

        char firstSuit = cards[0].charAt(1);

        return Arrays.stream(cards).allMatch(c -> c.charAt(1) == firstSuit);
    }

    public boolean isStraight(String[] cards) {

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

    public boolean isThreeOfAKind(String[] cards) {

        return false;
    }

    public boolean isTwoPair(String[] cards) {

        return false;
    }

    public boolean isPair(String[] cards) {

        return false;
    }

}
