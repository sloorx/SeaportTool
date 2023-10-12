package project;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Fleet {

	private Map<String, Ship> ships;
	private static Fleet instance = null;
	
	public static Fleet getInstance(){
		if(instance == null){
			instance = new Fleet();
		}
		return instance;
	}
	
	private Fleet() {
		ships = new HashMap<String, Ship>();
	}
	
	public boolean addShip(String shipname, int capacity) {
		return addShip(shipname, capacity, 1);
	}
	
	public boolean addShip(String shipname, int capacity, int shipcount) {
		if(ships.containsKey(shipname)){
			Ship s = ships.get(shipname);
			if(s.equals(new Ship(shipname, capacity, 0))){
				s.setAmount(s.getAmount() + shipcount);
				return true;
			}else{
				return false;
			}
		}else{
			ships.put(shipname, new Ship(shipname, capacity, shipcount));
			return true;
		}
	}
	
	public boolean editShip(String shipname, int newCapacity){
		if(ships.containsKey(shipname)){
			ships.get(shipname).setCapacity(newCapacity);
			return true;
		}
		return false;
	}

	public boolean editShip(String shipname, String newName, int newCapacity){
		if(ships.containsKey(shipname)){
			Ship s = ships.get(shipname);
			s.setName(newName);
			s.setCapacity(newCapacity);
			ships.remove(shipname);
			ships.put(newName, s);
			return true;
		}
		return false;
	}

	public boolean removeShip(String shipname) {
		if(ships.containsKey(shipname)){
			ships.remove(shipname);
			return true;
		}
		return false;
	}
	
	public boolean removeShip(String shipname, int shipcount) {
		if(ships.containsKey(shipname)){
			Ship s = ships.get(shipname);
			s.setAmount(s.getAmount() - shipcount);
			if(s.getAmount() <= 0){
				ships.remove(shipname);
			}
			return true;
		}
		return false;
	}
	
	public Collection<Ship> getShips() {
		return ships.values();
	}
	
	public boolean load(String path) {
		return true;
	}
	
	public boolean save(String path) {
		return true;
	}
}
