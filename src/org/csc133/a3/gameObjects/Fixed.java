package org.csc133.a3.gameObjects;

public abstract class Fixed extends GameObject {

    public Fixed(){
        super();
    }

    public boolean isCollidingWith(Helicopter helicopter){
        return helicopter.myTranslation.getTranslateX()
                + helicopter.getWidth()/4 > myTranslation.getTranslateX()
                - dimension.getWidth()/2 &&
                helicopter.myTranslation.getTranslateX()
                        - helicopter.getWidth()/4
                        < myTranslation.getTranslateX()
                        + dimension.getWidth()/2 &&
                helicopter.myTranslation.getTranslateY()
                        + helicopter.getHeight()/4
                        > myTranslation.getTranslateY()
                        - dimension.getHeight()/2 &&
                helicopter.myTranslation.getTranslateY()
                        - helicopter.getHeight()/4
                        < myTranslation.getTranslateY()
                        + dimension.getHeight()/2;
    }
}
