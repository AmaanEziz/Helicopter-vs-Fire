package org.csc133.a3.gameObjects;

public abstract class Movable extends GameObject {
    private int speed;
    private double heading;

    public Movable(){
        speed = 0;
        heading = 90;
    }

    public void move(){
        if (speed > 0){
            translate(7*speed*Math.cos(Math.toRadians(heading)),
                    -7*speed*Math.sin(Math.toRadians(heading)));
        }
    }

    // Adjust the speed of the helicopter based on the value of speedChange
    // passed in, speedChange can be -1 or 1
    //
    public void adjustSpeed(int speedChange){
        if (speed + speedChange <= 10 && speed + speedChange >= 0){
            speed += speedChange;
        }
    }

    public void turn(int turnAmount){
        heading = heading + turnAmount;
        rotate(-turnAmount);
    }

    public int getHeading(){
        if(heading<0){
            return (int) (heading +=360);
        }else if(heading>360){
            return (int) (heading-=360);
        }else {
            return (int) heading;
        }
    }

    public int getSpeed(){
        return speed;
    }

    protected void setHeading(int heading) {
        this.heading = heading;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }
}
