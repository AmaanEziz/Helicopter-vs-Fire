package org.csc133.a3.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

import java.util.Random;

import static com.codename1.ui.CN.*;

public class Building extends GameObject{
private int value;
private int damage;
private int currentValue;
private int buildingID;

    public Building(int buildingObject, Dimension worldSize){
        setColor(ColorUtil.rgb(255,0,0));
        if(buildingObject == 0) {
            this.dimension = new Dimension(1200, 200);
            this.worldSize = worldSize;
            value = 33333;
            currentValue = value;
            this.buildingID=buildingObject;
            translate(0, this.getDimension().getHeight()*2.2);

        }else if(buildingObject == 1){
            this.dimension = new Dimension(200, 500);
            value = 33333;
            currentValue = value;
            this.buildingID=buildingObject;
            translate(-getDimension().getWidth()*3,
                    -getDimension().getHeight()/2);

        }else{
            this.dimension = new Dimension(200, 400);
            value = 33333;
            currentValue = value;
            this.buildingID=buildingObject;
            translate(getDimension().getWidth()*3,
                    -getDimension().getHeight()/2);
        }
        translate(worldSize.getWidth()/2,worldSize.getHeight()/2);
    }

    public int getCurrentValue(){
        return this.currentValue;
    }

    public int getCurrentValue(int loss){
        return currentValue = currentValue - loss;
    }

    public void adjustValue(int loss){
        this.value = loss;
    }

    public int getBuildingArea() {
        return (dimension.getWidth() * dimension.getHeight());
    }

    public void setFireInBuilding(Fire fire){
        float xMin = myTranslation.getTranslateX()
                - this.getWidth()/2 + fire.getMaxSize()/2;
        float yMin = (float) (myTranslation.getTranslateY()
                - fire.getMaxSize()*1.5 + this.getHeight()/2
                - fire.getMaxSize()/2);

        int boundX = this.getWidth() - fire.getMaxSize();
        int boundY = -this.getHeight() + fire.getMaxSize();

        float randX = 0;
        float randY = 0;

        if(boundX>0){
            randX = new Random().nextInt(boundX);
        }else{
            randX = new Random().nextInt(-boundX);
            randX*=-1;
        }

        if(boundY>0){
            randY = new Random().nextInt(boundY);
        }else{
            randY = new Random().nextInt(-boundY);
            randY*=-1;
        }

        fire.translate(randX + xMin, randY + yMin);
    }

    public void setDMG(int damage){
        this.damage = damage;
    }

    @Override
    public void updateLocalTransforms() {

    }

    @Override
    protected void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {
        g.setColor(getColor());
        containerTranslate(g,parentOrigin);
        cn1ForwardPrimitiveTranslate(g,getDimension());
        g.drawRect(-getWidth()/2,-getHeight()/2, getWidth(), getHeight());

        g.setFont(Font.createSystemFont
                (FACE_MONOSPACE, STYLE_BOLD, SIZE_MEDIUM));
        g.scale(1,-1);
        g.drawString("V  : $" + this.getCurrentValue(),
                getWidth()/2 + 25, getHeight()/8 - getHeight());
        g.drawString("D  : " + this.damage + "%",
                getWidth()/2 + 25, getHeight()/8 + 25 - getHeight());
    }
}
