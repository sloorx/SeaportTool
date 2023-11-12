package project;

import java.util.List;
import java.util.ArrayList;

public class Solution {
	private List<Turn> turns;
	private int freeCapacity;

	public Solution() {
		turns = new ArrayList<Turn>();
		freeCapacity = 0;
	}

	public Solution(Solution s) {
		turns = new ArrayList<>();
		for (Turn t : s.getTurns()) {
			turns.add(new Turn(t));
		}
		freeCapacity = s.getFreeCapacity();
	}

	public int addTrip(Trip trip) {
		int shipcount;
		List<Trip> trips;
		freeCapacity += trip.getShip().getCapacity() - trip.getAmount();
		for (int i = 0; i < turns.size(); i++) {
			trips = turns.get(i).getTrips();
			shipcount = 0;
					
			for (int j = 0; j < trips.size(); j++) {
				if (trips.get(j).getShip().equals(trip.getShip())) {
					shipcount++;
				}
			}
			if (shipcount < trip.getShip().getAmount()) {
				turns.get(i).addTrips(trip);
				return i;
			}
		}
		turns.add(new Turn());
		turns.get(turns.size() - 1).addTrips(trip);
		return turns.size() - 1;
	}

	public List<Turn> getTurns() {
		return turns;
	}

	public int getTurnCount() {
		return turns.size();
	}

	public int getFreeCapacity() {
		return freeCapacity;
	}
}
