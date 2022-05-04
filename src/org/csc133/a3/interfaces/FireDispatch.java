package org.csc133.a3.interfaces;

import com.codename1.ui.geom.Point2D;

public interface FireDispatch {
    boolean contains(Point2D p);
    void select(boolean selected);
    boolean isSelected();
    void update(Object o);
}
