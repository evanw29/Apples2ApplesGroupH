import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Hand {
    // Contains the agents red/green cards for win it is a player/judge
    // Implements best choice card and contrarian methods

    GreenCard greenCard;
    HashMap<RedCard, Integer> redCards = new HashMap<>();

    Hand(ArrayList<RedCard> r) {
        for (RedCard red : r) {
            this.redCards.put(red, 0);
        }

    }

    public void setGreenCard(GreenCard g) {
        this.greenCard = g;
    }

    public void addRedCard(RedCard r) {
        // Initial relationship score set to 0
        this.redCards.put(r, 0);
    }

    public RedCard chooseCard(boolean isContrarian) {
        // Returns best choice card or worst choice card based on bool

        // Right now returns random as below choice methodologies isn't implemented
        List<RedCard> keysAsArray = new ArrayList<>(redCards.keySet());
        int r = new Random().nextInt(keysAsArray.size());
        RedCard chosen = keysAsArray.get(r);
        this.redCards.remove(chosen);
        return chosen;
    }

    // Implement methods to relate red to green cards and score them (store in hashmap)
}
