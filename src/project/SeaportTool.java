package project;

import project.GUI.ToolGUI;

/**
 * The main class of the seaport-tool. Launches the controller and the gui
 */
public class SeaportTool {

    public static void main(String[] args) {
    	ToolGUI gui = new ToolGUI();
    	ToolController controller = new ToolController();
    	
    	gui.setController(controller);
    	controller.setGUI(gui);
    	
    	new Thread(gui).start();
    	controller.run();
    }
}
