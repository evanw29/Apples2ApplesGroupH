import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GreenCard implements Card{

    //private final HashMap<String, List<String>> synonymMap = new HashMap<String, List<String>>();

    private final List<String> synonymList = new ArrayList<>();

    private String adjective;

    public GreenCard(String adjective){

        this.adjective = adjective;

    }

}
