package com.ldts.ForwardWarfare.Controller;

import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Facility.OilPump;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.Fields;

import com.googlecode.lanterna.input.KeyStroke;
import com.ldts.ForwardWarfare.Map.Map;

import java.util.ArrayList;
import java.util.List;

public class Player implements Controller {
    private List<Element> troops = new ArrayList<>();
    private List<Facility> facilities = new ArrayList<>();
    private Element base;
    private int coins;
    public Player(List<Element> inicialFacilities) throws InvalidControllerException {
        if (inicialFacilities == null || inicialFacilities.size() != 2)
            throw new InvalidControllerException("Invalid initial Factory and Base");
        facilities.add(((Fields)inicialFacilities.get(1)).getFacility());
        base = inicialFacilities.get(0);
        this.coins = 100;
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
