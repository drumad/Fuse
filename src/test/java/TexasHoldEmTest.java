import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.*;

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
            "Kc 9s Ks Kd 9d 3c 6d Full House (winner)\n" + "9c Ah Ks Kd 9d 3c 6d Two Pair\n" + "Ac Qc Ks Kd 9d 3c \n" + "9h 5s \n"
                + "4d 2d Ks Kd 9d 3c 6d Flush\n" + "7s Ts Ks Kd 9d";

        assertEquals(expected, texasHoldEm.playTexasHoldEm());
    }
}