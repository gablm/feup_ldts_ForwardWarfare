package com.ldts.ForwardWarfare.Controller;

import com.googlecode.lanterna.TextColor;
import com.ldts.ForwardWarfare.Element.Element;

import java.util.List;

public class Bot extends ControllerBase {
    public Bot(List<Element> initialFacilities, TextColor color) throws InvalidControllerException {
        super(initialFacilities, color);
    }
}
