package project;

import java.util.List;
import java.util.ArrayList;


/**
 * Soltuion for a quest. Contains the turns which contain the trips.
 */
public class Solution {
	private List<Turn> turns;
	private int freeCapacity;

	/**
	 * Creates a new empty solution
	 */
	public Solution() {
		turns = new ArrayList<Turn>();
		freeCapacity = 0;
	}

	/**
	 * Creates a copy of the given Solution, turns are copied as a deep-copy, trips are copied as a reference
	 * @param s The Solution to copy
	 */
	public Solution(Solution s) {
		turns = new ArrayList<>();
		if(s == null){
			freeCapacity = 0;
		}else{
			for (Turn t : s.getTurns()) {
				turns.add(new Turn(t));
			}
			freeCapacity = s.getFreeCapacity();
		}
	}

	/**
	 * Adds a trip to a solution. The trip will automatically be added in the first turn where the trip is possible.
	 * @param trip The trip to add
	 * @return The turn where the trip was added
	 */
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

	/**
	 * Adds a trip to the last turn
	 * @param trip The trip to add
	 */
    public void addTripFH(Trip trip) {
        freeCapacity += trip.getShip().getCapacity() - trip.getAmount();
        turns.get(turns.size() - 1).addTrips(trip);
    }

	/**
	 * Returns the turns of the solution
	 * @return
	 */
    public List<Turn> getTurns() {
        return turns;
    }

	/**
	 * Adds a turn to the end of a solution
	 * @param turn The turn to add
	 */
    public void addTurn(Turn turn) {
        if(!turn.getTrips().isEmpty()){
            for(Trip trip : turn.getTrips()){
                freeCapacity += trip.getShip().getCapacity() - trip.getAmount();
            }
        }
        this.turns.add(turn);
    }

	/**
	 * Returns the number of turns
	 * @return
	 */
    public int getTurnCount(){
        return turns.size();
    }

	/**
	 * Returns the unused capacity in the solution
	 * @return
	 */
    public int getFreeCapacity() {
        return freeCapacity;
    }
    
	/**
	 * Returns a list of trips of a solution
	 * @param solution
	 * @return
	 */
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

	/**
	 * Checks if this solution solves a given quest.
	 * @param q
	 * @return
	 */
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

	/**
	 * Returns the amount of a specified resource transported by this solution
	 * @param resource
	 * @return
	 */
	public int getTransportedAmount(String resource){
		int amount = 0;
		List<Trip> trips = getAllTrips(this);
		for(Trip t: trips){
			if(t.getResource().equals(resource)){
					amount += t.getAmount();
				}
		}
		return amount;
	}
}
