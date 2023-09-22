import java.util.ArrayList;
import java.util.List;

public class Turn {
    private List<Trip> trips;

    public Turn(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public boolean addTrips(Trip trip) {
        this.trips.add(trip);
        return true;
    }
}
