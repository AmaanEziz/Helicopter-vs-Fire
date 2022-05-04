package org.csc133.a3.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

public class River extends Fixed {

    public River(Dimension worldSize) {
        this.worldSize=worldSize;
        setColor(ColorUtil.BLUE);
        setDimension(new Dimension(worldSize.getWidth(),
                worldSize.getHeight()/7));
        translate(0,worldSize.getHeight()/7);
        translate(worldSize.getWidth()/2,worldSize.getHeight()/2);
    }

    public boolean isCollidingWith(Helicopter helicopter){
        return super.isCollidingWith(helicopter);
    }

    @Override
    public void updateLocalTransforms() {

    }

    @Override
    protected void localDraw(Graphics g,
                             Point parentOrigin, Point screenOrigin) {
        g.setColor(getColor());
        containerTranslate(g,parentOrigin);
        cn1ForwardPrimitiveTranslate(g,getDimension());
        g.drawRect(-dimension.getWidth()/2,
                -dimension.getHeight()/2,
                dimension.getWidth(), dimension.getHeight());
    }
}

