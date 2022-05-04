package org.csc133.a3;

import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point2D;
import org.csc133.a3.gameObjects.*;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWorld {
    Dimension worldSize;
    private int totalFireDMG;
    private int maxFireDMG;
    private int buildingArea = 0;
    private int damagePercent = 0;
    private int ticks;
    private int fireTotal = 0;
    private boolean zoomed = false;
    private final int NUMBER_OF_BUILDINGS = 3;
    private static PlayerHelicopter playerHelicopter;
    private Helipad helipad;
    private River river;
    private FlightPath.BezierCurve bezierCurve;
    private FlightPath.BezierCurve bezierCurveRightSide;
    private FlightPath.BezierCurve bezierCurveLeftSide;
    private NonPlayerHelicopter nonPlayerHelicopter;
    private BuildingCollection buildings;
    private FireCollection fires;
    private FlightPath.FlightControl fc;
    Point2D tempFireLocation;
    int path = 0;

    private CopyOnWriteArrayList<GameObject> gameObjects;
    private int rand;

    private double fireTotalSize;

    public GameWorld(){
    }

    public void init() {
        ticks = 0;
        helipad = new Helipad(worldSize);
        river = new River(worldSize);
        buildings = new BuildingCollection();
        fires = new FireCollection();
        rand = new Random().nextInt(10)+50;

        bezierCurve = new FlightPath.BezierCurve(worldSize, 0);

        bezierCurveRightSide = new FlightPath.BezierCurve(
                worldSize, 1);
        bezierCurveLeftSide = new FlightPath.BezierCurve(
                worldSize, 2);

        nonPlayerHelicopter = new NonPlayerHelicopter(worldSize, bezierCurve);

        for (int x = 0; x < NUMBER_OF_BUILDINGS; x++) {
            buildings.add(new Building(x, worldSize));
        }

        for (int i = 0; i < 2; i++) {
            int buildingID = 0;
            for (Building building : buildings) {
                Fire temp = new Fire(worldSize, buildingID);
                building.setFireInBuilding(temp);
                if(buildingID == 2){
                    temp.getLocation();
                    //System.err.println("create: "+ temp.getLocation());
                }
                temp.getLocation();
                getTotalFireSize();
                fires.add(temp);
                fireTotal++;
                buildingID++;
                //System.err.println("ID: " + buildingID);
            }
            //System.err.println("ID: " + buildingID);
        }

        while (this.fireTotalSize < 950) {
            int buildingID = 0;
            for (Building building : buildings) {
                if (ticks % rand == 0) {
                    Fire temp = new Fire(worldSize, buildingID);
                    building.setFireInBuilding(temp);
                    if(buildingID == 2){
                        tempFireLocation = temp.getLocation();
                        //System.err.println("create: "+ temp.getLocation());
                    }
                    fires.add(temp);
                    fireTotal++;
                    buildingID++;
                    getTotalFireSize();
                    //System.err.println("ID1: " + buildingID);
                }
                if (this.fireTotalSize > 950) {
                    break;
                }
            }
        }
        for (Building building : buildings){
            buildingArea += building.getBuildingArea();
        }

        playerHelicopter = new PlayerHelicopter(worldSize);
        nonPlayerHelicopter = new NonPlayerHelicopter(worldSize, bezierCurve);

        //fc = new FlightPath.FlightControl(nonPlayerHelicopter);

        nonPlayerHelicopter.setPath(bezierCurve);

        gameObjects = new CopyOnWriteArrayList<>();

        gameObjects.add(helipad);
        gameObjects.add(river);
        gameObjects.add(buildings);
        gameObjects.add(fires);
        gameObjects.add(bezierCurve);
        gameObjects.add(bezierCurveRightSide);
        gameObjects.add(bezierCurveLeftSide);
        gameObjects.add(nonPlayerHelicopter);
        gameObjects.add(playerHelicopter);
        //System.err.println("helicopter added to gameObjects");
    }

    public void quit(){
        Display.getInstance().exitApplication();
    }

    void tick(){
        ticks++;
        getTotalFireSize();
        getTotalDMG();
        for(Fire fire: fires){
            fire.start();
            if(ticks%rand == 0 && fire.begin() == true ) {
                fire.grow();
                getTotalDMG();
                for (Building building : buildings){
                    building.adjustValue(this.damagePercent*1000/3);
                    building.setDMG(this.damagePercent/3);
                    building.getCurrentValue(this.damagePercent/3);
                }
            }
            if(fire.getSize() <= 8){
                fireTotal--;
                fires.remove(fire);
            }
        }


        for (Building building : buildings){
            building.adjustValue(this.damagePercent*1000/3);
            building.setDMG(this.damagePercent/3);
        }

        /*if (helicopter.isEngineOn()) {
            helicopter.move();
            helicopter.useFuel();
        }*/

        if(river.isCollidingWith(nonPlayerHelicopter)){
            nonPlayerHelicopter.grabWaterFromRiver();
        }

        for(Fire fire: fires){
            if(fire.isCollidingWith(nonPlayerHelicopter)
                    && nonPlayerHelicopter.getCurrentWaterInTank() > 1){
                fire.gigaShrink();
                if(nonPlayerHelicopter.getCurrentWaterInTank()>=500){
                    nonPlayerHelicopter.setWater(
                            nonPlayerHelicopter.getCurrentWaterInTank()-500);
                }
            }
        }

        if(nonPlayerHelicopter.getLocation().getX() ==
                bezierCurve.getEndControlPoint().getX()
                && nonPlayerHelicopter.getLocation().getY() ==
                bezierCurve.getEndControlPoint().getY()
                && bezierCurve.getCurveID() == 0 && path == 0){
            bezierCurve.oldPath();
            bezierCurveRightSide.newActivePath();

            nonPlayerHelicopter.setPath(bezierCurveRightSide);
            path=1;
        }else if(nonPlayerHelicopter.getLocation().getX() ==
                bezierCurveRightSide.getEndControlPoint().getX()
                && nonPlayerHelicopter.getLocation().getY() ==
                bezierCurveRightSide.getEndControlPoint().getY()
                && bezierCurveRightSide.getCurveID() == 1 && path == 1){

            bezierCurveRightSide.oldPath();
            bezierCurveLeftSide.newActivePath();
            nonPlayerHelicopter.setPath(bezierCurveLeftSide);

            path = 2;
        }else if(nonPlayerHelicopter.getLocation().getX() ==
                bezierCurveLeftSide.getEndControlPoint().getX()
                && nonPlayerHelicopter.getLocation().getY() ==
                bezierCurveLeftSide.getEndControlPoint().getY()
                && bezierCurveLeftSide.getCurveID() == 2 && path == 2){

            bezierCurveLeftSide.oldPath();
            /*for(Fire fire : fires){
                if(fire.getBuildingID() == 2){
                    flyToFire(fire.getLocation());
                    //System.err.println("set: "+ fire.getLocation());
                }
            }*/
            bezierCurveRightSide.newActivePath();
            nonPlayerHelicopter.setPath(bezierCurveRightSide);
            path = 1;
        }

        if (playerHelicopter.isEngineOn()) {
            playerHelicopter.move();
            playerHelicopter.useFuel();
        }
        if(nonPlayerHelicopter.isEngineOn()){
            nonPlayerHelicopter.setSpeed();
            nonPlayerHelicopter.move();
            nonPlayerHelicopter.useFuel();
        }

        victoryScreen();
    }

    public CopyOnWriteArrayList<GameObject> getGameObjectCollection() {
        return gameObjects;
    }

    public void drink(){
        if(river.isCollidingWith(playerHelicopter)){
            playerHelicopter.grabWaterFromRiver();
        }
    }

    public void fight(){
        for(Fire fire: fires){
            if(fire.isCollidingWith(playerHelicopter)
                    && playerHelicopter.getCurrentWaterInTank() > 1){
                fire.shrink(playerHelicopter.getCurrentWaterInTank());
            }
        }
        playerHelicopter.useWater();
    }

    // Once states are working check if helicopter is
    // stopped instead of speed = 0
    void victoryScreen(){
        if (helipad.isCollidingWith(playerHelicopter) && fireTotal == 0
                && playerHelicopter.getSpeed() == 0){
            if(Dialog.show("Game Over", "You Won!\nScore: "
                    + playerHelicopter.getFuel() + "\nPlay Again?",
                    "Heck Yea!", "Some Other Time")) {
                // user clicked yes
                new Game();

            } else {
                //If user clicked no
                quit();
            }
        }
    }

    void turn(int turnAmount){
        playerHelicopter.turn(turnAmount);
    }

    public void left() {
        playerHelicopter.turn(-15);
    }

    public void right(){
        playerHelicopter.turn(15);
    }

    public void adjustSpeed(int changeSpeed){
        if (playerHelicopter.isEngineOn()) {
            playerHelicopter.adjustSpeed(changeSpeed);
        }
    }

    public String getHeading() {
        return String.valueOf(playerHelicopter.getHeading());
    }

    private void getTotalFireSize() {
        this.fireTotalSize = 0;

        for (Fire fire : fires) {
            this.fireTotalSize += fire.currentSize();
        }
    }

    private void getTotalDMG() {
        this.totalFireDMG = 0;
        for (Fire fire : fires) {
            this.totalFireDMG += fire.getGreatestSize();
        }

        if(totalFireDMG>maxFireDMG){
            maxFireDMG=totalFireDMG;
        }

        this.damagePercent = (this.maxFireDMG*42000 / this.buildingArea);
    }

    public String getSpeed() {
        return String.valueOf(playerHelicopter.getSpeed());
    }

    public String getFuel() {
        return String.valueOf(playerHelicopter.getFuel());
    }

    public String getFireCount() {
        return String.valueOf(fires.size());
    }

    public String getDamage() {
        return (damagePercent + "%");
    }

    public String getLoss() {
        return ("$" + damagePercent * 1000);
    }

    public String getFireSize() {
        return String.valueOf((int)this.fireTotalSize);
    }

    public void setDimension(Dimension worldSize) {
        this.worldSize = worldSize;
    }

    public void setBezierCurve(Point2D location){
        bezierCurveRightSide.setEndControlPoint(location);
        bezierCurveLeftSide.setStartControlPoint(location);
    }

    public void quitGame() {
        quit();
    }

    public void selectFire(Point2D p){
        for(Fire fire : fires){
                if(fire.contains(p) && !fire.isSelected()){
                    fire.select(true);
                    //flyToFire(p);
                    setBezierCurve(fire.getLocation());
                }
            }
    }

    public void startEngine(){
        playerHelicopter.startOrStopEngine();
        nonPlayerHelicopter.startOrStopEngine();
    }

    public void zoom() {
        if(zoomed == false){
            zoomed = true;
        }else{
            zoomed = false;
        }
    }

    public boolean zoomed(){
        return zoomed;
    }
}

