package project.solver;

import project.Solution;
import project.Trip;
import project.Turn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SummaryFH {
    private List<List<String>> allResourcePermut;

    public List<List<String>> getAllResourcePermut(Map<String, Integer> resources) {
        allResourcePermut = new ArrayList<>();
        Map<String, Integer> copyResources = new HashMap<>(resources);
        List<String> keysRes = new ArrayList<>(copyResources.keySet().stream().toList());
        int level = 0;
        for (String s : keysRes) {
            permutationAllResources(keysRes, level, s);
        }
        return allResourcePermut.stream().distinct().toList();
    }

    private void permutationAllResources(List<String> keysRes, int level, String s) {
        List<String> copyKeys = new ArrayList<>(keysRes);
        copyKeys.remove(s);
        copyKeys.add(level, s);
        if (level == keysRes.size() - 1) {
            allResourcePermut.add(copyKeys);
        } else {
            level++;
            for (String res : copyKeys) {
                permutationAllResources(copyKeys, level, res);
            }
        }
    }

    public List<Solution> addSolution(List<Solution> solutions, Solution solution) {
        if (solutions.isEmpty())
            solutions.add(solution);
        if (((solutions.get(0).getTurnCount() == solution.getTurnCount()) && (solutions.get(0).getFreeCapacity() == solution.getFreeCapacity()))) {
            solutions = compareAndAdd(solutions, solution);
        } else if ((solutions.get(0).getTurnCount() >= solution.getTurnCount()) && (solutions.get(0).getFreeCapacity() >= solution.getFreeCapacity())) {
            solutions.removeAll(solutions);
            solutions.add(solution);
        }
        return solutions;
    }

    private List<Solution> compareAndAdd(List<Solution> solutions, Solution solution) {
        Boolean esDiferente = false;
        for(Solution s : solutions) {
            List<Turn> turnsOrigin = s.getTurns();
            List<Turn> turnsNew = solution.getTurns();
            for (int i = 0; i < turnsOrigin.size(); i++) {
                List<Trip> tripsOrigin = turnsOrigin.get(i).getTrips();
                List<Trip> tripsNew = turnsNew.get(i).getTrips();
                for (Trip trip : tripsOrigin) {
                    for (Trip tripN : tripsNew) {
                        if ((trip.getShip().getName().matches(tripN.getShip().getName()) && (trip.getResource() != tripN.getResource())))
                            esDiferente = true;
                    }
                }
            }
        }
        if (esDiferente) {
            solutions.add(solution);
        }
        return solutions;
    }

}