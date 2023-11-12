package project;

public class Trip {
    private Ship ship;
    private String resource;
    private int amount;

    public Trip(Ship ship, String resource, int amount) {
        this.ship = ship;
        this.resource = resource;
        this.amount = amount;
    }

    public Ship getShip() {
        return ship;
    }

    public String getResource() {
        return resource;
    }

    public int getAmount() {
        return amount;
    }
}
