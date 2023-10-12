package project;

public class Trip {
    private Ship ship;
    private String ressource;
    private int amount;

    public Trip(Ship ship, String ressource, int amount) {
        this.ship = ship;
        this.ressource = ressource;
        this.amount = amount;
    }

    public Ship getShip() {
        return ship;
    }

    public String getRessource() {
        return ressource;
    }

    public int getAmount() {
        return amount;
    }
}
