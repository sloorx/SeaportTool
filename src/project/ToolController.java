package project;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import project.GUI.ToolGUI;

public class ToolController {

	private LinkedBlockingDeque<GUIEvent> eventQueue;
	private Fleet fleet;
	private Quest quest;
	private Thread thread;
	private ToolGUI gui;

	public ToolController() {
		eventQueue = new LinkedBlockingDeque<GUIEvent>();
		fleet = Fleet.getInstance();
		quest = new Quest();
	}

	public void setGUI(ToolGUI gui) {
		this.gui = gui;
	}

	public void run() {
		GUIEvent event;
		List<Object> params;
		thread = Thread.currentThread();
		boolean retval;
		List<Solution> solutions = null;
		QuestSolver solver = null;
		while (true) {
			if (!eventQueue.isEmpty()) {
				event = eventQueue.poll();
				params = event.getParameters();
				
				switch (event.getType()) {
				case SHIP_ADDED:
					retval = fleet.addShip((String) params.get(0), ((Integer) params.get(1)).intValue(), ((Integer) params.get(2)));
					
					if (retval) {
						gui.updateGUI(event);
					} else {
						params = new ArrayList<>();
						params.add("Schiffname bereits in Verwendung");
						gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
					}
					break;
				case SHIP_EDITED:
					retval = fleet.editShip((String) params.get(0), (String) params.get(1), ((Integer) params.get(2)).intValue());
					
					if (retval) {
						gui.updateGUI(event);
					} else {
						params = new ArrayList<>();
						params.add(""); // TODO
						gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
					}
					break;
				case SHIP_REMOVED:
					retval = fleet.removeShip((String) params.get(0), ((Integer) params.get(1)).intValue());
					
					if (retval) {
						gui.updateGUI(event);
					} else {
						params = new ArrayList<>();
						params.add(""); // TODO
						gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
					}
					break;
				case RESOURCE_ADDED:
					retval = quest.addResource((String) params.get(0), (Integer) params.get(1));
					
					if (retval) {
						gui.updateGUI(event);
					} else {
						params = new ArrayList<>();
						params.add(""); // TODO
						gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
					}
					break;
				case RESOURCE_EDITED:
					retval = quest.editResource((String) params.get(0), (Integer) params.get(1));
					
					if (retval) {
						gui.updateGUI(event);
					} else {
						params = new ArrayList<>();
						params.add(""); // TODO
						gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
					}
					break;
				case RESOURCE_REMOVED:
					retval = quest.removeResource((String) params.get(0));
					
					if (retval) {
						gui.updateGUI(event);
					} else {
						params = new ArrayList<>();
						params.add(""); // TODO
						gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
					}
					break;
				case SOLVE:
					switch ((SolverTypes) params.get(0)) {
					case CAPACITY_FH:
						// TODO
						break;
					case TIME_FH:
						// TODO
						break;
					case CAPACITY_CM:
						// TODO
						break;
					case TIME_CM:
						// TODO
						break;
					case CAPACITY_SL:
						solver = new CapacitySolverSL();
						break;
					case TIME_SL:
						// TODO
						break;
					default:
						break;
					}
					if (solver != null) {
						solutions = solver.solve(quest);
						params = new ArrayList<>(solutions);
						gui.updateGUI(new GUIEvent(EventTypes.SOLUTION_ADDED, params));
					}
					break;
				case SAVE:
					// TODO
					break;
				case LOAD:
					// TODO
					break;
				case EXIT:
					System.exit(0);
					break;
				default:
					break;
				}
			} else {
				update(null); //TODO man kann null nicht an LinkedBlockingDeque übergeben, sonst wird eine Exception geworfen (siehe ToolGUI), 
								//stattdessen neuer Eventtype Empty?
			}
		}
	}

	public synchronized void update(GUIEvent e) {
		if (Thread.currentThread().equals(this.thread)) {
			try {
				wait();
			} catch (InterruptedException e1) {
			}
			;
		} else {
			eventQueue.add(e);
			notify();
		}
	}
}
