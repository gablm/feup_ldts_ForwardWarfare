package com.ldts.ForwardWarfare.UI;

import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.LanternaTerminal;

import java.io.IOException;

public abstract class UI {
    protected LanternaTerminal UITerminal;
    protected Screen screen;

    UI() {}

    public abstract void build() throws IOException;

}
