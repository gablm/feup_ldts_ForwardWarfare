package com.ldts.ForwardWarfare.Controller;

import com.googlecode.lanterna.input.KeyStroke;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Map.Map;

public interface Controller {
    boolean buy(Element troops, int price);
    void endRound();
}
