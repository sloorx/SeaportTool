import java.util.Map;
import java.util.Set;

public class Quest {
    private Map<String, Integer> ressources;

    public Quest(){
    }

    public boolean addRessource(String ressourse, Integer amount){
        return true;
    }

    public boolean editRessource(String ressourse, Integer amount){
        return true;
    }

    public boolean removeRessource(String ressourse){
        return true;
    }

    public int getValue(String ressource){
        return 0;
    }

    public Set<String> getRessources(){
        return null;
    }
}
