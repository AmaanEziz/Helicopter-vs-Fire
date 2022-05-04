package org.csc133.a3.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Dimension;

public class PlayerHelicopter extends Helicopter{
    private int padding = -400;
    public PlayerHelicopter(Dimension worldSize) {
        super(worldSize, ColorUtil.YELLOW);
        translate(this.worldSize.getWidth()/2,
                this.worldSize.getHeight()/2 + padding);
        addHeloText();
    }

    @Override
    public void updateLocalTransforms() {
        heloBladeUpdate(-10d*getSpeed());
    }
}
