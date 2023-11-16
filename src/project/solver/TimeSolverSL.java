package project.solver;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import project.Fleet;
import project.Quest;
import project.QuestSolver;
import project.Ship;
import project.Solution;
import project.Trip;


/**
 * @author Simon Loose
 * Calculates the solution for a quest with number of turns as the primary quality-factor and unused capacity as the secondary quality-factor
 */
public class TimeSolverSL implements QuestSolver {

    private Quest quest;

    @Override
    public List<Solution> solve(Quest q) {
        List<Solution> result = new LinkedList<>();
        List<Solution> temp;
        PriorityQueue<Solution> solutions = new PriorityQueue<>(new TimeComparator());
        Solution ref = null;
        Solution next = null;
        TimeComparator comp = new TimeComparator();
        this.quest = q;

        solutions.add(new Solution());                          // start with empty solution
        while(!solutions.isEmpty()){
            next = solutions.poll();                            // get current best from queue
            if(ref != null){
                if(comp.compare(ref, next) < 0){                // skip solution if it is worse than known solution that solves quest
                    continue;
                }
            }
            temp = expandSolution(next, ref);                   // add all ship-resource combinations to the solution
            for(Solution s: temp){
                if(s.solves(q)){                                // solves the quest?
                    if(ref != null){
                        if(comp.compare(s, ref) <= 0){          // better or equal than previous best?
                            if(comp.compare(s, ref) < 0){       // better? -> discard previous solutions
                                ref = s;
                                result.clear();
                            }
                            result.add(s);
                        }
                    }else{
                        ref = s;
                        result.add(s);
                    }
                }else{
                    solutions.add(s);
                }
            }
        }
        temp = result;
        result = new LinkedList<>();
        for(Solution s: temp){                                  // remove duplicates/permutations
            if(!result.contains(s)){
                result.add(s);
            }
        }
        return result;
    }
    
    /**
     * Expands a given solution with all possible resource-ship combinations
     * @param s the solution to expand
     * @param solvedReference a finished solution to filter out results that are worse than this solution or null if no solution is finished
     * @return a list of soultions based on the given solution each with one resource-ship-combination added
     */
    private List<Solution> expandSolution(Solution s, Solution solvedReference){
        List<Solution> solutions = new LinkedList<>();
        Fleet fleet = Fleet.getInstance();
        int amount;
        Solution temp;
        TimeComparator comp = new TimeComparator();
        for(String r: quest.getResources()){
            amount = quest.getValue(r) - s.getTransportedAmount(r);
            for(Ship ship: fleet.getShips()){
                temp = new Solution(s);
                temp.addTrip(new Trip(ship, r, Math.min(amount, ship.getCapacity())));
                if(solvedReference != null){
                    if(comp.compare(temp, solvedReference) <= 0){
                        solutions.add(temp);
                    }
                }else{
                    solutions.add(temp);
                }
            }
        }
        return solutions;
    }


    private class TimeComparator implements Comparator<Solution>{

        @Override
        public int compare(Solution s1, Solution s2) {
            if(s1.getTurnCount() < s2.getTurnCount()){
                return -1;
            }else if (s1.getTurnCount() > s2.getTurnCount()) {
                return 1;
            }else{
                if(s1.getFreeCapacity() < s2.getFreeCapacity()){
                    return -1;
                }else if (s1.getFreeCapacity() > s2.getFreeCapacity()) {
                    return 1;
                }else{
                    return 0;
                }
            }
        }

    }
}
