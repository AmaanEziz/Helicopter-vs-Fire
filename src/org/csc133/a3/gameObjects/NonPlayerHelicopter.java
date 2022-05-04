package org.csc133.a3.gameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point2D;

public class NonPlayerHelicopter extends Helicopter{
    private FlightPath.BezierCurve bezierCurve;
    private double t = 0;
    private double heading = 0;
    private int speed = 0;

    public NonPlayerHelicopter(Dimension worldSize,
                               FlightPath.BezierCurve bezierCurve) {
        super(worldSize, ColorUtil.GREEN);
        addHeloText();
        this.bezierCurve = bezierCurve;
        heading-= 90;
    }

    public void setPath(FlightPath.BezierCurve bezierCurve) {
        this.bezierCurve = bezierCurve;
        t=0;
    }

    public void setSpeed(){
        speed = 20;
    }

    @Override
    public void updateLocalTransforms(){
        heloBladeUpdate(-10d*speed);

        Point2D c = new Point2D(myTranslation.getTranslateX(),
                myTranslation.getTranslateY());
        Point2D p = this.bezierCurve.evaluateCurve(t);

        double tx = p.getX() - c.getX();
        double ty = p.getY() - c.getY();

        double theta = 360 - Math.toDegrees(Math.atan2(ty,tx));

        this.translate(tx,ty);

        if(t<=1){
            t += 0.001*speed;
            rotate(heading - theta);
            heading = theta;
        }else{
            t=0;
        }
    }
}
