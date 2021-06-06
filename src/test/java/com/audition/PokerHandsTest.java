package com.audition;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class PokerHandsTest {

    private PokerHands pokerHand;

    @Before
    public void setUp() {

        pokerHand = new PokerHands();
    }

    @Test
    public void determineHand() {

    }

    @Test
    public void breakTie() {

    }

    @Test
    public void test_isStraightFlush_straightsOnly_false() {

        String[] cards = new String[] {"4H", "8C", "6D", "5S", "7H"};

        assertFalse(pokerHand.isStraightFlush(cards));

        cards = new String[] {"TS", "AH", "QD", "KD", "JC"};

        assertFalse(pokerHand.isStraightFlush(cards));
    }

    @Test
    public void test_isStraightFlush_flushOnly_false() {

        String[] cards = new String[] {"3H", "2H", "JH", "AH", "5H"};

        assertFalse(pokerHand.isStraightFlush(cards));
    }

    @Test
    public void test_isStraightFlush_true() {

        String[] cards = new String[] {"6S", "4S", "5S", "8S", "7S"};

        assertTrue(pokerHand.isStraightFlush(cards));
    }

    @Test
    public void test_isFourOfAKind_AAAAQ_true() {

        String[] cards = new String[] {"AC", "AS", "AD", "AH", "QH"};

        assertTrue(pokerHand.isFourOfAKind(cards));
    }

    @Test
    public void test_isFourOfAKind_QQQKK_false() {

        String[] cards = new String[] {"KH", "KD", "QS", "QH", "QC"};

        assertFalse(pokerHand.isFourOfAKind(cards));
    }

    @Test
    public void test_isFullHouse_QQQKK_true() {

        String[] cards = new String[] {"KH", "KD", "QS", "QH", "QC"};

        assertTrue(pokerHand.isFullHouse(cards));
    }

    @Test
    public void test_isFullHouse_JJJJT_false() {

        String[] cards = new String[] {"JC", "JS", "JD", "TH", "JH"};

        assertTrue(pokerHand.isFourOfAKind(cards));
    }

    @Test
    public void test_isFlush_HHHHC_false() {

        String[] cards = new String[] {"3H", "2H", "JH", "AH", "5C"};

        assertFalse(pokerHand.isFlush(cards));
    }

    @Test
    public void test_isFlush_HHHHH_true() {

        String[] cards = new String[] {"3H", "2H", "JH", "AH", "5H"};

        assertTrue(pokerHand.isFlush(cards));
    }

    @Test
    public void test_isStraight_A2345_false() {

        // For this kata, this is not acceptable
        String[] cards = new String[] {"AS", "2H", "4C", "3S", "5C"};

        assertFalse(pokerHand.isStraight(cards));
    }

    @Test
    public void test_isStraight_22556_false() {

        String[] cards = new String[] {"6S", "2H", "2C", "5S", "5C"};

        assertFalse(pokerHand.isStraight(cards));
    }

    @Test
    public void test_isStraight_45678_true() {

        String[] cards = new String[] {"4H", "8C", "6D", "5S", "7H"};

        assertTrue(pokerHand.isStraight(cards));
    }

    @Test
    public void test_isStraight_TJQKA_true() {

        String[] cards = new String[] {"TS", "AH", "QD", "KD", "JC"};

        assertTrue(pokerHand.isStraight(cards));
    }

    @Test
    public void test_isThreeOfAKind_2A444_true() {

        String[] cards = new String[] {"2H", "AD", "4S", "4D", "4H"};

        assertTrue(pokerHand.isThreeOfAKind(cards));
    }

    @Test
    public void test_isThreeOfAKind_77766_true() {

        String[] cards = new String[] {"7H", "7D", "6S", "7C", "6D"};

        assertFalse(pokerHand.isThreeOfAKind(cards));
    }

    @Test
    public void test_isTwoPair_2233A_true() {

        String[] cards = new String[] {"2S", "AH", "3D", "2D", "3C"};

        assertTrue(pokerHand.isTwoPair(cards));
    }

    @Test
    public void test_isTwoPair_22233_false() {

        String[] cards = new String[] {"2H", "2C", "3S", "3D", "2C"};

        assertFalse(pokerHand.isTwoPair(cards));
    }

    @Test
    public void test_isPair_TTQKA_true() {

        String[] cards = new String[] {"TS", "AH", "QD", "KD", "TC"};

        assertTrue(pokerHand.isPair(cards));
    }

    @Test
    public void test_isPair_9TQKA_false() {

        String[] cards = new String[] {"TS", "9H", "QD", "KD", "AC"};

        assertFalse(pokerHand.isPair(cards));
    }
}