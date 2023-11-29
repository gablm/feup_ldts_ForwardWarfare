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
    protected TextColor borderBackground;
    protected TextColor controllerColor;
    protected int coins;

    public ControllerBase(List<Element> initialFacilities, TextColor controllerColor) throws InvalidControllerException {
        if (initialFacilities == null || initialFacilities.size() != 2)
            throw new InvalidControllerException("Invalid initial Factory and Base");
        facilities.add(initialFacilities.get(1));
        base = initialFacilities.get(0);
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
        coins -= price;
        return true;
    }

    public void endRound() {
        coins += 100;
        for (Element i : facilities) {
                coins += 30;
        }
    }

    public Border getSelection1() {
        return selection1;
    }
    public Border getSelection2() {
        return selection2;
    }
    public void setSelection1(Border selection1) {
        this.selection1 = selection1;
    }
    public void setSelection2(Border selection2) {
        this.selection2 = selection2;
    }
    public void setBorderBackground(TextColor color) {
        this.borderBackground = color;
    }
    @Override
    public void draw(TextGraphics graphics) {
        base.draw(graphics, controllerColor);
        for (Element i : troops)
            i.draw(graphics, controllerColor);
        for (Element i : facilities)
            i.draw(graphics, controllerColor);
    }

    @Override
    public void drawBorder(TextGraphics graphics) {
        if (selection2 != null) {
            graphics.setBackgroundColor(borderBackground);
            selection2.draw(graphics, TextColor.ANSI.RED_BRIGHT);
        }
        if (selection1 != null) {
            graphics.setBackgroundColor(borderBackground);
            selection1.draw(graphics, selection2 == null ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.CYAN_BRIGHT);
        }
    }
}
