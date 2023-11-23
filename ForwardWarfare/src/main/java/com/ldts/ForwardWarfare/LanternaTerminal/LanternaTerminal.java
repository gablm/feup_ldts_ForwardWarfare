package com.ldts.ForwardWarfare.LanternaTerminal;

import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public interface LanternaTerminal {
    Screen createScreen() throws IOException;
}
