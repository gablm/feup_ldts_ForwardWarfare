package com.ldts.ForwardWarfare.Controller;

import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Facility.OilPump;
import com.ldts.ForwardWarfare.Element.Tile.Fields;

import java.util.ArrayList;
import java.util.List;

public class Bot implements Controller {
    private List<Element> troops = new ArrayList<>();
    private List<Facility> facilities = new ArrayList<>();
    private Element base;
    private int coins;
    public Bot(List<Element> initialFacilities) throws InvalidControllerException {
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
