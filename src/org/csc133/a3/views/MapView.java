package org.csc133.a3.views;

import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a3.GameWorld;
import org.csc133.a3.gameObjects.GameObject;
import org.csc133.a3.interfaces.FireDispatch;
import org.csc133.a3.interfaces.Subject;

import java.util.ArrayList;

// CustomContainer Equivalent
public class MapView extends Container implements Subject {
    private GameWorld gw;
    private ArrayList<FireDispatch> fireObservers;
    private float winLeft, winBottom, winRight, winTop;

    public MapView(GameWorld gw) {
        this.gw = gw;
        fireObservers = new ArrayList<>();
    }

    @Deprecated
    public void displayTransform(Graphics g){
        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(getAbsoluteX(), getAbsoluteY());

        // apply display mapping
        //
        gXform.translate(0,getHeight());
        gXform.scale(1f,-1f);

        // move the drawing coordinates as part of the "local origin" transformations
        //
        gXform.translate(-getAbsoluteX(),-getAbsoluteY());
        g.setTransform(gXform);
    }

    private Transform buildWorldToNDXform(float winWidth, float winHeight, float winLeft, float winBottom){
        Transform tmpXform = Transform.makeIdentity();
        tmpXform.scale(1/winWidth, 1/winHeight);
        tmpXform.translate(-winLeft,-winBottom);
        return tmpXform;
    }

    private Transform buildNDtoDisplayXform(float displayWidth, float displayHeight){
        Transform tmpXform = Transform.makeIdentity();
        tmpXform.translate(0,displayHeight);
        tmpXform.scale(displayWidth, -displayHeight);
        return tmpXform;
    }
    private void setupVTM(Graphics g){
        Transform worldToND, ndToDisplay, theVTM;

        if(gw.zoomed()){
            winLeft = (float) (-this.getWidth()/20);
            winBottom = (float) (-this.getHeight()/20);
            winRight = (float) (this.getWidth()*1.05);
            winTop = (float) (this.getHeight()*1.05);
        }else{
            winLeft = winBottom = 0;
            winRight = this.getWidth();
            winTop = this.getHeight();
        }

        float winHeight = winTop - winBottom;
        float winWidth= winRight - winLeft;

        worldToND = buildWorldToNDXform(winWidth,winHeight,winLeft,winBottom);
        ndToDisplay = buildNDtoDisplayXform(this.getWidth(),this.getHeight());
        theVTM = ndToDisplay.copy();
        theVTM.concatenate(worldToND);

        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(getAbsoluteX(),getAbsoluteY());
        gXform.concatenate(theVTM);
        gXform.translate(-getAbsoluteX(),-getAbsoluteY());
        g.setTransform(gXform);
    }

    Transform getVTM(){
        Transform worldToND, ndToDisplay, theVTM;

        if(gw.zoomed()){
            winLeft = (float) (-this.getWidth()/20);
            winBottom = (float) (-this.getHeight()/20);
            winRight = (float) (this.getWidth()*1.05);
            winTop = (float) (this.getHeight()*1.05);
        }else{
            winLeft = winBottom = 0;
            winRight = this.getWidth();
            winTop = this.getHeight();
        }

        float winHeight = winTop - winBottom;
        float winWidth= winRight - winLeft;

        worldToND = buildWorldToNDXform(winWidth,winHeight,winLeft,winBottom);
        ndToDisplay = buildNDtoDisplayXform(this.getWidth(),this.getHeight());
        theVTM = ndToDisplay.copy();
        theVTM.concatenate(worldToND);
        return theVTM;

    }

    private Transform getInverseVTM(){
        Transform inverseVTM = Transform.makeIdentity();

        try{
            getVTM().getInverse(inverseVTM);
        }catch (Transform.NotInvertibleException e){
            e.printStackTrace();
        }
        return inverseVTM;
    }

    private Point2D transformPoint2D(Transform t, Point2D p){
        float[] in = new float[2];
        float[] out = new float[2];
        in[0] = (float)p.getX();
        in[1] = (float)p.getY();
        t.transformPoint(in,out);
        return new Point2D(out[0],out[1]);
    }

    @Override
    public void pointerPressed(int x, int y){
        x = x - getAbsoluteX();
        y = (y - getAbsoluteY());
        Point2D sp = new Point2D(x,y);

        this.gw.selectFire(sp);
        this.gw.setBezierCurve(transformPoint2D(
                getInverseVTM(),new Point2D(x,y)));
    }

    @Override
    public void Notify() {
        for(int i=0; i<fireObservers.size();i++){
            fireObservers.get(i).update(this);
        }
    }


    @Override
    public void paint(Graphics g){
        super.paint(g);

        Point parentOrigin = new Point(this.getX(), this.getY());
        Point screenOrigin = new Point(getAbsoluteX(), getAbsoluteY());
        //displayTransform(g);
        // draw axis
        //
        /*g.setColor(ColorUtil.LTGRAY);
        //Horizontal Line
        g.drawLine(0,
                getHeight()/2 + screenOrigin.getY()/2
                        - parentOrigin.getY()/2, getWidth(),
                getHeight()/2 + screenOrigin.getY()/2
                        - parentOrigin.getY()/2);

        //Vertical Line
        g.drawLine(getWidth()/2, 0,
                getWidth()/2, getHeight() + screenOrigin.getY());*/

        for(GameObject go: gw.getGameObjectCollection()){
            setupVTM(g);

            go.draw(g, parentOrigin, screenOrigin);// maybe
            g.resetAffine();
        }
    }

    public void updateLocalTransforms() {
        for(GameObject go: gw.getGameObjectCollection()){
            go.updateLocalTransforms();
        }
    }

    @Override
    public void laidOut(){
        gw.setDimension(new Dimension(this.getWidth(), this.getHeight()));
    }
}
