import java.util.HashMap;
import java.util.Map;

public class Players {
    // Keeps track of players scores and detects when someone wins
    HashMap<Integer, Integer> playerScores = new HashMap<>();
    HashMap<Integer, Boolean> judgeBehaviour = new HashMap<>();
    final int winningScore;

    Players(int numPlayers, int maxScore) {
        // Setup for keeping count of score
        this.winningScore = maxScore;
        for (int i = 1; i <= numPlayers; i++) {
            playerScores.put(i, 0);
        }
        for (int i = 0; i < numPlayers; i++) {
            judgeBehaviour.put(i, false);
        }
    }

    public void incrementPlayerScore(int player) {
        // Increments player score by 1
        this.playerScores.merge(player, 1, Integer::sum);
    }

    public void updateJudgeBehaviour(int judge, boolean behaviour) {
        this.judgeBehaviour.put(judge, behaviour);
    }

    public boolean getCurrentRoundJudgeBehaviour(int judge) {
        if (checkForCloseWinner()) return true; // Contrarian if someone is close to winning
        return this.judgeBehaviour.get(judge);
    }

    public int checkForWinner() {
        for (Map.Entry<Integer, Integer> player : this.playerScores.entrySet()) {
            if (player.getValue() >= this.winningScore) {
                return player.getKey();
            }
        }
        return -1; // No winners yet
    }

    public boolean checkForCloseWinner() {
        // Will win in next move
        for (Map.Entry<Integer, Integer> player : this.playerScores.entrySet()) {
            if (player.getValue() == (this.winningScore - 1)) {
                return true;
            }
        }
        return false; // No close winners
    }


}
