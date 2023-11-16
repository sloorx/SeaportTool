package project;

import java.util.List;

/**
 * Events for communication between ToolController and ToolGUI
 */
public class GUIEvent {

    private EventTypes eventtype;
    private List<Object> parameters;

    /**
     * Creates a new GUIEvent
     * @param et The type of event
     * @param params a list of parameters. Amount and type depend on the eventtype
     */
    public GUIEvent(EventTypes et, List<Object> params){
        this.eventtype = et;
        this.parameters = params;
    }

    /**
     * Returns the eventtype
     * @return
     */
    public EventTypes getType(){
        return eventtype;
    }

    /**
     * Returns the list of parameters
     * @return
     */
    public List<Object> getParameters(){
        return parameters;
    }
}
