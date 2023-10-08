import java.util.List;

public class GUIEvent {

    private EventTypes eventtype;
    private List<Object> parameters;

    public GUIEvent(EventTypes et, List<Object> params){
        this.eventtype = et;
        this.parameters = params;
    }

    public EventTypes getType(){
        return eventtype;
    }

    public List<Object> getParameters(){
        return parameters;
    }
}
