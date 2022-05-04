package org.csc133.a3.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import org.csc133.a3.GameWorld;
import org.csc133.a3.commands.*;

public class ControlCluster extends Container {
    GameWorld gw;
    private Container leftBox = new Container(
            new GridLayout(1,3));
    private  Container rightBox = new Container(
            new GridLayout(1,3));
    private Container middleBox = new Container(
            new GridLayout(1,7));

    public ControlCluster(GameWorld gw) {
        this.gw = gw;
        this.setLayout(new BorderLayout());
        this.getAllStyles().setBgTransparency(255);
        this.getAllStyles().setBgColor(ColorUtil.WHITE);

        Button left = new Button ("Left");
        left.setCommand(new Left(gw));
        left.getAllStyles().setBgTransparency(255);
        left.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        leftBox.add(left);

        Button right = new Button ("Right");
        right.setCommand(new Right(gw));
        right.getAllStyles().setBgTransparency(255);
        right.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        leftBox.add(right);

        Button fight = new Button ("Fight");
        fight.setCommand(new Fight(gw));
        fight.getAllStyles().setBgTransparency(255);
        fight.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        leftBox.add(fight);

        Button padding1 = new Button("  ");
        middleBox.add(padding1);
        Button padding2 = new Button("  ");
        middleBox.add(padding2);

        Button startEngine = new Button("Start Engine");
        startEngine.setCommand(new StartEngine(gw));
        startEngine.getAllStyles().setBgTransparency(255);
        startEngine.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        middleBox.add(startEngine);

        Button padding3 = new Button("");
        middleBox.add(padding3);

        Button exitGame = new Button("Exit");
        exitGame.setCommand(new Exit(gw));
        exitGame.getAllStyles().setBgTransparency(255);
        exitGame.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        middleBox.add(exitGame);


        Button padding4 = new Button("  ");
        middleBox.add(padding4);

        Button padding5 = new Button("  ");
        middleBox.add(padding5);

        Button drink = new Button ("Drink");
        drink.setCommand(new Drink(gw));
        drink.getAllStyles().setBgTransparency(255);
        drink.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        rightBox.add(drink);

        Button brake = new Button ("Brake");
        brake.setCommand(new Brake(gw));
        brake.getAllStyles().setBgTransparency(255);
        brake.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        rightBox.add(brake);

        Button accel = new Button ("Accel");
        accel.setCommand(new Accel(gw));
        accel.getAllStyles().setBgTransparency(255);
        accel.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        rightBox.add(accel);
        
        this.add(BorderLayout.WEST, leftBox);
        this.add(BorderLayout.CENTER, middleBox);
        this.add(BorderLayout.EAST, rightBox);
    }
}
