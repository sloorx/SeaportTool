import java.util.Map;
import java.util.Set;


public class Fleet {

	private Map<Ship, Integer> ships;
	
	
	public Fleet() {
		
	}
	
	public boolean addShip(String shipname, int capacity) {	
		return true;
	}
	
	public boolean addShip(String shipname, int capacity, int shipcount) {		
		return true;
	}
	
	public boolean removeShip(String shipname) {
		return true;
	}
	
	public boolean removeShip(String shipname, int shipcount) {
		return true;
	}
	
	public Set<Ship> getShips() {
		return ships.keySet();
	}
	
	public int getShipAmount(Ship ship) {
		return ships.get(ship);
	}
	
	public boolean load(String path) {
		return true;
	}
	
	public boolean save(String path) {
		return true;
	}
}
