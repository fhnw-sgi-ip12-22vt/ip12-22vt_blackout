package com.blackout.view.pui;

import com.blackout.component.LEDButton;
import com.blackout.component.PIN;
import com.blackout.controller.AppController;
import com.blackout.model.AppModel;
import com.blackout.util.mvcbase.PuiBase;
import com.pi4j.context.Context;

public class AppPUI extends PuiBase<AppModel, AppController> {
    // first pair, of inputs
    private LEDButton btnA;
    private LEDButton btnB;
    private LEDButton btnC;
    private LEDButton btnAckA;


    // second pair, of inputs
    private LEDButton btnZ;
    private LEDButton btnY;
    private LEDButton btnX;
    private LEDButton btnAckB;
    private LEDButton shutdownBtn;

    public AppPUI(AppController controller, Context pi4J) {
        super(controller, pi4J);
    }

    @Override
    public void initializeParts() {
        btnZ = new LEDButton(pi4J, PIN.TXD, PIN.RXD);
        btnY = new LEDButton(pi4J, PIN.D24, PIN.D23);
        btnX = new LEDButton(pi4J, PIN.CEO, PIN.CE1);
        btnAckB = new LEDButton(pi4J, PIN.D20, PIN.D21);

        btnA = new LEDButton(pi4J, PIN.D27, PIN.D17);
        btnC = new LEDButton(pi4J, PIN.MOSI, PIN.MISO);
        btnB = new LEDButton(pi4J, PIN.D5, PIN.D0);
        btnAckA = new LEDButton(pi4J, PIN.PWM13, PIN.PWM19);

        shutdownBtn = new LEDButton(pi4J, PIN.SDA1, PIN.SCL1);
        shutdownBtn.setLed(false);
    }

    @Override
    public void setupUiToActionBindings(AppController controller) {
        btnA.onDown(controller::actionA);
        btnB.onDown(controller::actionB);
        btnC.onDown(controller::actionC);
        btnAckA.onDown(controller::actionAckA);

        btnZ.onDown(controller::actionZ);
        btnY.onDown(controller::actionY);
        btnX.onDown(controller::actionX);
        btnAckB.onDown(controller::actionAckB);

        shutdownBtn.onDown(() -> {
            controller.shutdownHard();
        });
    }

    @Override
    public void setupModelToUiBindings(AppModel model) {
        onChangeOf(model.ledA).execute((oldValue, newValue) -> btnA.setLed(newValue));
        onChangeOf(model.ledB).execute((oldValue, newValue) -> btnB.setLed(newValue));
        onChangeOf(model.ledC).execute((oldValue, newValue) -> btnC.setLed(newValue));
        onChangeOf(model.ledAckA).execute((oldValue, newValue) -> btnAckA.setLed(newValue));

        onChangeOf(model.ledZ).execute((oldValue, newValue) -> btnZ.setLed(newValue));
        onChangeOf(model.ledY).execute((oldValue, newValue) -> btnY.setLed(newValue));
        onChangeOf(model.ledX).execute((oldValue, newValue) -> btnX.setLed(newValue));
        onChangeOf(model.ledAckB).execute((oldValue, newValue) -> btnAckB.setLed(newValue));
    }
}
