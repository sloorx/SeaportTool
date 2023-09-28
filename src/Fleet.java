import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Fleet {

	private Map<Ship, Integer> ships;
	private static Fleet instance = null;
	
	public static Fleet getInstance(){
		if(instance == null){
			instance = new Fleet();
		}
		return instance;
	}
	
	private Fleet() {
		ships = new HashMap<Ship, Integer>();
	}
	
	public boolean addShip(String shipname, int capacity) {
		return true;
	}
	
	public boolean addShip(String shipname, int capacity, int shipcount) {		
		return true;
	}
	
	public boolean editShip(String shipname, int newCapacity){
		return true;
	}

	public boolean editShip(String shipname, String newName, int newCapacity){
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
