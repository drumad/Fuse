import com.audition.PokerHands;
import com.audition.util.FileReaderUtil;
import org.apache.commons.text.WordUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TexasHoldEm extends PokerHands {

    /**
     * Reads through the input file.
     *
     * @return
     */
    public String playTexasHoldEm() throws IOException {

        StringBuffer buffer = new StringBuffer();
        String input = FileReaderUtil.readInputFile();

        List<String> players = new ArrayList<>();
        List<Hand> hands = new ArrayList<>();
        Set<Integer> winners = new HashSet<>();
        String[] lines = input.split("\n");

        Hand highestHand = null;
        String[] highestCards = null;
        int highest = -1;
        int index = 0;

        for (String line : lines) {

            String[] cards = line.toUpperCase().split(" ");
            players.add(line);

            if (cards.length == 7) {
                // Played until the end.
                Hand currHand = determineHand(cards);
                hands.add(currHand);

                if (highestHand == null) {
                    highestHand = currHand;
                    highest = index;
                    highestCards = cards;

                    winners.add(index);
                } else if (currHand.rank > highestHand.rank) {
                    winners.clear();
                    winners.add(index);

                    highestHand = currHand;
                    highest = index;
                    highestCards = cards;
                } else if (currHand.rank == highestHand.rank) {

                    int compare = breakTie(cards, highestCards);
                    if (compare == 0) {
                        // tied
                        winners.add(highest);
                        winners.add(index);
                    } else if (compare < 0) {
                        winners.add(index);
                        highest = index;
                        highestCards = cards;
                    } else if (compare > 0) {
                        winners.add(highest);
                    }
                }
            } else {
                hands.add(null);
            }

            index++;
        }

        for (int i = 0; i < players.size(); i++) {
            String player = lines[i];
            Hand hand = hands.get(i);

            buffer.append(player).append(" ").append(hand == null ? "" : WordUtils.capitalizeFully(hand.formatted));
            if (winners.contains(i)) {
                buffer.append(" ").append("(winner)");
            }

            buffer.append("\n");
        }

        return buffer.toString().trim();
    }
}
