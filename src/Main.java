import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        // Initialize list of green and red cards
        HashMap<String, GreenCard> greenCards = new HashMap<>();
        HashMap<String, RedCard> redCards = new HashMap();

        // Reads, formats and adds to list of green cards
        try {

            // Reads green cards from text file
            List<String> greenCardsLines = readLinesFromFile("GreenCards.txt");

            // Extracts just the words from the lines of the text file
            List<List<String>> greenCardsWords = extractWords(greenCardsLines);

            // Goes through each green card
            for (List<String> card : greenCardsWords) {
                String adjective;
                List<String> synonyms = new ArrayList<>();

                // Sets the adjectives and the three synonyms attached to them
                adjective = card.get(0);
                synonyms.add(card.get(1));
                synonyms.add(card.get(2));
                synonyms.add(card.get(3));

                // Creates a green card object and sets the adjective and synonyms
                GreenCard greenCard = new GreenCard(adjective, synonyms);

                // For testing purposes
                // System.out.println(greenCard.getAdjective() + " " + greenCard.getSynonyms());

                // Adds the green card to the green cards list
                greenCards.put(adjective, greenCard);
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        // Reads, formats and adds to list of red cards
        try {

            // Reads red cards from text file
            List<String> redCardsLines = readLinesFromFile("RedCards.txt");

            // Splits the noun and flavour text for the lines of the text file
            List<List<String>> redCardsWords = splitLines(redCardsLines);

            // Goes through each red card
            for (List<String> card : redCardsWords) {

                // Sets the noun and flavour text
                String noun = card.get(0);
                String flavourText = card.get(1);

                // Creates a red card object and sets the noun and flavour text. NOTE: redCardsLines here is temporary (Should change to be the new synonyms)
                RedCard redCard = new RedCard(noun, flavourText, redCardsLines); // CHANGE redCardLines to new synonyms list of strings

                // Adds the red card to the list of red cards
                redCards.put(noun, redCard);
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        // For testing purposes
        // System.out.println(greenCards);    
        // System.out.println(redCards);

        Scanner input = new Scanner(System.in);

        System.out.println("Enter Green Card: ");
        String inCard = input.next();

        if (greenCards.containsKey(inCard)){

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

    // Extracts just the words for the given lines
    public static List<List<String>> extractWords(List<String> lines) {
        List<List<String>> extractedWords = new ArrayList<>();
        
        for (String line : lines) {
            List<String> wordList = new ArrayList<>();
            String[] words = line.split("\\s+");
            
            // Add first word
            if (words.length > 0) {
                wordList.add(words[0]);
            }
            
            // Extract words inside parentheses using regular expression
            Pattern pattern = Pattern.compile("\\((.*?)\\)");
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String synonyms = matcher.group(1);
                String[] synonymsArray = synonyms.split(",\\s*"); // Split by comma and optional spaces
                for (String synonym : synonymsArray) {
                    wordList.add(synonym);
                }
            }
            extractedWords.add(wordList);
        }
        
        return extractedWords;
    }

    // Splits the line into two parts. Everything before the first hyphen and everything after the first hyphen
    public static List<List<String>> splitLines(List<String> lines) {
        List<List<String>> partsList = new ArrayList<>();
        
        for (String line : lines) {
            List<String> parts = new ArrayList<>();
            
            int hyphenIndex = line.indexOf('-');
            String adjective = line.substring(0, hyphenIndex).trim();
            String flavourText = line.substring(hyphenIndex + 1).trim();
            
            parts.add(adjective);
            parts.add(flavourText);
            
            partsList.add(parts);
        }
        
        return partsList;
    }
}

