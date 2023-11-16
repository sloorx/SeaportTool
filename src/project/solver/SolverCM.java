package project.solver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import project.Fleet;
import project.Quest;
import project.QuestSolver;
import project.Ship;
import project.Solution;
import project.Trip;

/**
 * @author Christoph Mehlis
 * 
 *         The class implements the QuestSolver interface to solve quests using
 *         different solving strategies. It can be initialized with various
 *         implementations of the ISolverCM interface to control the solving
 *         behavior.
 */
public class SolverCM implements QuestSolver {

	private ISolverCM solver;
	private boolean isTimeSolver;

	/**
	 * Constructor for the SolverCM class.
	 * 
	 * @param solver An implementation of the ISolverCM interface to determine the
	 *               solving strategy.
	 */
	public SolverCM(ISolverCM solver) {
		this.solver = solver;
		isTimeSolver = solver instanceof TimeSolverCM;
	}

	/**
	 * Solves the given quest by generating and combining trips for each resource,
	 * considering the solving strategy.
	 * 
	 * @param q The quest to be solved.
	 * @return A list of unique solutions for the given quest.
	 */
	@Override
	public List<Solution> solve(Quest q) {

		List<String> resources = new ArrayList<>(q.getResources());
		List<Ship> ships = new ArrayList<Ship>(Fleet.getInstance().getShips());

		sortLists(q, resources, ships);

		List<List<Trip>> resourceTrips = new ArrayList<>();		
		List<List<Trip>> combinedTrips = new ArrayList<>();

		// generate trips for each resource
		for (String resource : resources) {
			if (combinedTrips.isEmpty())
				generateTrips(resource, q.getValue(resource), ships, new ArrayList<>(), combinedTrips,
						Integer.MAX_VALUE);
			else {
				generateTrips(resource, q.getValue(resource), ships, new ArrayList<>(), resourceTrips,
						Integer.MAX_VALUE);
				
				// combine all trips from previouse ressources with trips of current ressource
				combinedTrips = combineAllTrips(combinedTrips, resourceTrips);
				resourceTrips.clear();
			}
		}

		List<Solution> result = new ArrayList<Solution>();
		// add combined trips to single solutions
		for (List<Trip> trips : combinedTrips) {
			Solution solution = new Solution();
			
			for (Trip trip : trips) 
				solution.addTrip(trip);
			
			result.add(solution);
		}

		return getSolutionsAndRemoveDuplicates(result, q);
	}

	/**
	 * Sorts the lists of resources and ships according to certain criteria in order
	 * to possibly speed up the creation of the solution.
	 * 
	 * @param q         The quest being solved.
	 * @param resources The list of resources to be sorted.
	 * @param ships     The list of ships to be sorted.
	 */
	private void sortLists(Quest q, List<String> resources, List<Ship> ships) {
		resources.sort(new Comparator<String>() {

			@Override
			public int compare(String r1, String r2) {
				int c1 = q.getValue(r1);
				int c2 = q.getValue(r2);

				return (c1 == c2) ? 0 : (c1 < c2) ? 1 : -1;
			}
		});

		ships.sort(new Comparator<Ship>() {

			@Override
			public int compare(Ship s1, Ship s2) {
				int a1 = s1.getAmount();
				int a2 = s2.getAmount();
				int c1 = s1.getCapacity() * a1;
				int c2 = s2.getCapacity() * a2;

				return (c1 == c2) ? ((a1 < a2) ? 0 : 1) : (c1 < c2) ? 1 : -1;
			}
		});
	}

	/**
	 * Filters the solutions based on specific criteria and ensures uniqueness.
	 * 
	 * @param result The list of solutions to be filtered.
	 * @param q      The quest being solved.
	 * @return A list of unique solutions that solve the quest.
	 */
	private List<Solution> getSolutionsAndRemoveDuplicates(List<Solution> result, Quest q) {
		result = solver.getBestSolutions(result);

		List<Solution> uniqueSolutions = new ArrayList<>();
		boolean isDuplicate;

		for (Solution solution : result) {
			isDuplicate = false;

			for (Solution uniqueSolution : uniqueSolutions) {
				if (solution.equals(uniqueSolution)) {
					isDuplicate = true;
					break;
				}
			}

			if ((!isDuplicate) && (solution.solves(q)))
				uniqueSolutions.add(solution);
		}

		return uniqueSolutions;
	}

	/**
	 * Combines two lists of trips into a new list of trips.
	 *  Removes duplicate trips from the combined list.
	 * 
	 * @param trips1 The first list of trips.
	 * @param trips2 The second list of trips.
	 * @return A list of unique trips combined from the input lists.
	 */
	private List<List<Trip>> combineAllTrips(List<List<Trip>> trips1, List<List<Trip>> trips2) {
		List<List<Trip>> combinedTrips = new ArrayList<>();

		for (List<Trip> list1 : trips1) {
			for (List<Trip> list2 : trips2) {
				List<Trip> combinedList = new ArrayList<>(list1);
				combinedList.addAll(list2);
				
				// Remove duplicates using equals method of Trip class
	            combinedList = combinedList.stream().distinct().collect(Collectors.toList());

				combinedTrips.add(combinedList);
			}
		}

		// Remove duplicates from the final combined list
	    combinedTrips = combinedTrips.stream().distinct().collect(Collectors.toList());

		return combinedTrips;
	}

	/**
	 * Generates trips for a given resource and amount, considering the solving
	 * strategy.
	 * 
	 * @param resource        The resource for which trips are generated.
	 * @param amount          The amount of the resource to be covered.
	 * @param ships           The list of available ships.
	 * @param currentTrips    The current list of trips being generated.
	 * @param result          The list to store the generated trips.
	 * @param minFreeCapacity The minimum free capacity criterion.
	 */
	private void generateTrips(String resource, int amount, List<Ship> ships, List<Trip> currentTrips,
			List<List<Trip>> result, int minFreeCapacity) {

		// resource is completely divided
		if (amount == 0) {
			// check if trips have bigger value of free capacity
			int currentFreeCapacity = calculateFreeCapacity(currentTrips);
			if ((currentFreeCapacity <= minFreeCapacity) || (isTimeSolver))
				result.add(new ArrayList<>(currentTrips));

			return;
		}

		for (Ship ship : ships) {
			int usedSpace = Math.min(amount, ship.getCapacity());

			// skip trip, if free capacity is bigger than minFreeCapacity
//			if ((ship.getCapacity() - usedSpace > minFreeCapacity) && (!isTimeSolver))
//				continue;

			currentTrips.add(new Trip(ship, resource, usedSpace));

			// split rest amount of resource to other ships
			generateTrips(resource, amount - usedSpace, ships, currentTrips, result,
					calculateFreeCapacity(currentTrips));

			// reset to previous state, delete last added trip
			currentTrips.remove(currentTrips.size() - 1);
		}
	}

	/**
	 * Calculates the total free capacity of a list of trips.
	 * 
	 * @param trips The list of trips for which free capacity is calculated.
	 * @return The total free capacity.
	 */
	private int calculateFreeCapacity(List<Trip> trips) {
		int totalCapacity = 0;
		int usedCapacity = 0;

		for (Trip trip : trips) {
			totalCapacity += trip.getShip().getCapacity();
			usedCapacity += trip.getAmount();
		}

		return totalCapacity - usedCapacity;
	}

}
