package com.audition;

import com.audition.TexasHoldEm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TexasHoldEmTest {

    private TexasHoldEm texasHoldEm;

    @Before
    public void setUp() {

        texasHoldEm = new TexasHoldEm();
    }

    @Test
    public void playTexasHoldEm() throws IOException {

        String expected =
            "Kc 9s Ks Kd 9d 3c 6d Full House\n"
                + "9c Ah Ks Kd 9d 3c 6d Two Pair\n"
                + "Ac Qc Ks Kd 9d 3c\n"
                + "9h 5s\n"
                + "4d 2d Ks Kd 9d 3c 6d Flush\n"
                + "7s Ts Ks Kd 9d\n"
                + "Ac 9s As Ad 9d 3c 6d Full House (winner)";

        assertEquals(expected, texasHoldEm.playTexasHoldEm());
    }
}