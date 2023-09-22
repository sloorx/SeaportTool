import java.util.List;

public class Solution {
    private List<Turn> turns;
    private int freeCapacity;

    public Solution(List<Turn> turns, int freeCapacity) {
        this.turns = turns;
        this.freeCapacity = freeCapacity;
    }

    public int addTrip(Trip trip) {
        this.turns = turns;
        return 0;
    }

    public List<Turn> getTurns() {
        return turns;
    }

    public int getTurnCount(){
        return 0;
    }

    public int getFreeCapacity() {
        return freeCapacity;
    }
}
