package project;

import java.util.concurrent.LinkedBlockingDeque;

public class ToolController {

    private LinkedBlockingDeque<GUIEvent> eventQueue;
    private Fleet fleet;
    private Quest quest;

    public ToolController(){
        eventQueue = new LinkedBlockingDeque<GUIEvent>();
        fleet = Fleet.getInstance();
        quest = new Quest();
    }

    public void run(){
        GUIEvent event;
        while(true){
            if(!eventQueue.isEmpty()){
                event = eventQueue.poll();
                switch (event.getType()) {
                    case SHIP_ADDED:
                        break;
                    case SHIP_EDITED:
                        break;
                    case SHIP_REMOVED:
                        break;
                    case RESOURCE_ADDED:
                        break;
                    case RESOURCE_EDITED:
                        break;
                    case RESOURCE_REMOVED:
                        break;
                    case SOLVE:
                        break;
                    case SAVE:
                        break;
                    case LOAD:
                        break;
                    case EXIT:
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void update(GUIEvent e){
        eventQueue.add(e);
    }
}
