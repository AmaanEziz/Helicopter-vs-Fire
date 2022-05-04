package org.csc133.a3.views;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.GridLayout;
import org.csc133.a3.GameWorld;

public class GlassCockPit extends Container {
    GameWorld gw;
    Label heading;
    Label speed;
    Label fuel;
    Label fires;
    Label fireSize;
    Label damage;
    Label loss;

    public GlassCockPit(GameWorld gw) {
        this.gw = gw;

        this.getAllStyles().setBgTransparency(255);
        this.setLayout(new GridLayout(2, 7));
        this.add("HEADING");
        this.add("SPEED");
        this.add("FUEL");
        this.add("FIRES");
        this.add("FIRE SIZE");
        this.add("DAMAGE");
        this.add("LOSS");

        heading = new Label("0");
        speed = new Label("0");
        fuel = new Label("0");
        fires = new Label("0");
        fireSize = new Label("0");
        damage = new Label("0");
        loss = new Label("0");

        this.add(heading);
        this.add(speed);
        this.add(fuel);
        this.add(fires);
        this.add(fireSize);
        this.add(damage);
        this.add(loss);
    }

    public void update(){
        heading.setText(gw.getHeading());
        speed.setText(gw.getSpeed());
        fuel.setText(gw.getFuel());
        fires.setText(gw.getFireCount());
        fireSize.setText(gw.getFireSize());
        damage.setText(gw.getDamage());
        loss.setText(gw.getLoss());
    }
}
