import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class Agent {
    public static void main(String[] args) {

        /****************** To test agent through intellij ****************/
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
            List<String> greenCards = readLinesFromFile("src/Cards/BasicGreenCards.txt");
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
            List<String> redCards = readLinesFromFile("src/Cards/BasicRedCards.txt");
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
            System.out.println("Usage: ./agent <MAX NUMBER OF PLAYERS> <NUMBER OF POINTS TO WIN> <GREEN EXTENSION FILE NAME> <RED EXTENSION FILE NAME>");
            exit(1);
        }



        // Set up
        Players players = new Players(numPlayers, pointsToWin);
        Scanner input = new Scanner(System.in);
        Hand agentsHand = new Hand();

        // Display
        System.out.println("I assume I am player 1!");
        System.out.print("The other players are:");
        for (int i = 2; i <= numPlayers; i++) System.out.print(i + " ");
        System.out.println("\nPlease enter the red cards dealt to me, one by one.");
        for (int i = 1; i <= 7; i++) {
            System.out.println("Card " + i + ": ");
            String card = input.next();
            exit(1); // will remove later
        }

        // Main Loop


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

    // Going to remove, just kept for now
    public static RedCard nounSearch(GreenCard adjectiveCard, HashMap<String, RedCard> cards) {

        List<String> adjList = adjectiveCard.getSynonyms();

        RedCard rc;
        int bestValue = 0;
        RedCard bestMatch = null;
        for (String noun : cards.keySet()) {

            rc = cards.get(noun);
            List<String> nounList = rc.getSynonyms();

            // Retains all matching
            nounList.retainAll(adjList);

            if (nounList.size() > bestValue) {
                bestMatch = rc;
                bestValue = nounList.size();

            }


        }

        return bestMatch;
    }


}
