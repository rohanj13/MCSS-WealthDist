import java.util.ArrayList;

public class Landscape {
    private final static int WIDTH=45;
    private final static int HEIGHT=45;
    private final static int MAXGRAIN = 50;
    private int percentBestLand;
    private Patch[][] land;
//    private int grainGrowthInterval;
    private int numGrainGrown;
    private ArrayList<Patch> diffuseList;
    
//    public static void main(String[] args) {
//    	Landscape landscape = new Landscape(5, 1, 4);
//    	landscape.setupLand();
//    	landscape.printLand();
//    }

    public int grainAhead(int location_x, int location_y, int vision) { }

    public Landscape(int percentBestLand, int grainGrowthInterval, int numGrainGrown) {
    	land = new Patch[WIDTH][HEIGHT];
    	this.percentBestLand = percentBestLand;
//    	this.grainGrowthInterval = grainGrowthInterval;
    	this.numGrainGrown = numGrainGrown;
    	this.diffuseList = new ArrayList<Patch>();
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
    
    public void growEverywhere() {
    	for(int x = 0; x < WIDTH; x++) {
    		for (int y = 0; y < HEIGHT; y++) {
    			land[x][y].grow(numGrainGrown);
    		}
    	}
    }
    
    public void harvest() {
    	
    }
    
    public void turn_all_people() {
    	
    }
    
    public void printLand() {
    	for(int y = 0; y < HEIGHT; y++) {
    		System.out.print("line " + y + ":");
    		for (int x = 0; x < WIDTH; x++) {
    			System.out.print(" " + land[x][y].getGrainHere());
    		}
    		System.out.println();
    	}
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
}


