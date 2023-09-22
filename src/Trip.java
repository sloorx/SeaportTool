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

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public String getRessource() {
        return ressource;
    }

    public void setRessource(String ressource) {
        this.ressource = ressource;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
