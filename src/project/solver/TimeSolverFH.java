package project.solver;

import project.*;

import java.util.*;

public class TimeSolverFH implements QuestSolver {

    @Override
    public List<Solution> solve(Quest q) {
        SummaryFH summary = new SummaryFH();
        Map<String, Integer> resources = q.getQuest();
        List<List<String>> resourcesPermutations = summary.getAllResourcePermut(resources);
        List<Ship> fleet = Fleet.getInstance().getShips().stream().toList();
        List<Solution> solutions = new ArrayList<>();
        for (List<String> orderResources : resourcesPermutations) {
            Solution solution = new Solution();
            solution = findSolutions(resources, fleet, solution, orderResources);
            solutions = summary.addSolution(solutions,solution);
        }
        return solutions;
    }

    private Solution findSolutions(Map<String, Integer> resources, List<Ship> fleet, Solution solution, List<String> orderResources) {
        Map<String, Integer> copyResource = new HashMap<>(resources);
        List<Ship> copyFleet = new ArrayList<>(fleet);
        List<String> copyOrder = new ArrayList<>(orderResources);
        Turn turn = new Turn();
        solution.addTurn(turn);
        for (String resourceStr : orderResources) {
            int mengeResour = resources.get(resourceStr);
            Trip trip = null;
            Ship divisibleShip = null;
            int divTimes = 0;
            for (Ship ship : copyFleet) {
                if (mengeResour == ship.getCapacity()) {
                    trip = getTrip(copyResource, copyFleet, copyOrder, resourceStr, mengeResour, ship);
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
            getMoreTripsInTheSameTurn(solution, copyResource, copyFleet, copyOrder);
        }
        if (copyResource.size() > 0)
            solution = findSolutions(copyResource, fleet, solution, copyOrder);
        return solution;
    }

    private Trip getTrip(Map<String, Integer> copyResource, List<Ship> copyFleet, List<String> copyOrder, String resourceStr, int mengeResour, Ship ship) {
        Trip trip = new Trip(ship, resourceStr, mengeResour);
        copyResource.remove(resourceStr);
        copyFleet.remove(ship);
        copyOrder.remove(resourceStr);
        return trip;
    }

    private void getMoreTripsInTheSameTurn(Solution solution, Map<String, Integer> copyResource, List<Ship> copyFleet, List<String> copyOrder) {
        for (String resourceStr : copyResource.keySet()) {
            if (copyFleet.size() == 0)
                break;
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
                trip = getTrip(copyResource, copyFleet, copyOrder, resourceStr, mengeResour, overShip);
            } else {
                trip = new Trip(underShip, resourceStr, underShip.getCapacity());
                copyResource.replace(resourceStr, (mengeResour - underShip.getCapacity()));
                copyFleet.remove(underShip);
            }
            solution.addTripFH(trip);
        }
    }

}
