package project;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private List<Trip> trips;

    public Turn() {
        trips = new ArrayList<>();
    }

    public Turn(Turn t){
        trips = new ArrayList<>(t.getTrips());
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public boolean addTrips(Trip trip) {
        return this.trips.add(trip);
    }
}
