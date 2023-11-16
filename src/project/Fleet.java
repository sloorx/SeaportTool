package project;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains and manages the ships. This class is implemented in the singleton-pattern. Use getInstance() instead of the constructor.
 */
public class Fleet {

	private Map<String, Ship> ships;
	private static Fleet instance = null;
	
	/**
	 * Returns the Fleet object
	 * @return
	 */
	public static Fleet getInstance(){
		if(instance == null){
			instance = new Fleet();
		}
		return instance;
	}
	
	private Fleet() {
		ships = new HashMap<String, Ship>();
	}
	
	/**
	 * Adds a ship to the fleet
	 * @param shipname The name of the ship
	 * @param capacity the capacity of the ship
	 * @return false if ship with the name but different capacity already exists, true otherwise
	 */
	public boolean addShip(String shipname, int capacity) {
		return addShip(shipname, capacity, 1);
	}
	
	/**
	 * Adds ships to the fleet
	 * @param shipname The name of the ship
	 * @param capacity the capacity of the ship
	 * @param shipcount the amount of ships
	 * @return false if ship with the name but different capacity already exists, true otherwise
	 */
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
	
	/**
	 * Changes the capacity of a ship
	 * @param shipname the name of the ship
	 * @param newCapacity the new capacity of the ship
	 * @return true if the ship exists, false otherwise
	 */
	public boolean editShip(String shipname, int newCapacity){
		if(ships.containsKey(shipname)){
			ships.get(shipname).setCapacity(newCapacity);
			return true;
		}
		return false;
	}

	/**
	 * Changes all stats of a ship
	 * @param shipname the old name of the ship
	 * @param newName the new name of the ship
	 * @param newCapacity the new capacity of the ship
	 * @param newAmount the new amount of the ships
	 * @return true if the ship exists, false otherwise
	 */
	public boolean editShip(String shipname, String newName, int newCapacity, int newAmount){
		if(ships.containsKey(shipname)){
			Ship s = ships.get(shipname);
			s.setName(newName);
			s.setCapacity(newCapacity);
			s.setAmount(newAmount);
			ships.remove(shipname);
			ships.put(newName, s);
			return true;
		}
		return false;
	}

	/**
	 * Removes a shiptype from the fleet
	 * @param shipname the name of the ship
	 * @return true if successful, false otherwise
	 */
	public boolean removeShip(String shipname) {
		if(ships.containsKey(shipname)){
			ships.remove(shipname);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes a number of ships from the fleet
	 * @param shipname the name of the ship
	 * @param shipcount the amount of ships to remove
	 * @return true if successful, false otherwise
	 */
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
	
	/**
	 * Returns a collection containing all ships in the fleet
	 * @return
	 */
	public Collection<Ship> getShips() {
		return ships.values();
	}
	
	/**
	 * Removes all ships from the fleet
	 */
	public void clear(){
		ships.clear();
	}
}
