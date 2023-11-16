package project;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The quest class contains the list of required resources and offers methods for managing them.
 */
public class Quest {
    private Map<String, Integer> resources;

    public Quest(){
        resources = new HashMap<String, Integer>();
    }

    /**
     * Adds a resource to a quest if it does not already exist
     * @param resource The resource name
     * @param amount The amount
     * @return False if the quest already contained the resource, true otherwise
     */
    public boolean addResource(String resource, Integer amount){
        if(resources.containsKey(resource)){
            return false;
        }
        resources.put(resource, amount);
        return true;
    }

    /**
     * Changes the amount of a resource
     * @param resource The name of the resource
     * @param amount the new amount
     * @return True if the resource existed, false otherwise
     */
    public boolean editResource(String resource, Integer amount){
        if(resources.containsKey(resource)){
            resources.put(resource, amount);
            return true;
        }
        return false;
    }

    /**
     * Removes a resource from the quest
     * @param resource The name of the resource
     * @return True if the resource existed and was removed, false otherwise
     */
    public boolean removeResource(String resource){
        if(resources.remove(resource) != null){
            return true;
        }
        return false;
    }

    /**
     * Returns the amount of a given resource
     * @param resource The name of the resource
     * @return
     */
    public int getValue(String resource){
        return resources.get(resource);
    }

    /**
     * Returns a Set containing the name of all resources
     * @return
     */
    public Set<String> getResources(){
        return resources.keySet();
    }

    /**
     * Overrides the list of resources with a new list
     * @param resources
     */
    public void setResources(Map<String, Integer> resources){
        this.resources = resources;
    }

    /**
     * Removes all resources from the quest
     */
    public void clear(){
        resources.clear();
    }

    /**
     * Returns a map containing all resource-amount combinations of the quest
     * @return
     */
    public Map<String, Integer> getQuest(){
        return resources;
    }
}
