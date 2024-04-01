import java.util.HashMap;
import java.util.Map;

public class Players {
    // Keeps track of players scores and detects when someone wins
    HashMap<Integer, Integer> playerScores = new HashMap<>();
    final int winningScore;

    Players(int numPlayers, int maxScore) {
        // Setup for keeping count of score
        this.winningScore = maxScore;
        for (int i = 1; i <= numPlayers; i++) {
            playerScores.put(i, 0);
        }
    }

    public void incrementPlayerScore(int player) {
        // Increments player score by 1
        this.playerScores.merge(player, 1, Integer::sum);
    }

    public int checkForWinner() {
        for (Map.Entry<Integer, Integer> player : this.playerScores.entrySet()) {
            if (player.getValue() >= this.winningScore) {
                return player.getKey();
            }
        }
        return -1; // No winners yet
    }


}
