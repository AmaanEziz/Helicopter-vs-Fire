package org.csc133.a3.gameObjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a3.interfaces.Drawable;

public abstract class GameObject implements Drawable {
    protected int color;
    protected Dimension dimension;
    protected Dimension worldSize;
    protected Transform myTranslation, myRotation, myScale;

    public GameObject(){
        myTranslation = Transform.makeIdentity();
        myRotation = Transform.makeIdentity();
        myScale = Transform.makeIdentity();
    }

    public void setColor(int color){
        this.color = color;
    }

    public int getColor(){
        return this.color;
    }

    public void setDimension(Dimension dimension){
        this.dimension = new Dimension(dimension.getWidth(),
                dimension.getHeight());
    }

    public Dimension getDimension(){
        return dimension;
    }

    public Point2D getLocation(){
        return new Point2D(myTranslation.getTranslateX(),
                myTranslation.getTranslateY());
    }

    public int getWidth(){
        return dimension.getWidth();
    }

    public int getHeight(){
        return dimension.getHeight();
    }

    public void rotate(double degrees) {
        myRotation.rotate((float)Math.toRadians(degrees), 0, 0);
    }

    public void scale(double sx, double sy) {
        myScale.scale((float)sx, (float)sy);
    }

    public void translate(double tx, double ty) {
        myTranslation.translate((float)tx, (float)ty);
    }

    private Transform gOrigXform;

    protected Transform preLTTransform(Graphics g, Point originScreen){
        Transform gXform = Transform.makeIdentity();

        // get the current transform and save it
        // PreLTTransforms
        g.getTransform(gXform);
        gOrigXform = gXform.copy();

        // move the drawing coordinates back
        //
        gXform.translate(originScreen.getX(),originScreen.getY());
        return gXform;
    }

    public abstract void updateLocalTransforms();

    protected void localTransforms(Transform gxForm){
        // append Objects's LTs to the graphics object's transform
        // localTransforms
        gxForm.translate(myTranslation.getTranslateX(),
                myTranslation.getTranslateY());
        gxForm.concatenate(myRotation);
        gxForm.scale(myScale.getScaleX(), myScale.getScaleY());
    }

    protected void postLTTransform(Graphics g, Point originScreen,
                                   Transform gXform) {
        // move the drawing coordinates so that the
        // local origin coincides with the screen origin postLTTransforms
        gXform.translate(-originScreen.getX(),-originScreen.getY());
        g.setTransform(gXform);
    }

    protected void restoreOriginalTransform(Graphics g) {
        // restore the original xform
        // restoreLTTransforms
        g.setTransform(gOrigXform);
    }

    protected void containerTranslate(Graphics g, Point parentOrigin){
        Transform gxForm = Transform.makeIdentity();
        g.getTransform(gxForm);
        gxForm.translate(parentOrigin.getX(), parentOrigin.getY());
        g.setTransform(gxForm);
    }

    protected void cn1ForwardPrimitiveTranslate(Graphics g,
                                                Dimension pDimension){
        Transform gxForm = Transform.makeIdentity();
        g.getTransform(gxForm);
        gxForm.translate(-pDimension.getWidth()/2,
                -pDimension.getHeight()/2);
        g.getTransform(gxForm);
    }

    protected void cn1ReversePrimitiveTranslate(Graphics g,
                                                Dimension pDimension){
        Transform gxForm = Transform.makeIdentity();
        g.getTransform(gxForm);
        gxForm.translate(pDimension.getWidth()/2,
                pDimension.getHeight()/2);
        g.getTransform(gxForm);
    }

    protected abstract void localDraw(Graphics g, Point parentOrigin,
                                      Point screenOrigin);

    public void draw(Graphics g, Point parentOrigin, Point screenOrigin) {
        Transform gXform = preLTTransform(g, screenOrigin);
        localTransforms(gXform);
        postLTTransform(g, screenOrigin, gXform);
        localDraw(g,parentOrigin,screenOrigin);
        restoreOriginalTransform(g);
    }
}
