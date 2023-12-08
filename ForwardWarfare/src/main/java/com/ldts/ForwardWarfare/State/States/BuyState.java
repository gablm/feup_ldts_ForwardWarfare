package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Facility.Airport;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Facility.Factory;
import com.ldts.ForwardWarfare.Element.Facility.Port;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

public class BuyState extends BaseState{
    private Facility facilitySelected;
    public BuyState(Controller p1, Controller p2, Map map, Facility facility) {
        super(p1, p2, map);
        facilitySelected = facility;
    }

    @Override
    public State play(Action action) {
        if (action == Action.ESCAPE)
            return new MoveEndState(p1, p2, map);
        return this;
    }

    @Override
    public void draw(TextGraphics graphics) {
        if (facilitySelected.getClass() == Factory.class)
            drawFactoryShop(graphics);
        else if (facilitySelected.getClass() == Airport.class)
            drawAirportShop(graphics);
        else if (facilitySelected.getClass() == Port.class)
            drawPortShop(graphics);
        else
            System.out.println("Invalid facility");
    }

    @Override
    public boolean requiresInput() {
        return true;
    }

    private void drawFactoryShop(TextGraphics graphics)
    {
        graphics.setForegroundColor(new TextColor.RGB(255,255,255));
        graphics.setBackgroundColor(new TextColor.RGB(80,80,80));
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');
        String coins = new StringBuilder().append(p1.getCoins()).append("!").toString();
        graphics.putString(2, 13, "(  2");
        graphics.putString(2, 14, "_  5");
        graphics.putString(2, 15, "@ 10");
        graphics.putString(9, 13, "#  5");
        graphics.putString(9, 14, "$ 15");
        graphics.putString(9, 15, "+  7");
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(1, 11, " Factory-Shop");
        graphics.setForegroundColor(new TextColor.RGB(255,223,0));
        graphics.putString(6, 16, coins);
        graphics.putString(6, 13, "!");
        graphics.putString(6, 14, "!");
        graphics.putString(6, 15, "!");
        graphics.putString(13, 13, "!");
        graphics.putString(13, 14, "!");
        graphics.putString(13, 15, "!");
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
    }

    private void drawAirportShop(TextGraphics graphics)
    {

    }

    private void drawPortShop(TextGraphics graphics)
    {

    }

}
