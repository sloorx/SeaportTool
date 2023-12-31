package project.solver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import project.Fleet;
import project.Quest;
import project.QuestSolver;
import project.Ship;
import project.Solution;
import project.Trip;

/**
 * @author Simon Loose
 * Calculates the solution for a quest with free capacity as the primary quality-factor and number of turns as the secondary quality-factor
 */
public class CapacitySolverSL implements QuestSolver {

    @Override
    public List<Solution> solve(Quest q) {
        List<Solution> solutions = new LinkedList<>();
        List<Solution> temp_solutions;
        List<List<Ship>> resourceSolution;
        Solution solution;
        Trip trip;
        int amount;
        int best;
        for(String r : q.getResources()){
            amount = q.getValue(r);
            //solve each resource individually
            resourceSolution = solveResource(amount);
            // if no solutions exist add resource solutions to result
            if(solutions.isEmpty()){
                for(List<Ship> sl : resourceSolution){
                    amount = q.getValue(r);
                    solution = new Solution();
                    for(Ship s : sl){
                        trip = new Trip(s, r, Integer.min(s.getCapacity(), amount));
                        amount -= s.getCapacity();
                        solution.addTrip(trip);
                    }
                    solutions.add(solution);
                }
            //else add resource solution to each solution
            }else{
                temp_solutions = new LinkedList<>();
                for(Solution sol : solutions){
                    for(List<Ship> sl : resourceSolution){
                        amount = q.getValue(r);
                        solution = new Solution(sol);
                        for(Ship s : sl){
                            trip = new Trip(s, r, Integer.min(s.getCapacity(), amount));
                            amount -= s.getCapacity();
                            solution.addTrip(trip);
                        }
                        temp_solutions.add(solution);
                    }
                }
                solutions = temp_solutions;
            }
        }
        // find best solution by secondary condition
        best = Integer.MAX_VALUE;
        for(Solution s : solutions){
            if(s.getTurnCount() < best){
                best = s.getTurnCount();
            }
        }
        // return only best solutions
        temp_solutions = new LinkedList<Solution>();
        for(Solution s : solutions){
            if(s.getTurnCount() == best){
                if(!temp_solutions.contains(s)){        // check for duplicates
                    temp_solutions.add(s);
                }
            }
        }
        return temp_solutions;
    }
    
    /**
     * Calculates the best solution (based on unused capacity) for a single resource
     * @param amount the amount of the resource to distribute to ships
     * @return a list of ship combinations that can transport the given amount of a resource
     */
    private List<List<Ship>> solveResource(int amount){
        List<List<Ship>> ret = new ArrayList<>();
        List<List<Ship>> temp;
        List<Ship> shipList;
        Fleet fleet = Fleet.getInstance();
        int deltaCap, sum;
        int best = Integer.MIN_VALUE;

        for(Ship s : fleet.getShips()){
            deltaCap = amount - s.getCapacity();    // deltaCap > 0: resource remaining, < 0: capacity remaining
            if( deltaCap > 0){                      // More resource than fits on the ship?
                temp = solveResource(deltaCap);     // -> recursion
                shipList = temp.get(0);
                sum = 0;
                for(Ship t : shipList){
                    sum += t.getCapacity();
                }
                deltaCap -= sum;
                if(deltaCap >= best){               // Only add better/equal solutions
                    for(List<Ship> sl : temp){
                        shipList = new ArrayList<>(sl);
                        shipList.add(s);
                        ret.add(shipList);
                    }
                    best = deltaCap;
                }
            }else{
                if(deltaCap >= best){               // Only add better/equal solutions
                    shipList = new ArrayList<>();
                    shipList.add(s);
                    ret.add(shipList);
                    best = deltaCap;
                }
            }
        }
        temp = new ArrayList<>();
        for(List<Ship> sl : ret){                   // remove all solutions that are worse than the best
            sum = 0;
            for(Ship s : sl){
                sum += s.getCapacity();
            }
            if(amount - sum == best){
                temp.add(sl);
            }
        }
        return temp;
    }
}
