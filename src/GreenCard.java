import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class GreenCard {
    String adjective;
    List<String> synonyms;

    GreenCard(String adjective, List<String> synonyms) throws FileNotFoundException {
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
