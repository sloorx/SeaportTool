package project.solver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import project.Fleet;
import project.Quest;
import project.QuestSolver;
import project.Ship;
import project.Solution;
import project.Trip;
import project.Turn;

public class SolverCM implements QuestSolver {

	private ISolverCM solver;

	public SolverCM(ISolverCM solver) {
		this.solver = solver;
	}

	@Override
	public List<Solution> solve(Quest q) {

		List<String> resources = new ArrayList<>(q.getResources());
		List<Ship> ships = new ArrayList<Ship>(Fleet.getInstance().getShips());

		sortLists(q, resources, ships);

		List<List<Trip>> resourceTrips;
		List<List<Trip>> combinedTrips = new ArrayList<>();

		for (String resource : resources) {
			resourceTrips = new ArrayList<>();
			if (combinedTrips.isEmpty())
				generateTrips(resource, q.getValue(resource), ships, 0, new ArrayList<>(), combinedTrips);
			else {
				generateTrips(resource, q.getValue(resource), ships, 0, new ArrayList<>(), resourceTrips);
				combinedTrips = combineAllTrips(combinedTrips, resourceTrips);
			}
		}

		List<Solution> result = new ArrayList<Solution>();
		// add combined tripps to single solution
		for (List<Trip> trips : combinedTrips) {
			Solution solution = new Solution();
			for (Trip trip : trips) {
				solution.addTrip(trip);
			}
			result.add(solution);
		}

		return getSolutionsAndRemoveDuplicates(result, q);
	}

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

	private List<Solution> getSolutionsAndRemoveDuplicates(List<Solution> result, Quest q) {
		result = solver.getBestSolutions(result);

		List<Solution> uniqueSolutions = new ArrayList<>();
		for (Solution solution : result) {
			boolean isDuplicate = false;

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

	private List<List<Trip>> combineAllTrips(List<List<Trip>> trips1, List<List<Trip>> trips2) {
		List<List<Trip>> combinedTrips = new ArrayList<>();

		for (List<Trip> list1 : trips1) {
			for (List<Trip> list2 : trips2) {
				List<Trip> combinedList = new ArrayList<>(list1);
				combinedList.addAll(list2);
				combinedTrips.add(combinedList);
			}
		}

		return combinedTrips;
	}

	private void generateTrips(String resource, int amount, List<Ship> ships, int startIndex, List<Trip> currentTrips,
			List<List<Trip>> result) {

		if (amount == 0) {
			result.add(new ArrayList<>(currentTrips));
			return;
		}

		for (int i = startIndex; i < ships.size(); i++) {
			Ship ship = ships.get(i);
			int usedSpace = Math.min(amount, ship.getCapacity());
			currentTrips.add(new Trip(ship, resource, usedSpace));

			// split rest amount of resource to other ships
			generateTrips(resource, amount - usedSpace, ships, i, currentTrips, result);

			// reset to previous state, delete last added trip
			currentTrips.remove(currentTrips.size() - 1);
		}
	}

}
