import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RedCard implements Card {
    final String noun; // Unique identifier
    final String flavourText;
    List<String> synonyms;

    RedCard(String line) {
        List<String> arguments = new ArrayList<>(Arrays.asList(line.split("&")));
        this.noun = arguments.get(0).toLowerCase().trim();
        this.flavourText = arguments.get(1).toLowerCase();
    }

    public String getID(){ return this.noun; }

    public String getFlavourText() {
        return this.flavourText;
    }

    public List<String> getSynonyms() {
        // Returns shallow copy of list
        return new ArrayList<>(this.synonyms);
    }

    public void removeSynonym(String synonym) {
        this.synonyms.remove(synonym);
    }

    @Override
    public String toString() {
        return this.noun + ": " + this.flavourText;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof RedCard)) return false;

        return ((RedCard) o).getID().equals(this.getID());
    }

    public int hashCode() {
        return Objects.hashCode(this.noun);
    }

}
