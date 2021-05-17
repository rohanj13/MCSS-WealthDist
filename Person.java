public class Person {
    private int age;
    private int wealth;
    private int lifeExpectancy;
    private int metabolism;
    private int vision;
    private int locationX;
    private int locationY;
    private Status status;
    private Landscape landscape;
    private int facingX;
    private int facingY;

    public Person(int age, int wealth, int lifeExpectancy, int metabolism, int vision, Landscape landscape,
    int facingX, int facingY) {
        this.age = age;
        this.wealth = wealth;
        this.lifeExpectancy = lifeExpectancy;
        this.metabolism = metabolism;
        this.vision = vision;
        this.landscape = landscape;
        this.facingX = facingX;
        this.facingY = facingY;
    }

    public void turnTowardsGrain() {
        int bestX = facingX;
        int bestY = facingY;
        int bestAmount = landscape.grainAhead(facingX, facingY, vision);

        if (!(facingX == locationX + 1 && facingY == locationY)) {
            if (landscape.grainAhead(locationX + 1, locationY, vision) > bestAmount) {
                bestX = locationX + 1;
                bestY = locationY;
                bestAmount = landscape.grainAhead(locationX + 1, locationY, vision);
            }
        }

        if (!(facingX == locationX - 1 && facingY == locationY)) {
            if (landscape.grainAhead(locationX - 1, locationY, vision) > bestAmount) {
                bestX = locationX - 1;
                bestY = locationY;
                bestAmount = landscape.grainAhead(locationX - 1, locationY, vision);
            }
        }

        if (!(facingX == locationX && facingY == locationY + 1)) {
            if (landscape.grainAhead(locationX, locationY + 1, vision) > bestAmount) {
                bestX = locationX;
                bestY = locationY + 1;
                bestAmount = landscape.grainAhead(locationX, locationY + 1, vision);
            }
        }

        if (!(facingX == locationX && facingY == locationY - 1)) {
            if (landscape.grainAhead(locationX, locationY - 1, vision) > bestAmount) {
                bestX = locationX;
                bestY = locationY - 1;
                bestAmount = landscape.grainAhead(locationX, locationY - 1, vision);
            }
        }

        this.facingX = bestX;
        this.facingY = bestY;
        
    }

    public void moveEatAgeDie() {
        this.locationX = facingX;
        this.locationY = facingY;
        this.wealth = this.wealth - this.metabolism;
        this.age += 1;

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

    public void gainGrain(int harvest) {
        wealth += harvest;
    }


}
