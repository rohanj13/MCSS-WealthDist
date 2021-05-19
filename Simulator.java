import java.util.ArrayList;
import java.util.Random;

public class Simulator {
    //global variables
    public static ArrayList<Person> people;
    public static int maxMetabolism;
    public static int numTicks;
    public static int minLifeExpectancy;
    public static int maxLifeExpectancy;
    public static int maxVision;
    public static int numPeople;
    public static int grainGrowthInterval;
    public static int percentageBestland;
    public static int numGrainGrown;
    public Landscape land;
        
    public Simulator() {
    	numPeople = 100;
    	maxVision = 5;
    	maxMetabolism = 15;   
        minLifeExpectancy = 1;
        maxLifeExpectancy = 83;
        percentageBestland = 10;
        grainGrowthInterval = 1;
    	numGrainGrown = 4;
    	numTicks = 0;
    	land = new Landscape(percentageBestland, numGrainGrown);
    	land.setupLand();
    	setupPeople(land);
    	land.updateStatus();
    	land.printLand();
    	while(numTicks < 100) {
    		tick();
    		land.printLand();
    	}
    }

    public void setupPeople(Landscape land) {
    	Random random = new Random();
        for (int i = 0; i < numPeople; i++) {
            int locationX = random.nextInt(Landscape.WIDTH);
            int locationY = random.nextInt(Landscape.HEIGHT);
            Person person = new Person();
            person.setupPerson();
            land.addPerson(person, locationX, locationY);
        }
    }
    
   
    
    public void tick() {
    	land.turn_all_people();
    	land.harvest();
    	land.all_people_take_action();
    	land.updateStatus();
    	if(numTicks % grainGrowthInterval == 0) {
    		land.growEverywhere();
    	}
    	numTicks++;
    }


    public static void main(String args[]) {
    	Simulator simulator = new Simulator();
    }
}