package project;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single turn of trips
 */
public class Turn {
    private List<Trip> trips;

    /**
     * Creates a new empty turn
     */
    public Turn() {
        trips = new ArrayList<>();
    }

    /**
     * Creates a new turn based on an existing turn
     * @param t
     */
    public Turn(Turn t){
        trips = new ArrayList<>(t.getTrips());
    }

    /**
     * Returns the trips that are made during this turn
     * @return
     */
    public List<Trip> getTrips() {
        return trips;
    }

    /**
     * Adds a trip to this turn
     * @param trip
     * @return
     */
    public boolean addTrips(Trip trip) {
        return this.trips.add(trip);
    }
}
