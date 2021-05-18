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
        
    public Simulator() {
        maxMetabolism = 15;
        numTicks = 1;
        minLifeExpectancy = 1;
        maxLifeExpectancy = 83;
        maxVision = 5;
        numPeople = 100;
    }

    public void setupPeople() {
    	Random random = new Random();
        for (int i = 0; i < numPeople; i++) {
            int lifeExpectancy = minLifeExpectancy + random.nextInt(maxLifeExpectancy - minLifeExpectancy + 1);
            int metabolism = 1 + random.nextInt(maxMetabolism);
            int wealth = metabolism + random.nextInt(51);
            int vision = 1 + random.nextInt(maxVision);
            int age = random.nextInt(lifeExpectancy);
            Direction direction = randomDirection();
            int locationX = random.nextInt(Landscape.WIDTH);
            int locationY = random.nextInt(Landscape.HEIGHT);
            // random.nextInt(max - min) + min;
        }
    }
    
    public Direction randomDirection() {
    	Random random = new Random();
    	int facing = random.nextInt(4);
    	if(facing == 0) {
        	return Direction.UP;
        } else if (facing == 1) {
        	return Direction.RIGHT;
        } else if (facing == 2) {
        	return Direction.DOWN;
        } else {
        	return Direction.LEFT;
        }
    }


    public static void main(String args[]) {
        Landscape land = new Landscape(10, 4);

        while(true){
            //functions
        }
    }
}