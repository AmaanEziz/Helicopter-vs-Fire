package org.csc133.a3.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class BuildingCollection extends GameObjectCollection<Building> {

    public BuildingCollection(){
        super();
        this.color = ColorUtil.rgb(255,0,0);
    }

    @Override
    public void updateLocalTransforms() {

    }

    @Override
    protected void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {

    }

    @Override
    public void draw(Graphics g, Point originParent, Point originScreen) {
        for(Building building : getGameObjects()){
            building.draw(g, originParent, originScreen);
        }
    }
}