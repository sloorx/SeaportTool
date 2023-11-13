package project.solver;

import project.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeSolverFH implements QuestSolver {

    @Override
    public List<Solution> solve(Quest q) {
        Map<String, Integer> resources = q.getQuest();
        List<Ship> fleet = Fleet.getInstance().getShips().stream().toList();

        List<Solution> solutions = new ArrayList<>();
        Solution solution = new Solution();
        solution = findSolutions(resources, fleet, solution);
        solutions.add(solution);

        return solutions;
    }

    private Solution findSolutions(Map<String, Integer> resources, List<Ship> fleet, Solution solution) {
        Map<String, Integer> copyResource = new HashMap<>(resources);
        List<Ship> copyFleet = new ArrayList<>(fleet);
        Turn turn = new Turn();
        solution.addTurn(turn);
        for (String resourceStr : resources.keySet()) {
            int mengeResour = resources.get(resourceStr);
            Trip trip = null;
            Ship divisibleShip = null;
            int divTimes = 0;
            for (Ship ship : copyFleet) {
                if (mengeResour == ship.getCapacity()) {
                    trip = new Trip(ship, resourceStr, mengeResour);
                    copyResource.remove(resourceStr);
                    copyFleet.remove(ship);
                    break;
                } else if ((mengeResour % ship.getCapacity()) == 0) {
                    if (divisibleShip == null) {
                        divisibleShip = ship;
                        divTimes = mengeResour / ship.getCapacity();
                    } else if (divTimes > (mengeResour / ship.getCapacity())) {
                        divisibleShip = ship;
                        divTimes = mengeResour / ship.getCapacity();
                    }
                }
            }
            if (trip == null && divisibleShip != null && divTimes != 0) {
                trip = new Trip(divisibleShip, resourceStr, divisibleShip.getCapacity());
                copyResource.replace(resourceStr, (mengeResour - divisibleShip.getCapacity()));
                copyFleet.remove(divisibleShip);
            }
            if (trip != null)
                solution.addTripFH(trip);
        }
        if (copyFleet.size() > 0 && copyResource.size() > 0) {
            for (String resourceStr : copyResource.keySet()) {
                int mengeResour = copyResource.get(resourceStr);
                Ship underShip = null;
                Ship overShip = null;
                Trip trip;
                for (Ship ship : copyFleet) {
                    if (ship.getCapacity() > mengeResour) {
                        if (overShip == null || (ship.getCapacity() < overShip.getCapacity()))
                            overShip = ship;
                    } else {
                        if (underShip == null || (ship.getCapacity() > underShip.getCapacity()))
                            underShip = ship;
                    }
                }
                if (overShip != null) {
                    trip = new Trip(overShip, resourceStr, mengeResour);
                    copyResource.remove(resourceStr);
                    copyFleet.remove(overShip);
                } else {
                    trip = new Trip(underShip, resourceStr, underShip.getCapacity());
                    copyResource.replace(resourceStr, (mengeResour - underShip.getCapacity()));
                    copyFleet.remove(overShip);
                }
                solution.addTripFH(trip);
            }
        }

        return solution;
    }
}
