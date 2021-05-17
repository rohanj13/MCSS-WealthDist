import java.util.ArrayList;

public class Simulator {
    //global variables
    public static ArrayList<Person> people;
    public static int maxMetabolism;
    public static int numTicks;
    public static int minLifeExpectancy;
    public static int maxLifeExpectancy;
    public static int maxVision;
    public static void main(String args[]) {
        Person person = new Person();
        Patch patch = new Patch();
        Landscape land = new Landscape();

        while(true){
            person.turnTowardsGrain();
            person.moveEatAgeDie();

        }
    }
}