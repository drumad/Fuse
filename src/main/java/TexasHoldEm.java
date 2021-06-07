import com.audition.util.FileReaderUtil;

import java.io.IOException;

public class TexasHoldEm {

    /**
     * Reads through the input file.
     *
     * @return
     */
    public String playTexasHoldEm() throws IOException {

        StringBuffer buffer = new StringBuffer();
        String input = FileReaderUtil.readInputFile();

        String[] lines = input.toUpperCase().split("\n");

        for (String line : lines) {

            String[] hand = line.split(" ");

            if (hand.length == 7) {
                // Played until the end.

            } else {
                // folded

            }
        }

        return buffer.toString().trim();
    }
}
