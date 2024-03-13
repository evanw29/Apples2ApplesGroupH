import java.util.List;

public class GreenCard {
    String adjective;
    List<String> synonyms;

    GreenCard(String adjective, List<String> synonyms){
        this.adjective = adjective;
        this.synonyms = synonyms;
    }

    public void setAdjective(String adjective) {
        this.adjective = adjective;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public String getAdjective() {
        return this.adjective;
    }

    public List<String> getSynonyms() {
        return this.synonyms;
    }

    @Override
    public String toString() {
        return this.adjective + " " + this.synonyms;
    }
}
