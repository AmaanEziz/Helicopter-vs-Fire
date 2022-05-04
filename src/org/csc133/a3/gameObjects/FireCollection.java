package org.csc133.a3.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class FireCollection extends GameObjectCollection<Fire> {

    public FireCollection(){
        super();
        this.color = ColorUtil.MAGENTA;
    }

    @Override
    public void updateLocalTransforms() {}

    @Override
    protected void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {}

    // a collection of fire
    //
    @Override
    public void draw(Graphics g, Point originParent, Point originScreen) {
        for(Fire fires : getGameObjects()){
            fires.draw(g, originParent, originScreen);
        }
    }
}
