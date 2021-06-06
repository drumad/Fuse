package com.audition;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class PokerHandsTest {

    private PokerHands pokerHand;

    @Before
    public void setUp() {

        pokerHand = new PokerHands();
    }

    @Test
    public void test_determineHand_straightFlush_23456_true() {

        String[] cards = new String[] {"2H", "3H", "4H", "5H", "6H"};

        assertEquals(PokerHands.Hand.STRAIGHT_FLUSH, pokerHand.determineHand(cards));
    }

    @Test
    public void test_determineHand_straightFlush_A2345_false() {

        String[] cards = new String[] {"2C", "3C", "4C", "5C", "AC"};

        assertEquals(PokerHands.Hand.FLUSH, pokerHand.determineHand(cards));
    }

    @Test
    public void test_determineHand_fourOfAKind_3333K() {

        String[] cards = new String[] {"3C", "3S", "3H", "3D", "KC"};

        assertEquals(PokerHands.Hand.FOUR_KIND, pokerHand.determineHand(cards));
    }

    @Test
    public void test_determineHand_fullHouse_444QQ() {

        String[] cards = new String[] {"4C", "4S", "QH", "4D", "QC"};

        assertEquals(PokerHands.Hand.FULL_HOUSE, pokerHand.determineHand(cards));
    }

    @Test
    public void test_determineHand_flush_spades() {

        String[] cards = new String[] {"6S", "4S", "QS", "TS", "8S"};

        assertEquals(PokerHands.Hand.FLUSH, pokerHand.determineHand(cards));
    }

    @Test
    public void test_determineHand_straight_89TJQ() {

        String[] cards = new String[] {"8C", "TS", "9H", "QC", "JC"};

        assertEquals(PokerHands.Hand.STRAIGHT, pokerHand.determineHand(cards));
    }

    @Test
    public void test_determineHand_threeOfAKind_999KQ() {

        String[] cards = new String[] {"9C", "KS", "QH", "9D", "9H"};

        assertEquals(PokerHands.Hand.THREE_KIND, pokerHand.determineHand(cards));
    }

    @Test
    public void test_determineHand_twoPair_33JJ4() {

        String[] cards = new String[] {"4C", "3S", "JH", "3D", "JC"};

        assertEquals(PokerHands.Hand.TWO_PAIR, pokerHand.determineHand(cards));
    }

    @Test
    public void test_determineHand_pair_35667() {

        String[] cards = new String[] {"6C", "3S", "5H", "6D", "7C"};

        assertEquals(PokerHands.Hand.PAIR, pokerHand.determineHand(cards));
    }

    @Test
    public void test_determineHand_highCard_36758() {

        String[] cards = new String[] {"7H", "5H", "6D", "3C", "8S"};

        assertEquals(PokerHands.Hand.HIGH_CARD, pokerHand.determineHand(cards));
    }

    @Test
    public void test_breakTie_straightFlush_black() {

        String[] black = new String[] {"3S", "4S", "5S", "6S", "7S"};
        String[] white = new String[] {"2H", "3H", "4H", "5H", "6H"};

        assertTrue(pokerHand.isStraightFlush(black));
        assertTrue(pokerHand.isStraightFlush(white));
        assertEquals(-7, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_straightFlush_white() {

        String[] black = new String[] {"8S", "9S", "TS", "JS", "QS"};
        String[] white = new String[] {"TD", "JD", "QD", "KD", "AD"};

        assertTrue(pokerHand.isStraightFlush(black));
        assertTrue(pokerHand.isStraightFlush(white));
        assertEquals(14, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_straightFlush_tie() {

        String[] black = new String[] {"5H", "7H", "6H", "9H", "8H"};
        String[] white = new String[] {"8C", "7C", "5C", "6C", "9C"};

        assertTrue(pokerHand.isStraightFlush(black));
        assertTrue(pokerHand.isStraightFlush(white));
        assertEquals(0, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_fourKind_black() {

        String[] black = new String[] {"9H", "9C", "9D", "9C", "6H"};
        String[] white = new String[] {"2S", "2D", "2H", "7D", "2C"};

        assertTrue(pokerHand.isFourOfAKind(black));
        assertTrue(pokerHand.isFourOfAKind(white));
        assertEquals(-9, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_fourKind_white() {

        String[] black = new String[] {"TH", "TC", "TD", "QH", "TS"};
        String[] white = new String[] {"JC", "2H", "JS", "JD", "JH"};

        assertTrue(pokerHand.isFourOfAKind(black));
        assertTrue(pokerHand.isFourOfAKind(white));
        assertEquals(11, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_fourKind_tie() {

        String[] black = new String[] {"8H", "8C", "5D", "8D", "8S"};
        String[] white = new String[] {"5C", "8H", "8S", "8C", "8D"};

        assertTrue(pokerHand.isFourOfAKind(black));
        assertTrue(pokerHand.isFourOfAKind(white));
        assertEquals(0, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_fullHouse_black() {

        String[] black = new String[] {"3H", "3C", "3D", "6C", "6H"};
        String[] white = new String[] {"2S", "2D", "2H", "7D", "7C"};

        assertTrue(pokerHand.isFullHouse(black));
        assertTrue(pokerHand.isFullHouse(white));
        assertEquals(-3, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_fullHouse_white() {

        String[] black = new String[] {"JH", "JC", "QD", "QH", "QS"};
        String[] white = new String[] {"KC", "2H", "KS", "2S", "KH"};

        assertTrue(pokerHand.isFullHouse(black));
        assertTrue(pokerHand.isFullHouse(white));
        assertEquals(13, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_fullHouse_tie() {

        String[] black = new String[] {"5H", "7C", "5D", "7H", "5S"};
        String[] white = new String[] {"5C", "5H", "7S", "5S", "7H"};

        assertTrue(pokerHand.isFullHouse(black));
        assertTrue(pokerHand.isFullHouse(white));
        assertEquals(0, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_flush_black() {

        String[] black = new String[] {"5H", "7H", "JH", "6H", "9H"};
        String[] white = new String[] {"4C", "5C", "2C", "6C", "7C"};

        assertTrue(pokerHand.isFlush(black));
        assertTrue(pokerHand.isFlush(white));
        assertEquals(-11, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_flush_white() {

        String[] black = new String[] {"8S", "9S", "TS", "JS", "2S"};
        String[] white = new String[] {"TD", "5D", "QD", "KD", "3D"};

        assertTrue(pokerHand.isFlush(black));
        assertTrue(pokerHand.isFlush(white));
        assertEquals(13, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_flush_tie() {

        String[] black = new String[] {"3C", "5C", "4C", "AC", "KC"};
        String[] white = new String[] {"4S", "3S", "5S", "KS", "AS"};

        assertTrue(pokerHand.isFlush(black));
        assertTrue(pokerHand.isFlush(white));
        assertEquals(0, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_straight_black() {

        String[] black = new String[] {"5H", "7C", "8D", "6H", "9S"};
        String[] white = new String[] {"4C", "5H", "3S", "6S", "7H"};

        assertTrue(pokerHand.isStraight(black));
        assertTrue(pokerHand.isStraight(white));
        assertEquals(-9, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_straight_white() {

        String[] black = new String[] {"8H", "9C", "TC", "JS", "QD"};
        String[] white = new String[] {"TC", "JH", "QC", "KD", "AH"};

        assertTrue(pokerHand.isStraight(black));
        assertTrue(pokerHand.isStraight(white));
        assertEquals(14, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_straight_tie() {

        String[] black = new String[] {"TH", "JC", "QS", "KS", "AD"};
        String[] white = new String[] {"TD", "JH", "QD", "KC", "AC"};

        assertTrue(pokerHand.isStraight(black));
        assertTrue(pokerHand.isStraight(white));
        assertEquals(0, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_threeKind_black() {

        String[] black = new String[] {"4H", "4C", "KD", "4H", "6S"};
        String[] white = new String[] {"3C", "QH", "3S", "AS", "3H"};

        assertTrue(pokerHand.isThreeOfAKind(black));
        assertTrue(pokerHand.isThreeOfAKind(white));
        assertEquals(-4, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_threeKind_white() {

        String[] black = new String[] {"5H", "2C", "5C", "5S", "4D"};
        String[] white = new String[] {"7C", "7H", "2C", "5D", "7H"};

        assertTrue(pokerHand.isThreeOfAKind(black));
        assertTrue(pokerHand.isThreeOfAKind(white));
        assertEquals(7, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_threeKind_tie() {

        String[] black = new String[] {"JH", "9C", "JS", "TS", "JD"};
        String[] white = new String[] {"9D", "JH", "JS", "JC", "TC"};

        assertTrue(pokerHand.isThreeOfAKind(black));
        assertTrue(pokerHand.isThreeOfAKind(white));
        assertEquals(0, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_twoPair_black() {

        String[] black = new String[] {"2H", "2C", "KD", "KH", "6S"};
        String[] white = new String[] {"JC", "QH", "QS", "AS", "JH"};

        assertTrue(pokerHand.isTwoPair(black));
        assertTrue(pokerHand.isTwoPair(white));
        assertEquals(-13, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_twoPair_white() {

        String[] black = new String[] {"3H", "2C", "2S", "3S", "4D"};
        String[] white = new String[] {"3C", "3H", "2C", "5S", "2H"};

        assertTrue(pokerHand.isTwoPair(black));
        assertTrue(pokerHand.isTwoPair(white));
        assertEquals(5, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_twoPair_tie() {

        String[] black = new String[] {"AH", "TC", "QS", "TS", "AD"};
        String[] white = new String[] {"AC", "TH", "QD", "TD", "AC"};

        assertTrue(pokerHand.isTwoPair(black));
        assertTrue(pokerHand.isTwoPair(white));
        assertEquals(0, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_pair_black() {

        String[] black = new String[] {"AH", "TC", "QS", "TS", "4D"};
        String[] white = new String[] {"3C", "6H", "3S", "AS", "2H"};

        assertTrue(pokerHand.isPair(black));
        assertTrue(pokerHand.isPair(white));
        assertEquals(-10, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_pair_white() {

        String[] black = new String[] {"AH", "TC", "QS", "TS", "4D"};
        String[] white = new String[] {"3C", "6H", "AC", "AS", "2H"};

        assertTrue(pokerHand.isPair(black));
        assertTrue(pokerHand.isPair(white));
        assertEquals(14, pokerHand.breakTie(black, white));
    }

    @Test
    public void test_breakTie_pair_tie() {

        String[] black = new String[] {"AH", "TC", "QS", "TS", "4D"};
        String[] white = new String[] {"AC", "TH", "QD", "TD", "4C"};

        assertTrue(pokerHand.isPair(black));
        assertTrue(pokerHand.isPair(white));
        assertEquals(0, pokerHand.breakTie(black, white));
    }

    @Test
    public void playPoker() throws IOException {

        pokerHand.playPoker();
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