import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GreenCard implements Card {
    final String adjective;
    List<String> synonyms;

    GreenCard(String line) {
        List<String> arguments = new ArrayList<>(Arrays.asList(line.split("&")));
        this.adjective = arguments.get(0).toLowerCase().trim();
        this.synonyms = new ArrayList<>(Arrays.asList(arguments.get(1).trim().split(", ")));
    }

    public String getID(){ return this.adjective; }

    public void addSynonyms(List<String> s) {
        // In case of duplicate key, adds unique synonyms from duplicate here.
        for (String syn : s) {
            if (!this.synonyms.contains(syn)) {
                this.synonyms.add(syn);
            }
        }

    }

    public List<String> getSynonyms() {
        // returns shallow copy of list
        return new ArrayList<>(this.synonyms);
    }

    @Override
    public String toString() {
        return this.adjective + ": " + this.synonyms;
    }

}
