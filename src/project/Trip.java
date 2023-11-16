package project;

/**
 * The trip class represents a single trip by a single ship. This class offers no setter-methods and is therefore immutable.
 */
public class Trip {
    private Ship ship;
    private String resource;
    private int amount;

    /**
     * creates a new trip
     * @param ship the shiptype that is used for the trip
     * @param resource the name of the resource that is being transported
     * @param amount the amount of the resource that is being transported
     */
    public Trip(Ship ship, String resource, int amount) {
        this.ship = ship;
        this.resource = resource;
        this.amount = amount;
    }

    /**
     * Returns the shiptype that is used for this trip
     * @return
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Returns the resource that is being transported
     * @return
     */
    public String getResource() {
        return resource;
    }

    /**
     * Returns the amount of the resource that is being transported
     * @return
     */
    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null){
            return false;
        }
        if(o.getClass() != this.getClass()){
            return false;
        }
        Trip t = (Trip) o;
        if(!this.ship.equals(t.ship)){
            return false;
        }
        if(!this.resource.equals(t.resource)){
            return false;
        }
        if(this.amount != t.amount){
            return false;
        }
        return true;
    }
}
