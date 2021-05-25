import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
    public static int maxTick;
    public Landscape land;
        
    public Simulator() {
    	File file = new File("result.csv");
    	numTicks = 0;
    	land = new Landscape(percentageBestland, numGrainGrown);
    	land.setupLand();
    	setupPeople(land);
    	land.updateStatus();
    	try {
    		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	    	writer.write("Tick 0: " + land.landInformation());
	    	writer.newLine();
	    	while(numTicks < maxTick) {
	    		tick();
	    		writer.write("Tick " + numTicks + ": " + land.landInformation());
	    		writer.newLine();
	    	}
	    	writer.close();
    	} catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error during writing to file");
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
    
    public static void defaultSetting() {
    	numPeople = 100;
    	maxVision = 5;
    	maxMetabolism = 15;   
        minLifeExpectancy = 1;
        maxLifeExpectancy = 83;
        percentageBestland = 10;
        grainGrowthInterval = 1;
    	numGrainGrown = 4;
    	maxTick = 100;
    }
    
    public static void setUp(int[] parameters) {
    	numPeople = parameters[0];
		maxVision = parameters[1];
    	maxMetabolism = parameters[2];
        minLifeExpectancy = parameters[3];
        maxLifeExpectancy = parameters[4];
        percentageBestland = parameters[5];
        grainGrowthInterval = parameters[6];
    	numGrainGrown = parameters[7];
    	maxTick = parameters[8];
    }
    
    public static void printParameters() {
    	System.out.println("num-people: " + numPeople);
    	System.out.println("max-vision: " + maxVision);
    	System.out.println("metabolism-max: " + maxMetabolism);
    	System.out.println("life-expectancy-min: " + minLifeExpectancy);
    	System.out.println("life-expectancy-max: " + maxLifeExpectancy);
    	System.out.println("percent-best-land: " + percentageBestland);
    	System.out.println("grain-growth-interval: " + grainGrowthInterval);
    	System.out.println("num-grain-grown: " + numGrainGrown);
    	System.out.println("max-tick: " + maxTick);
    }


    public static void main(String args[]) {
    	if(args.length == 0) {
    		System.out.println("Running default settings");
    		defaultSetting();
    	} else {
    		if(args[0].endsWith(".csv")) {
    			String path = args[0];
    			File argumentFile = new File(path);
    			int[] parameters = new int[9];
    			try {
					BufferedReader reader = new BufferedReader(new FileReader(argumentFile));
					for(int i = 0; i < parameters.length; i++) {
						String line = reader.readLine();
						if(line == null) {
							System.out.println("Not enough parameters given from input file");
							System.exit(0);
						}
						int number  = Integer.parseInt(line.split(",")[1]);
						if(number < 0) {
							System.out.println("Invalid parameters");
							System.exit(0);
						}
						parameters[i] = number;
					}
					reader.close();
					
					// set up with parameters
					System.out.println("Running settings from " + path);
					setUp(parameters);
				} catch (FileNotFoundException e) {
					System.out.println("File not found");
					System.exit(0);
				} catch (IOException e) {
					System.out.println("Cannot read file");
					System.exit(0);
				} catch (NumberFormatException e) {
    	    		System.out.println("Given parameters is not a integer");
    				System.exit(0);
    	    	}
    		} else {
    			System.out.println("Please give a csv with parameters");
    			System.exit(0);
    		}
    	} 
    	printParameters();
    	Simulator simulator = new Simulator();
    }
}