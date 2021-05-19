import java.util.ArrayList;

public class Patch {
    private double grainHere;
    private double maxGrainHere;
    private int location_x;
    private int location_y;
    public ArrayList<Person> people;
    
    public Patch(int x, int y, int maxGrainHere) {
    	this.location_x = x;
    	this.location_y = y;
    	this.maxGrainHere = maxGrainHere;
    	grainHere = maxGrainHere;
    	this.people = new ArrayList<Person>();
    }
    
    public int getLocation_x() {
		return location_x;
	}

	public int getLocation_y() {
		return location_y;
	}

	public void grow(int numGrainGrown) {
    	grainHere = grainHere + numGrainGrown;
    	if (grainHere > maxGrainHere) {
    		grainHere = maxGrainHere;
    	}
    }
	
	public void addPerson(Person person) {
		people.add(person);
	}
	
	public void removePerson(Person person) {
		people.remove(person);
	}
    
    public void harvest() {
    	double peopleHere = people.size();
    	if(grainHere == 0 || peopleHere == 0) {
    		return;
    	}
    	double harvestAmount = Math.floor(grainHere/peopleHere);
    	for(Person person: people) {
    		person.gainGrain(harvestAmount);
    	}
    	grainHere = 0;
    }
    
    public double getGrainHere() {
		return grainHere;
	}

	public void setGrainHere(double grainHere) {
		this.grainHere = grainHere;
	}

	public double getMaxGrainHere() {
		return maxGrainHere;
	}

	public void setMaxGrainHere(double maxGrainHere) {
    	this.maxGrainHere = maxGrainHere;
    }
	
	public void addGrain(double amount) {
		grainHere = grainHere += amount;
	}
	
	public void roundGrainAmount() {
		this.grainHere = Math.round(this.grainHere);
	}
}