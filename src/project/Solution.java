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
    
	private List<Trip> getAllTrips(Solution solution) {
		List<Trip> trips = new ArrayList<>();
		for (Turn turn : solution.getTurns()) {
			trips.addAll(turn.getTrips());
		}
		return trips;
	}
	
	@Override
    public boolean equals(Object o) {
		if(this == o){
            return true;
        }
        if(o == null){
            return false;
        }
        if(o.getClass() != this.getClass()){
            return false;
        }
		Solution s = (Solution) o;
		List<Trip> trips1 = getAllTrips(this);
		List<Trip> trips2 = getAllTrips(s);
		for(Trip t: trips1){
			if(trips2.contains(t)){
				trips2.remove(t);
			}else{
				return false;
			}
		}
		return trips2.isEmpty();
    }

	public boolean solves(Quest q){
		int amount;
		List<Trip> trips = getAllTrips(this);
		for(String r: q.getResources()){
			amount = q.getValue(r);
			for(Trip t: trips){
				if(t.getResource().equals(r)){
					amount -= t.getAmount();
				}
			}
			if(amount != 0){
				return false;
			}
		}
		return true;
	}
}
