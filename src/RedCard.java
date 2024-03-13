import java.util.ArrayList;
import java.util.List;

public class RedCard {
    String noun;
    String flavourText;
    List<String> synonyms;

    RedCard(String noun, String flavourText, List<String> synonyms){
        this.noun = noun;
        this.flavourText = flavourText;
        this.synonyms = synonyms;
    }
    RedCard(String noun, List<String> synonyms){
        this.noun = noun;
        this.synonyms = synonyms;
    }

    public void setNoun(String noun) {
        this.noun = noun;
    }

    public void setFlavourText(String flavourText) {
        this.flavourText = flavourText;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }
    
    public String getNoun() {
        return this.noun;
    }

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
}
