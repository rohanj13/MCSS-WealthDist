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
    private int facing;

    public Person(int age, int wealth, int lifeExpectancy, int metabolism, int vision, Landscape landscape,
    int facing) {
        this.age = age;
        this.wealth = wealth;
        this.lifeExpectancy = lifeExpectancy;
        this.metabolism = metabolism;
        this.vision = vision;
        this.landscape = landscape;
        this.facing = facing;
    }

    public void turnTowardsGrain() {
        // int best = 0;
        // int bestAmount = landscape.grainAhead(facingX, facingY, vision);

        // if (facing ) {
        //     if (landscape.grainAhead(locationX, locationY, 90, vision) > bestAmount) {
        //         bestX = locationX + 1;
        //         bestY = locationY;
        //         bestAmount = landscape.grainAhead(locationX + 1, locationY, vision);
        //     }
        // }

        // if (!(facingX == locationX - 1 && facingY == locationY)) {
        //     if (landscape.grainAhead(locationX - 1, locationY, vision) > bestAmount) {
        //         bestX = locationX - 1;
        //         bestY = locationY;
        //         bestAmount = landscape.grainAhead(locationX - 1, locationY, vision);
        //     }
        // }

        // if (!(facingX == locationX && facingY == locationY + 1)) {
        //     if (landscape.grainAhead(locationX, locationY + 1, vision) > bestAmount) {
        //         bestX = locationX;
        //         bestY = locationY + 1;
        //         bestAmount = landscape.grainAhead(locationX, locationY + 1, vision);
        //     }
        // }

        // if (!(facingX == locationX && facingY == locationY - 1)) {
        //     if (landscape.grainAhead(locationX, locationY - 1, vision) > bestAmount) {
        //         bestX = locationX;
        //         bestY = locationY - 1;
        //         bestAmount = landscape.grainAhead(locationX, locationY - 1, vision);
        //     }
        // }

        // this.facingX = bestX;
        // this.facingY = bestY;

        this.facing = landscape.bestDirection(locationX, locationY, vision);
        
    }

    private int[] getPos(int direction, int locationX, int locationY) {
        int[] coordinates = new int[2];
        if (direction == 0) {
            if (locationY == landscape.getHeight() - 1) {
                locationY = 0;
            }
            else {
                locationY += 1;
            }
            
        }
        else if (direction == 90) {
            if (locationX == landscape.getWidth() - 1) {
                locationX = 0;
            }
            else {
                locationX += 1;
            }
            
        }
        else if (direction == 180) {
            if (locationY == 0) {
                locationY = landscape.getHeight() - 1;
            }
            else {
                locationY -= 1;
            }
        }
        else if (direction == 270) {
            if (locationX == 0) {
                locationX = landscape.getWidth() - 1;
            }
            else {
                locationX -= 1;
            }
        }
        coordinates[0] = locationX;
        coordinates[1] = locationY;
        return coordinates;
    }

    public void moveEatAgeDie() {
        int[] coordinates = getPos(facing, this.locationX, this.locationY);
        this.locationX = coordinates[0];
        this.locationY = coordinates[1];
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
