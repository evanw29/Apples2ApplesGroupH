import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


import static java.lang.System.exit;

public class demo {
    public static void main(String[] args) {
        // Main transformed for demo use

        // Initialize list of green and red cards
        HashMap<String, GreenCard> greenCards = new HashMap<>();
        HashMap<String, RedCard> redCards = new HashMap<>();

        // Reads file, create HashMap for greenCards
        try {
            // Reads green cards from text file
            List<String> greenCardsLines = readLinesFromFile("GCSynonyms.txt");

            // Creates green card objects and adds to hashmap
            greenCards = generateCardsGreen(greenCardsLines);
        }
        catch (IOException e) {
            System.out.println("Error in loading GSSynonyms.txt");
            exit(1);
        }

        // Reads file, create HashMap for redCards
        try {
            // Reads green cards from text file
            List<String> redCardsLines = readLinesFromFile("rc_synonyms.txt");

            // Creates green card objects and adds to hashmap
            redCards = generateCardsRed(redCardsLines);
        }
        catch (IOException e) {
            System.out.println("Error in loading rc_synonyms.txt");
            exit(1);
        }

        // Used for random red card when no match
        List<RedCard> redCardList = new ArrayList<>(redCards.values());

        // input / output section for demo
        boolean loop = true;
        Scanner input = new Scanner(System.in);

        while (loop) {
            System.out.println("Enter green Card, Enter 1 to display green card collection, or Enter 2 to exit.");
            String inCard = input.next();
            if (inCard.equals("2")) {
                loop = false;
            } else if (inCard.equals("1")) {
                System.out.println("Collection of green cards:");
                for (Map.Entry<String, GreenCard> entry : greenCards.entrySet()) {
                    System.out.println(entry.getValue().getAdjective());
                }
            } else {
                GreenCard card = greenCards.get(inCard.toLowerCase());
                if (card == null) {
                    System.out.println("Card not within collection.");
                } else {
                    RedCard bestMatch = nounSearch(card, redCards);
                    if (bestMatch == null) {
                        // Couldn't find a match outputting random red card
                        // System.out.println("Random"); for testing
                        // Gets random index
                        int randomIndex = new Random().nextInt(redCardList.size());
                        bestMatch = redCardList.get(randomIndex);
                    }
                    // Prints best match
                    System.out.printf("Best Red apple card: %s\n", bestMatch.getNoun());

                }
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

    // Used for finding the best noun for the given adjective
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

    // Generates card object and stores in HashMap. Should be added to CardDeck object.
    // So we don't have repeated functionality, will do for demo though
    public static HashMap<String, GreenCard> generateCardsGreen(List<String> greenCardsLines){

        // Initialization
        HashMap<String, GreenCard> greenCards = new HashMap<>();
        List<String> synonyms;

        // Keeping adjective in synonym list so that we can check it against noun synonyms too
        for (String line : greenCardsLines) {
            synonyms = new ArrayList<>(Arrays.asList(line.split(",")));
            GreenCard card = new GreenCard(synonyms.getFirst().toLowerCase(), synonyms);
            greenCards.put(card.getAdjective(), card);
        }

        return greenCards;
    }
    public static HashMap<String, RedCard> generateCardsRed(List<String> redCardsLines){

        // Initialization
        HashMap<String, RedCard> redCards = new HashMap<>();
        List<String> synonyms;

        for (String line : redCardsLines) {
            synonyms = new ArrayList<>(Arrays.asList(line.split(",")));
            RedCard card = new RedCard(synonyms.getFirst(), synonyms);
            card.removeSynonym(card.getNoun());
            card.setNoun(card.getNoun().toLowerCase());
            redCards.put(card.getNoun(), card);
        }

        return redCards;
    }
}
