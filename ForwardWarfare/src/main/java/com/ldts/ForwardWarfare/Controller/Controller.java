package com.ldts.ForwardWarfare.Controller;

import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Facility;

import java.util.List;

public interface Controller {
    boolean buy(Element troops, int price);
    void endRound();
    Element getBase();
    List<Element> getTroops();
    List<Facility> getFacilities();
}
