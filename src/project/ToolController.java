package project;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import project.GUI.ToolGUI;
import project.solver.*;

public class ToolController {

	private LinkedBlockingDeque<GUIEvent> eventQueue;
	private Fleet fleet;
	private Quest quest;
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
		boolean retval;
		List<Solution> solutions = null;
		QuestSolver solver = null;
		while (true) {
			if (!eventQueue.isEmpty()) {
				event = eventQueue.poll();
				params = event.getParameters();
				
				switch (event.getType()) {
				case SHIP_ADDED:
					retval = fleet.addShip((String) params.get(0), ((Integer) params.get(1)).intValue(), ((Integer) params.get(2)).intValue());
					if (retval) {
						gui.updateGUI(event);
					} else {
						params = new ArrayList<>();
						params.add("Schiffname bereits in Verwendung");
						gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
					}
					break;

				case SHIP_EDITED:
					retval = fleet.editShip((String) params.get(0), (String) params.get(1), ((Integer) params.get(2)).intValue(), ((Integer) params.get(3)).intValue());
					if (retval) {
						gui.updateGUI(event);
					} else {
						params = new ArrayList<>();
						params.add("Unbekannter Schiffsname");
						gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
					}
					break;

				case SHIP_REMOVED:
					retval = fleet.removeShip((String) params.get(0), ((Integer) params.get(1)).intValue());
					if (retval) {
						gui.updateGUI(event);
					} else {
						params = new ArrayList<>();
						params.add("Unbekannter Schiffsname");
						gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
					}
					break;

				case RESOURCE_ADDED:
					retval = quest.addResource((String) params.get(0), (Integer) params.get(1));
					if (retval) {
						gui.updateGUI(event);
					} else {
						params = new ArrayList<>();
						params.add("Ressourcenname bereits in Verwendung");
						gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
					}
					break;

				case RESOURCE_EDITED:
					retval = quest.editResource((String) params.get(0), (Integer) params.get(1));
					if (retval) {
						gui.updateGUI(event);
					} else {
						params = new ArrayList<>();
						params.add("Unbekannter Ressourcenname");
						gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
					}
					break;

				case RESOURCE_REMOVED:
					retval = quest.removeResource((String) params.get(0));
					if (retval) {
						gui.updateGUI(event);
					} else {
						params = new ArrayList<>();
						params.add("Unbekannter Ressourcenname");
						gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
					}
					break;

				case SOLVE:
					switch ((SolverTypes) params.get(0)) {
					case CAPACITY_FH:
						// solver = new CapacitySolverFH();
						break;
					case TIME_FH:
						// solver = new TimeSolverFH();
						break;
					case CAPACITY_CM:
						// solver = new CapacitySolverCM();
						break;
					case TIME_CM:
						// solver = new TimeSolverCM();
						break;
					case CAPACITY_SL:
						solver = new CapacitySolverSL();
						break;
					case TIME_SL:
						// solver = new TimeSolverSL();
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
				this.update(null); //wait for updates
			}
		}
	}

	public synchronized void update(GUIEvent e) {
		if (e == null) {
			try {
				wait();
			} catch (InterruptedException e1) {}
		} else {
			eventQueue.add(e);
			notify();
		}
	}
}
