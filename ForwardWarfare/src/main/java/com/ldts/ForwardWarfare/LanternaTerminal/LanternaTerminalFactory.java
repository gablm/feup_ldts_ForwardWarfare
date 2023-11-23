package com.ldts.ForwardWarfare.LanternaTerminal;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public interface LanternaTerminalFactory {
    void setFontSize(int size);
    void setDimensions(int width, int height);
    LanternaTerminal createTerminal() throws IOException, URISyntaxException, FontFormatException;
}
