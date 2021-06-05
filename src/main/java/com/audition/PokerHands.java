package com.audition;

import java.util.HashMap;
import java.util.Map;

public class PokerHands {

    protected enum Hand {

        HIGH_CARD (0),
        PAIR (1),
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

    private Map<String, Integer> pokerMap;

    private char[] suits;

    private PokerHands() {

        pokerMap = new HashMap<>();

        pokerMap.put("2", 2);
        pokerMap.put("3", 3);
        pokerMap.put("4", 4);
        pokerMap.put("5", 5);
        pokerMap.put("6", 6);
        pokerMap.put("7", 7);
        pokerMap.put("8", 8);
        pokerMap.put("9", 9);
        pokerMap.put("J", 10);
        pokerMap.put("Q", 11);
        pokerMap.put("K", 12);
        pokerMap.put("A", 13);

        suits = new char[]{'C', 'D', 'H', 'S'};
    }

    public Hand determineHand(String[] cards) {

        Hand hand = Hand.HIGH_CARD;

        return hand;
    }

    public int breakTie(Hand hand, String[] black, String[] white) {

        return 0;
    }

    /**
     *
     * @param cards
     * @return
     */
    public boolean isStraightFlush(String[] cards) {

        return false;
    }

    public boolean isFourOfAKind(String[] cards) {

        return false;
    }

    public boolean isFullHouse(String[] cards) {

        return false;
    }

    public boolean isFlush(String[] cards) {

        return false;
    }

    public boolean isStraight(String[] cards) {

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
