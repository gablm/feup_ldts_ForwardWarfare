package com.ldts.ForwardWarfare.LanternaTerminal.GameTerminal;

import com.googlecode.lanterna.TerminalSize;
import com.ldts.ForwardWarfare.LanternaTerminal.GameTerminal.GameTerminal;
import com.ldts.ForwardWarfare.LanternaTerminal.LanternaTerminal;
import com.ldts.ForwardWarfare.LanternaTerminal.LanternaTerminalFactory;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class GameTerminalFactory implements LanternaTerminalFactory {
    private int fontSize = 4;
    private TerminalSize dimensions;
    @Override
    public void setFontSize(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Font size should be positive.");
        fontSize = size;
    }

    @Override
    public void setDimensions(int width, int height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("Both numbers should be positive.");
        dimensions = new TerminalSize(width, height);
    }

    @Override
    public LanternaTerminal createTerminal() throws IOException, URISyntaxException, FontFormatException {
        return new GameTerminal(dimensions, fontSize);
    }
}
