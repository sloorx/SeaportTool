import java.util.concurrent.LinkedBlockingDeque;

public class ToolController {

    private LinkedBlockingDeque<GUIEvent> eventQueue;

    public ToolController(){
        eventQueue = new LinkedBlockingDeque<GUIEvent>();
    }

    public void run(){

    }

    public void update(GUIEvent e){
        eventQueue.add(e);
    }
}
