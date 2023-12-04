package com.ldts.ForwardWarfare.Controller;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Facility.OilPump;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.Fields;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class ControllerBase implements Controller {

    protected List<Element> troops = new ArrayList<>();
    protected List<Element> facilities = new ArrayList<>();
    protected Element base;
    protected Border selection1;
    protected Border selection2;
    protected TextColor controllerColor;
    protected boolean canPlay = true;
    protected int coins;

    public ControllerBase(List<Element> initialFacilities, TextColor controllerColor) throws InvalidControllerException {
        if (initialFacilities == null || initialFacilities.size() != 2)
            throw new InvalidControllerException("Invalid initial Factory and Base");
        Element factory = initialFacilities.get(1);
        factory.setForegroundColor(controllerColor);
        facilities.add(factory);
        base = initialFacilities.get(0);
        base.setForegroundColor(controllerColor);
        this.controllerColor = controllerColor;
        this.coins = 100;
    }

    public Element getBase() {
        return base;
    }

    public List<Element> getTroops() {
        return troops;
    }

    public List<Element> getFacilities() {
        return facilities;
    }

    public int getCoins() {
        return coins;
    }

    public boolean buy(Element troop, int price) {
        if (coins < price)
            return false;
        troops.add(troop);
        troop.setForegroundColor(controllerColor);
        coins -= price;
        return true;
    }

    public void endRound() {
        canPlay = false;
    }

    public Border getSelection1() {
        return selection1;
    }
    public Border getSelection2() {
        return selection2;
    }
    public void setSelection1(Border selection1) {
        this.selection1 = selection1;
        if (selection1 != null)
            this.selection1.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
    }
    public void setSelection2(Border selection2) {
        this.selection2 = selection2;
        if (selection2 != null)
            this.selection2.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
    }

    @Override
    public void draw(TextGraphics graphics) {
        base.draw(graphics);
        for (Element i : troops)
            i.draw(graphics);
        for (Element i : facilities)
            i.draw(graphics);
    }

    @Override
    public void drawBorder(TextGraphics graphics) {
        if (selection1 != null)
            selection1.draw(graphics);
        if (selection2 != null)
            selection2.draw(graphics);
    }

    @Override
    public void resetRound() {
        canPlay = true;
        coins += 100;
        for (Element i : facilities) {
            coins += 30;
        }
    }

    @Override
    public boolean canPlay() {
        return canPlay;
    }
}
