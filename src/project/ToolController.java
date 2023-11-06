package project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

import project.GUI.ToolGUI;
import project.solver.*;

public class ToolController {

    private LinkedBlockingDeque<GUIEvent> eventQueue;
    private Fleet fleet;
    private Quest quest;
    private ToolGUI gui;
    private List<GUIEvent> loadEvents;

    public ToolController() {
        eventQueue = new LinkedBlockingDeque<GUIEvent>();
        fleet = Fleet.getInstance();
        quest = new Quest();
        loadEvents = new ArrayList<>();
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
                        retval = fleet.removeShip((String) params.get(0));
                        if (retval) {
                            gui.updateGUI(event);
                        } else {
                            params = new ArrayList<>();
                            params.add("Unbekannter Schiffsname");
                            gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
                        }
                        break;

                    case RESOURCE_ADDED:
                        retval = quest.addResource((String) params.get(0), (Integer) (params.get(1)));
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
                                solver = new CapacitySolverFH();
                                break;
                            case TIME_FH:
                                solver = new TimeSolverFH();
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
                        retval = this.save((String) params.get(0));
                        if (retval) {
                            gui.updateGUI(event);
                        } else {
                            params = new ArrayList<>();
                            params.add("Fehler beim Speichern");
                            gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
                        }
                        break;

                    case LOAD:
                        loadEvents.clear();
                        retval = this.load((String) params.get(0));
                        if (retval) {
                            for (GUIEvent g : loadEvents) {
                                gui.updateGUI(g);
                            }
                            gui.updateGUI(event);
                        } else {
                            params = new ArrayList<>();
                            params.add("Fehler beim Laden");
                            gui.updateGUI(new GUIEvent(EventTypes.ERROR, params));
                        }
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
            } catch (InterruptedException e1) {
            }
        } else {
            eventQueue.add(e);
            notify();
        }
    }

    private boolean save(String path) {
        List<String> lines = new ArrayList<>();
        Collection<Ship> ships = fleet.getShips();
        for (Ship s : ships) {
            lines.add("ship");
            lines.add(s.getName());
            lines.add(String.valueOf(s.getCapacity()));
            lines.add(String.valueOf(s.getAmount()));
        }
        for (String s : quest.getResources()) {
            lines.add("resource");
            lines.add(s);
            lines.add(String.valueOf(quest.getValue(s)));
        }
        try {
            Files.write(Paths.get(path), lines);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean load(String path) {
        List<String> lines = null;
        int idx = 0;
        boolean retval = true;
        List<Object> params;
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            return false;
        }
        if (lines != null) {
            fleet.clear();
            quest.clear();
            try {
                while (idx < lines.size()) {
                    if (lines.get(idx).equals("ship")) {
                        retval = fleet.addShip(lines.get(idx + 1), Integer.parseInt(lines.get(idx + 2)), Integer.parseInt(lines.get(idx + 3)));
                        params = new ArrayList<>();
                        params.add(lines.get(idx + 1));
                        params.add(Integer.valueOf(lines.get(idx + 2)));
                        params.add(Integer.valueOf(lines.get(idx + 3)));
                        loadEvents.add(new GUIEvent(EventTypes.SHIP_ADDED, params));
                        idx += 4;
                        if (!retval) {
                            fleet.clear();
                            return false;
                        }
                    } else if (lines.get(idx).equals("resource")) {
                        quest.addResource(lines.get(idx + 1), Integer.parseInt(lines.get(idx + 2)));
                        idx += 3;
                    } else {
                        idx++;
                    }
                }
            } catch (Exception e) {
                fleet.clear();
                quest.clear();
                return false;
            }
            return true;
        }
        return false;
    }

    public Map<String, Integer> getQuest() {
        return quest.getQuest();
    }
}
