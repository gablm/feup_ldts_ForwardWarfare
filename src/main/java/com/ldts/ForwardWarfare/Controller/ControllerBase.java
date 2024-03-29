package com.ldts.ForwardWarfare.Controller;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Base;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Facility.OilPump;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.Fields;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Map.Map;
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

    private String name;

    public ControllerBase(List<Element> initialFacilities, TextColor controllerColor, String name) throws InvalidControllerException {
        if (initialFacilities == null || initialFacilities.size() != 2)
            throw new InvalidControllerException("Invalid initial Factory and Base");
        Element factory = initialFacilities.get(1);
        factory.setForegroundColor(controllerColor);
        facilities.add(factory);
        base = initialFacilities.get(0);
        base.setForegroundColor(controllerColor);
        this.controllerColor = controllerColor;
        this.coins = 15;
        this.name = name;
    }

    @Override
    public Element getBase() {
        return base;
    }

    @Override
    public int getBaseLives() {
        return ((Base) ((Tile) base).getFacility()).getLives();
    }

    @Override
    public List<Element> getTroops() {
        return troops;
    }

    @Override
    public List<Element> getFacilities() {
        return facilities;
    }

    @Override
    public int getCoins() {
        return coins;
    }

    @Override
    public boolean buy(Element troop, int price) {
        if (coins < price)
            return false;
        troops.add(troop);
        troop.setForegroundColor(controllerColor);
        coins -= price;
        return true;
    }

    @Override
    public void setBase(Element newbase) {
        base = newbase;
    }

    @Override
    public void addFacility(Element facility) {
        facilities.add(facility);
        facility.setForegroundColor(controllerColor);
    }

    @Override
    public void endRound() {
        canPlay = false;
        selection1 = null;
        selection2 = null;
    }

    @Override
    public Border getSelection1() {
        return selection1;
    }

    @Override
    public Border getSelection2() {
        return selection2;
    }

    @Override
    public void setSelection1(Border selection1) {
        this.selection1 = selection1;
        if (selection1 != null)
            this.selection1.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
    }

    @Override
    public void setSelection2(Border selection2) {
        this.selection2 = selection2;
        if (selection2 != null)
            this.selection2.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
    }

    @Override
    public void draw(TextGraphics graphics, Map map) {
        base.draw(graphics);
        for (Element i : troops) {
            graphics.setBackgroundColor(map.at(i.getPosition()).getColor());
            i.draw(graphics);
        }
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
        coins += 5;
        for (Element i : facilities) {
            Facility facility = ((Tile)i).getFacility();
            if (facility instanceof OilPump)
                coins += 5;
            if(facility.getUsed())
                facility.execute();
        }
        for (Element i : troops)
            ((Playable) i).setHasMoved(false);

        Base baseT = (Base)((Tile) base).getFacility();
        if (baseT.getUsed())
            baseT.execute();
        if(!baseT.getAttackedLastTurn())
            baseT.setLives(2);
        baseT.setAttackedLastTurn(false);
    }

    @Override
    public boolean canPlay() {
        return canPlay;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TextColor getControllerColor() {
        return controllerColor;
    }
}
