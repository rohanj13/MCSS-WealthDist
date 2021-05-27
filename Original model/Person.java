import java.util.Random;

public class Person {
    private int age;
    private double wealth;
    private int lifeExpectancy;
    private int metabolism;
    private int vision;
    private int locationX;
    private int locationY;
    private Status status;
    private Direction direction;

    public Person() {
       
    }
    
    public void setupPerson() {
    	Random random = new Random();
    	lifeExpectancy = Simulator.minLifeExpectancy + random.nextInt(Simulator.maxLifeExpectancy - Simulator.minLifeExpectancy + 1);
        metabolism = 1 + random.nextInt(Simulator.maxMetabolism);
        wealth = metabolism + random.nextInt(51);
        vision = 1 + random.nextInt(Simulator.maxVision);
        age = random.nextInt(lifeExpectancy);
        direction = randomDirection();
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
    
    public int getVision() {
		return vision;
	}
    
    public double getWealth() {
    	return wealth;
    }

	public int getLocationX() {
		return locationX;
	}

	public Status getStatus() {
		return status;
	}

	public int getLocationY() {
		return locationY;
	}

	public Direction getDirection() {
    	return this.direction;
    }
	
	public void setLocation(int x, int y) {
		this.locationX = x;
		this.locationY = y;
	}

    public void turn(Direction direction) {
        this.direction = direction;
    }

    public void moveEatAgeDie(int x, int y) {
    	this.locationX = x;
    	this.locationY = y;
        this.wealth = this.wealth - this.metabolism;
        this.age += 1;
        if(this.isDead()) {
        	this.setupPerson();
        }
    }

    public void setStatus(double maxWealth) {
        if (this.wealth <= maxWealth / 3) {
            this.status = Status.POOR;
        }
        else {
            if (this.wealth <= maxWealth * 2 / 3) {
                this.status = Status.MIDDLE_CLASS;
            } else {
                this.status = Status.RICH;
            }
        }
    }

    public boolean isDead() {
        if (age > lifeExpectancy || wealth < 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public void gainGrain(double harvest) {
        wealth += harvest;
    }


}
