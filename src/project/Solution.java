package project;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

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

    public void addTripFH(Trip trip) {
        freeCapacity += trip.getShip().getCapacity() - trip.getAmount();
        turns.get(turns.size() - 1).addTrips(trip);
    }

    public List<Turn> getTurns() {
        return turns;
    }

    public void addTurn(Turn turn) {
        if(!turn.getTrips().isEmpty()){
            for(Trip trip : turn.getTrips()){
                freeCapacity += trip.getShip().getCapacity() - trip.getAmount();
            }
        }
        this.turns.add(turn);
    }

    public int getTurnCount(){
        return turns.size();
    }

    public int getFreeCapacity() {
        return freeCapacity;
    }
    
    private void sortTrips(List<Trip> trips) {
		trips.sort((new Comparator<Trip>() {

			@Override
			public int compare(Trip t1, Trip t2) {
				Ship s1 = t1.getShip();
				Ship s2 = t2.getShip();

				return s1.getName().compareTo(s2.getName());
			}
		}).thenComparing(Trip::getResource));
	}

	private void getAllTrips(Solution solution, List<Trip> trips) {
		for (Turn turn : solution.getTurns()) {
			trips.addAll(turn.getTrips());
		}
	}
	
    public boolean equals(Solution s2) {
		List<Trip> trips1 = new ArrayList<>();
		List<Trip> trips2 = new ArrayList<>();
		getAllTrips(this, trips1);
		getAllTrips(s2, trips2);

		for(Trip t: trips1){
			if(trips2.contains(t)){
				trips2.remove(t);
			}else{
				return false;
			}
		}
		return trips2.isEmpty();
    }
}
