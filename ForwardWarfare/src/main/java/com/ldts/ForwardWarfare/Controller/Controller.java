package com.ldts.ForwardWarfare.Controller;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.State;

import java.util.List;

public interface Controller {
    boolean buy(Element troops, int price);
    void endRound();
    Element getBase();
    List<Element> getTroops();
    List<Element> getFacilities();
    int getCoins();
    Border getSelection1();
    public Border getSelection2();
    void setSelection1(Border selection1);
    void setSelection2(Border selection2);
    void draw(TextGraphics textGraphics);
    void drawBorder(TextGraphics graphics);

    State getInitialState(Controller p2, Map map);
}
