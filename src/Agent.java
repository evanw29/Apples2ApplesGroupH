import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class Agent {
    public static void main(String[] args) {

        /* ******** To test agent through intellij *************** */
        // click the file selected beside run button triangle
        // click three dots for the Agent class, edit parameters/ arguments
        // copy past following
        // 4 2 src\Cards\SampleGreenExtension.txt src\Cards\SampleRedExtension.txt
        // can change first two integers but keep path for txt files the same
        // or use terminal

        // Initialize decks / numbers
        Map<String, GreenCard> greenCardDeck = null;
        Map<String, RedCard> redCardDeck = null;
        int numPlayers = 0;
        int pointsToWin = 0;

        // Reads extension files, create HashMap for greenCardDeck / RedCards
        try {

            // Exits if less/more than 4 arguments
            if (args.length != 4) { throw new IOException(); }

            numPlayers = Integer.parseInt(args[0]);
            pointsToWin = Integer.parseInt(args[1]);

            // Reads green cards from text file
            List<String> greenCards = readLinesFromFile("src/BasicGreenCards.txt");
            greenCards.addAll(readLinesFromFile(args[2]));

            // Creates green card objects list
            List<GreenCard> temp = greenCards.stream().map(GreenCard::new).collect(Collectors.toList());

            // Creates green card deck hashmap and deals with duplicates
            greenCardDeck = temp.stream()
                    .collect(Collectors.toMap(GreenCard::getID, Function.identity(),
                            (GreenCard c1, GreenCard c2) -> {
                            c1.addSynonyms(c2.getSynonyms());
                            return c1; }));


            // Reads red cards from text file
            List<String> redCards = readLinesFromFile("src/BasicRedCards.txt");
            redCards.addAll(readLinesFromFile(args[3]));

            // Creates red card objects list
            List<RedCard> temp2 = redCards.stream().map(RedCard::new).collect(Collectors.toList());
            // Creates red card deck hashmap and deals with duplicates (This ignores second flavor text)
            redCardDeck = temp2.stream()
                    .collect(Collectors.toMap(RedCard::getID, Function.identity(),
                            (RedCard c1, RedCard c2) -> c1));
        }
        catch (Exception e) {
            // Invalid arguments
            System.out.println("Usage: ./Agent <MAX NUMBER OF PLAYERS> <NUMBER OF POINTS TO WIN> <GREEN EXTENSION FILE NAME> <RED EXTENSION FILE NAME>");
            exit(1);
        }

        // Pre-training will be setup here if we want to implement it

        // Set up
        Players players = new Players(numPlayers, pointsToWin);
        Scanner input = new Scanner(System.in);
        int winnerID;
        int winner;
        int currentJudge = 0;

        // Display
        System.out.println("I assume I am player 1!");
        System.out.print("The other players are:");
        for (int i = 2; i <= numPlayers; i++) System.out.print(i + " ");
        System.out.println("\nPlease enter the red cards dealt to me, one by one.");
        Hand agentsHand = new Hand(redCardsPlayed(redCardDeck, 7));

        // Main Loop (Can be refactored / reformatted but not high priority, focus on methods to choose card)
        boolean loop = true;
        while (loop) {
            System.out.println("---------------NEW ROUND------------------");
            System.out.println("What is my role in this round? (1-player; anything else - judge): ");
            String choice = input.next();
            if (!choice.equals("1")) {
                // Judge interface
                System.out.println("The green card I selected is:");
                GreenCard chosenGC = randomCard(greenCardDeck);
                System.out.println(chosenGC.getID());
                System.out.println("Now tell me the red cards the other players selected.");
                Hand judgeHand = new Hand(redCardsPlayed(redCardDeck, numPlayers-1));
                judgeHand.setGreenCard(chosenGC);

                // Pick Winner - Behavior changes when a player is close to winning
                RedCard winningRed = judgeHand.chooseCard(players.getCurrentRoundJudgeBehaviour(currentJudge));
                System.out.println("The winning card is:");
                System.out.println(winningRed.getID());

            } else {
                // Player interface
                System.out.println("Please enter the green card selected by the judge: ");
                GreenCard chosenGreenCard = validGreenCard(greenCardDeck);
                agentsHand.setGreenCard(chosenGreenCard);
                System.out.println("The red card I play is:");

                // pick best card from hand
                RedCard cRed = agentsHand.chooseCard(false);
                System.out.println(cRed.getID());

            }

            // Update Scores, check for winner
            System.out.println("Please tell me which player won (number): ");
            winner = Integer.parseInt(input.next());
            players.incrementPlayerScore(winner);
            winnerID = players.checkForWinner();
            if (!(winnerID==-1)) { // If not equals to -1, then someone won, exits loop/game
                System.out.println("GAME OVER");
                System.out.println("Player " + winnerID + " won");
                loop = false;
                continue;
            }

            // If game continues and agent was a player, refill hand and remove played cards
            if (choice.equals("1")) {
                System.out.println("Now tell me the red cards the other players selected");
                // Removing cards played from deck
                ArrayList<RedCard> redPlayed = redCardsPlayed(redCardDeck, numPlayers-1);
                Hand testBehaviour = new Hand(redPlayed);
                testBehaviour.setGreenCard(agentsHand.getGreenCard());
                RedCard c = testBehaviour.chooseCard(true);

                // updates judges behaviour
                if (winner != 1 && c.equals(redPlayed.get(winner-2))) { // agent didn't win and judge is contrarian
                    players.updateJudgeBehaviour(currentJudge, true);
                } else { // agent won or judge is non-contrarian
                    players.updateJudgeBehaviour(currentJudge, false);
                }
                currentJudge = (currentJudge + 1) % (numPlayers-1);
                agentsHand.addRedCard(validRedCard(redCardDeck));


            }

        }


    }

    // Reads the lines from a given file name
    public static List<String> readLinesFromFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        // Close the reader
        reader.close();

        return lines;
    }

    public static ArrayList<RedCard> redCardsPlayed(Map<String, RedCard> deck, int numCards) {
        Scanner input = new Scanner(System.in);
        ArrayList<RedCard> pulledCards = new ArrayList<>();
        for (int i = 1; i <= numCards; i++) {
            boolean loop = true;
            while (loop) {
                System.out.print("Card " + i + ": ");
                String card = input.nextLine();
                if (!deck.containsKey(card.toLowerCase())) {
                    System.out.println("Invalid red card, please choose one from deck.");
                } else {
                    // Removes Card from deck and adds to return list
                    loop = false;
                    pulledCards.add(deck.remove(card.toLowerCase()));
                }

            }
        }

        return pulledCards;
    }

    public static GreenCard randomCard(Map<String, GreenCard> deck) {
        // Used to pull random green card from deck
        List<String> keysAsArray = new ArrayList<>(deck.keySet());
        int r = new Random().nextInt(keysAsArray.size());
        String chosen = keysAsArray.get(r);
        return deck.remove(chosen);
    }

    public static GreenCard validGreenCard(Map<String, GreenCard> deck) {
        Scanner input = new Scanner(System.in);
        while (true) {
            String card = input.nextLine();

            if (deck.containsKey(card.toLowerCase())) {
                return deck.remove(card.toLowerCase());
            }
            System.out.println("Invalid Green Card, pick cards apart of deck.");
            System.out.println("Please enter the green card selected by the judge: ");
        }

    }

    public static RedCard validRedCard(Map<String, RedCard> deck) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter the new red card dealt to me: ");
            String card = input.nextLine();

            if (deck.containsKey(card.toLowerCase())) {
                return deck.remove(card.toLowerCase());
            }
            System.out.println("\nInvalid Red Card, pick cards apart of deck.");
        }

    }
}
