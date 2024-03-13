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
        return this.synonyms;
    }

    @Override
    public String toString() {
        return this.noun + " " + this.flavourText;
    }
}
