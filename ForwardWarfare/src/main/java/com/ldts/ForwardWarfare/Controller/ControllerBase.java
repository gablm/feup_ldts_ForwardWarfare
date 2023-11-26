package com.ldts.ForwardWarfare.Controller;

import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Facility.OilPump;
import com.ldts.ForwardWarfare.Element.Tile.Fields;

import java.util.ArrayList;
import java.util.List;

public abstract class ControllerBase implements Controller {

    protected List<Element> troops = new ArrayList<>();
    protected List<Facility> facilities = new ArrayList<>();
    protected Element base;
    protected int coins;

    public ControllerBase(List<Element> initialFacilities) throws InvalidControllerException {
        if (initialFacilities == null || initialFacilities.size() != 2)
            throw new InvalidControllerException("Invalid initial Factory and Base");
        facilities.add(((Fields)initialFacilities.get(1)).getFacility());
        base = initialFacilities.get(0);
        this.coins = 100;
    }

    public Element getBase() {
        return base;
    }

    public List<Element> getTroops() {
        return troops;
    }

    public List<Facility> getFacilities() {
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
        for (Facility i : facilities) {
            if (i instanceof OilPump)
                coins += 30;
        }
    }
}
