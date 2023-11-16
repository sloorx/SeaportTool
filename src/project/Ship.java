package project;

/**
 * The ship class represents a shiptype
 */
public class Ship {

	private String name;
	private int capacity;
	private int amount;
	
	/**
	 * create a new shiptype
	 * @param name the name of the shiptype
	 * @param capacity the cargo capacity
	 * @param amount the amount of ships of this type
	 */
	public Ship(String name, int capacity, int amount) {
		setName(name);
		setCapacity(capacity);
		setAmount(amount);
	}
	
	/**
	 * Returns the name of the ship
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the ship
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the cargo capacity of the shiptype
	 * @return
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * Sets the cargo capacity of the shiptype
	 * @param capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	/**
	 * Returns the amount of ships of this shiptype
	 * @return
	 */
	public int getAmount(){
		return amount;
	}

	/**
	 * Sets the amount of ships of this shiptype
	 * @param amount
	 */
	public void setAmount(int amount){
		this.amount = amount;
	}

	@Override
	public boolean equals(Object obj){
		if(obj instanceof Ship){
			if(name.equals(((Ship) obj).getName()) && capacity == ((Ship) obj).getCapacity()){
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode(){
		return name.hashCode();
	}
}
