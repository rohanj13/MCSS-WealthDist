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

    public Person(int age, int wealth, int lifeExpectancy, int metabolism, int vision,
    Direction direction) {
        this.age = age;
        this.wealth = wealth;
        this.lifeExpectancy = lifeExpectancy;
        this.metabolism = metabolism;
        this.vision = vision;
        this.direction = direction;
    }
    
    public int getVision() {
		return vision;
	}

	public int getLocationX() {
		return locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public Direction getDirection() {
    	return this.direction;
    }

    public void turn(Direction direction) {
        this.direction = direction;
    }

    public void moveEatAgeDie() {
        
        this.wealth = this.wealth - this.metabolism;
        this.age += 1;

    }
    
    public void moveTo(int x, int y) {
    	this.locationX = x;
    	this.locationY = y;
    }

    public void setStatus(int maxWealth) {
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
