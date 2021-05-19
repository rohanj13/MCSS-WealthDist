import java.util.ArrayList;

public class Landscape {
    public final static int WIDTH=45;
    public final static int HEIGHT=45;
    public final static int MAXGRAIN = 50;
    private int percentBestLand;
    private Patch[][] land;
    private int numGrainGrown;
    private ArrayList<Patch> diffuseList;
    private ArrayList<Person> people;

    public Landscape(int percentBestLand, int numGrainGrown) {
    	land = new Patch[WIDTH][HEIGHT];
    	this.percentBestLand = percentBestLand;
    	this.numGrainGrown = numGrainGrown;
    	this.diffuseList = new ArrayList<Patch>();
    	this.people = new ArrayList<Person>();
    }
    
    public void addPerson(Person person, int x, int y) {
    	Patch patch = land[x][y];
    	person.setLocation(x, y);
    	people.add(person);
    	patch.addPerson(person);
    }
    
    public void setupLand() {
    	// give some patches the highest amount of grain possible
    	// these patches are the "best land"
    	for(int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++) {
    			if(Math.random() * 100  <= percentBestLand) {
    				land[x][y] = new Patch(x, y, MAXGRAIN);
    			} else {
    				land[x][y] = new Patch(x, y, 0);
    			}
    		}
    	}
    	
    	// spread that grain around the window a little and put a little back
    	// into the patches that are the "best land" found above
    	for(int i = 0; i < 5; i++) {
	    	for(int x = 0; x < WIDTH; x++) {
	    		for (int y = 0; y < HEIGHT; y++) {
	    			if(land[x][y].getMaxGrainHere() != 0) {
	    				double maxGrainHere = land[x][y].getMaxGrainHere();
	    				land[x][y].setGrainHere(maxGrainHere);
	    			}
	    			if(land[x][y].getGrainHere() != 0) {
	    				diffuseList.add(land[x][y]);
	    			}
	    		}
	    	}
	    	this.diffuse(0.25);
    	}
    	
    	// spread the grain around some more
    	for(int i = 0; i < 10; i++) {
	    	for(int x = 0; x < WIDTH; x++) {
	    		for (int y = 0; y < HEIGHT; y++) {
	    			if(land[x][y].getGrainHere() != 0) {
	    				diffuseList.add(land[x][y]);
	    			}
	    		}
	    	}
	    	this.diffuse(0.25);
    	}
    	
    	for(int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++) {
    			Patch patch = land[x][y];
    			patch.roundGrainAmount();
    			patch.setMaxGrainHere(patch.getGrainHere());
    		}
    	}
    }
    
    public void diffuse(double percentage) {
    	for(Patch patch: diffuseList) {
    		int x = patch.getLocation_x();
    		int y = patch.getLocation_y();
    		double currentGrain = patch.getGrainHere();
    		patch.setGrainHere(currentGrain * (1 - percentage));
    		double diffuseAmount = (currentGrain * percentage /8);
    		
    		this.upPatch(x, y).addGrain(diffuseAmount);
    		this.downPatch(x, y).addGrain(diffuseAmount);
    		this.leftPatch(x, y).addGrain(diffuseAmount);
    		this.rightPatch(x, y).addGrain(diffuseAmount);
    		this.upLeftPatch(x, y).addGrain(diffuseAmount);
    		this.upRightPatch(x, y).addGrain(diffuseAmount);
    		this.downLeftPatch(x, y).addGrain(diffuseAmount);
    		this.downRightPatch(x, y).addGrain(diffuseAmount);
    	}
    	
    	diffuseList.clear();
    }
    
    public void growEverywhere() {
    	for(int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++) {
    			land[x][y].grow(numGrainGrown);
    		}
    	}
    }
    
    /**
     * Get all people turn towards the direction with most grain in vision
     */
    public void turn_all_people() {
    	for(Person person: people) {
    		int x = person.getLocationX();
    		int y = person.getLocationY();
    		int vision = person.getVision();
    		
    		person.turn(bestDirection(x, y, vision));
    	}
    }
    
    public void harvest() {
    	for(int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++) {
    			land[x][y].harvest();
    		}
    	}
    }
    
    public void all_people_take_action() {
    	for(Person person: people) {
    		int x = person.getLocationX();
    		int y = person.getLocationY();
    		Direction direction = person.getDirection();
    		Patch nextPatch = null;
    		if(direction == Direction.UP) {
    			nextPatch = upPatch(x, y);
    		} else if (direction == Direction.RIGHT){
    			nextPatch = rightPatch(x, y);
    		} else if (direction == Direction.DOWN){
    			nextPatch = downPatch(x, y);
    		} else if (direction == Direction.LEFT){
    			nextPatch = leftPatch(x, y);
    		} else {
    			nextPatch = upPatch(x, y);
    		}
    		land[x][y].removePerson(person);
    		int targetX = nextPatch.getLocation_x();
    		int targetY = nextPatch.getLocation_y();
    		
    		person.moveEatAgeDie(targetX, targetY);
    		nextPatch.addPerson(person);
    	}
    }
    
    public void updateStatus() {
    	double maxWealth = 0;
    	for(Person person: people) {
    		if(person.getWealth() > maxWealth) {
    			maxWealth = person.getWealth();
    		}
    	}
    	
    	for(Person person: people) {
    		person.setStatus(maxWealth);
    	}
    }
    
    public void printLand() {
    	int lowNum = 0;
    	int middleNum = 0;
    	int highNum = 0;
    	for(Person person: people) {
    		if(person.getStatus() == Status.POOR) {
    			lowNum++;
    		} else if(person.getStatus() == Status.MIDDLE_CLASS) {
    			middleNum++;
    		} else if(person.getStatus() == Status.RICH) {
    			highNum++;
    		}
    	}
    	System.out.println("Low: " + lowNum + "|middle: " + middleNum + "|high: " + highNum);
    }
    
    public Direction bestDirection(int x, int y, int vision) {
    	Direction bestDirection = Direction.UP;
    	double bestAmount = upTotal(x, y, vision -1);
    	double rightTotal = rightTotal(x, y, vision -1);
    	double downTotal = downTotal(x, y, vision -1);
    	double leftTotal = leftTotal(x, y, vision -1);
    	if(rightTotal > bestAmount) {
    		bestAmount = rightTotal;
    		bestDirection = Direction.RIGHT;
    	}
    	
    	if(downTotal > bestAmount) {
    		bestAmount = rightTotal;
    		bestDirection = Direction.DOWN;
    	}
    	
    	if(leftTotal > bestAmount) {
    		bestAmount = leftTotal;
    		bestDirection = Direction.LEFT;
    	}
		
		return bestDirection;
	}
    
    public Patch leftPatch(int x, int y) {
    	if(x == 0) {
    		return land[WIDTH - 1][y];
    	}
    	return land[x - 1][y];
    }
    
    public Patch rightPatch(int x, int y) {
    	if(x == WIDTH - 1) {
    		return land[0][y];
    	}
    	return land[x + 1][y];
    }
    
    public Patch upPatch(int x, int y) {
    	if(y == 0) {
    		return land[x][HEIGHT -1];
    	}
    	return land[x][y - 1];
    }
    
    public Patch downPatch(int x, int y) {
    	if(y == HEIGHT -1) {
    		return land[x][0];
    	}
    	return land[x][y + 1];
    }
    
    public Patch upLeftPatch(int x, int y) {
    	int returnX = x - 1;
    	int returnY = y - 1;
    	if(x == 0) {
    		returnX = WIDTH - 1;
    	}
    	if(y == 0) {
    		returnY = HEIGHT - 1;
    	}
    	
    	return land[returnX][returnY];
    }
    
    public Patch upRightPatch(int x, int y) {
    	int returnX = x + 1;
    	int returnY = y - 1;
    	if(x == WIDTH - 1) {
    		returnX = 0;
    	}
    	if(y == 0) {
    		returnY = HEIGHT - 1;
    	}
    	
    	return land[returnX][returnY];
    }
    
    public Patch downLeftPatch(int x, int y) {
    	int returnX = x - 1;
    	int returnY = y + 1;
    	if(x == 0) {
    		returnX = WIDTH - 1;
    	}
    	if(y == HEIGHT - 1) {
    		returnY = 0;
    	}
    	
    	return land[returnX][returnY];
    }
    
    public Patch downRightPatch(int x, int y) {
    	int returnX = x + 1;
    	int returnY = y + 1;
    	if(x == WIDTH - 1) {
    		returnX = 0;
    	}
    	if(y == HEIGHT - 1) {
    		returnY = 0;
    	}
    	
    	return land[returnX][returnY];
    }
	
    /**
	 * recursively get the total amount of grain of patches on the left in vision
	 * @param x
	 * @param y
	 * @param vision
	 * @return
	 */
	public double leftTotal(int x, int y, int vision){
		Patch leftPatch = leftPatch(x, y);
		double total = leftPatch.getGrainHere();
		if (vision == 0){
			return total;
		}
		
		return (total + leftTotal(leftPatch.getLocation_x(), leftPatch.getLocation_y(), vision -1));
	}
	
	/**
	 * recursively get the total amount of grain of patches on the right in vision
	 * @param x
	 * @param y
	 * @param vision
	 * @return
	 */
	public double rightTotal(int x, int y, int vision){
		Patch rightPatch = rightPatch(x, y);
		double total = rightPatch.getGrainHere();
		if (vision == 0){
			return total;
		}
		
		return (total + rightTotal(rightPatch.getLocation_x(), rightPatch.getLocation_y(), vision -1));
	}
	
	/**
	 * recursively get the total amount of grain of patches upward in vision
	 * @param x
	 * @param y
	 * @param vision
	 * @return
	 */
	public double upTotal(int x, int y, int vision){
		Patch upPatch = upPatch(x, y);
		double total = upPatch.getGrainHere();
		if (vision == 0){
			return total;
		}
		
		return (total + upTotal(upPatch.getLocation_x(), upPatch.getLocation_y(), vision -1));
	}
	
	
	/**
	 * recursively get the total amount of grain of patches downward in vision
	 * @param x
	 * @param y
	 * @param vision
	 * @return
	 */
	public double downTotal(int x, int y, int vision){
		Patch downPatch = downPatch(x, y);
		double total = downPatch.getGrainHere();
		if (vision == 0){
			return total;
		}
		
		return (total + downTotal(downPatch.getLocation_x(), downPatch.getLocation_y(), vision -1));
	}
}


