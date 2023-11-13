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

    public boolean equals(Trip t){
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
