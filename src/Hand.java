import java.util.*;

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
        this.redCards.replaceAll( (k,v)-> 0); // New green card resets relationship value
    }

    public void addRedCard(RedCard r) {
        // Initial relationship score set to 0
        this.redCards.put(r, 0);
    }

    public RedCard chooseCard(boolean isContrarian) {
        // Returns best choice card or worst choice card based on bool
        int value = 0;
        List<RedCard> keysAsArray = new ArrayList<>(redCards.keySet());
        RedCard chosen;
        // Adding relationship value to each redCard in map
        for (RedCard red : keysAsArray) {
            
            // Relationship between adjective / noun
            value += 3 * valueOfRelationShip(this.greenCard.getID(), red.getID());

            // Relationship between adjective Synonyms / nouns (Worth 2x relation size)
            for (String syn : this.greenCard.getSynonyms()) {
                value += valueOfRelationShip(syn, red.getID());
            }

            // Relationship between adjective / flavour text
            value += valueOfRelationShip(this.greenCard.getID(), red.getFlavourText());

            // Relationship between adjective synonyms / flavour text is messes with relationship, not including

            // Add relationship value to map
            this.redCards.replace(red, value);
        }

        // Chooses red card
        if (isContrarian) { // Chooses worst card
            chosen = Collections.min(this.redCards.entrySet(), Map.Entry.comparingByValue()).getKey();
        } else { // chooses best card
            chosen = Collections.max(this.redCards.entrySet(), Map.Entry.comparingByValue()).getKey();
        }

        // removes card from hand and returns it
        this.redCards.remove(chosen);
        return chosen;
    }

    // Generates all patterns of a string of size k
    public List<String> subStringPatterns(String str, int k) {
        List<String> Patterns = new ArrayList<>();

        // Iterate the given string
        for(int i = 0; i < str.length()-k+1; i++)
            Patterns.add(str.substring(i, i+k));

        return Patterns;
    }

    // Finds length of largest substring pattern in another string
    public int valueOfRelationShip(String green, String red) {
        int k = green.length();
        List<String> patterns;
        for (int i = k; i > 2; i--) { // Not checking substrings of size 2 or less, not meaningful
            // Generates patterns of size i
            patterns = subStringPatterns(green, i);
            for (String p : patterns) {
                if (red.toLowerCase().contains(p.toLowerCase())) {
                    return i; // Returns length of pattern found in string
                }
            }


        }

        return 0; // No meaningful relationship between the two string

    }
}
