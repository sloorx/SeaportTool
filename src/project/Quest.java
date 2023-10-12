package project;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Quest {
    private Map<String, Integer> resources;

    public Quest(){
        resources = new HashMap<String, Integer>();
    }

    public boolean addResource(String resource, Integer amount){
        if(resources.containsKey(resource)){
            return false;
        }
        resources.put(resource, amount);
        return true;
    }

    public boolean editResource(String resource, Integer amount){
        if(resources.containsKey(resource)){
            resources.put(resource, amount);
            return true;
        }
        return false;
    }

    public boolean removeResource(String resource){
        if(resources.remove(resource) != null){
            return true;
        }
        return false;
    }

    public int getValue(String resource){
        return resources.get(resource);
    }

    public Set<String> getResources(){
        return resources.keySet();
    }
}
