package org.csc133.a3.gameObjects.parts;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.gameObjects.GameObject;

public class Arc extends GameObject {
    private final int startAngle;
    private final int arcAngle;

    private Arc(){
        this.startAngle=0;
        this.arcAngle=0;
    }

    @Override
    public void updateLocalTransforms() {

    }

    public Arc(int color, int width, int height, int startAngle, int arcAngle){
        this(color,width,height,
                0,0,
                0,0,
                0,
                startAngle, arcAngle);
    }

    public Arc(int color,
        int width,
        int height,
        float tx, float ty, // translate x/y
        float sx, float sy, // scale x/y
        float degreesRotation,
        int startAngle, int arcAngle){

        setColor(color);
        setDimension(new Dimension(width, height));
        this.startAngle=startAngle;
        this.arcAngle=arcAngle;

        translate(tx,ty);
        scale(sx,sy);
        rotate(degreesRotation);
    }

    @Override
    protected void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {
        g.setColor(getColor());
        containerTranslate(g,parentOrigin);
        cn1ForwardPrimitiveTranslate(g,getDimension());
        g.drawArc(-getWidth()/2,-getHeight()/2,
                getWidth(), getHeight(),
                startAngle, arcAngle);
    }
}
