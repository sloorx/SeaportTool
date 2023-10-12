package project;

public class Ship {

	private String name;
	private int capacity;
	private int amount;
	
	
	public Ship(String name, int capacity, int amount) {
		setName(name);
		setCapacity(capacity);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int getAmount(){
		return amount;
	}

	public void setAmount(int amount){
		this.amount = amount;
	}

	public boolean equals(Object obj){
		if(obj instanceof Ship){
			if(name.equals(((Ship) obj).getName()) && capacity == ((Ship) obj).getCapacity()){
				return true;
			}
		}
		return false;
	}

	public int hashCode(){
		return name.hashCode();
	}
}
