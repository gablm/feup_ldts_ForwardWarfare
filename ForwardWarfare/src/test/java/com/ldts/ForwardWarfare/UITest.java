package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.UI.HowToPlayMenu;
import com.ldts.ForwardWarfare.UI.UI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class UITest {
    @Test
    public void HowToPlayMenuTest() throws IOException, MapParseException, URISyntaxException {
        UI HowToPlayMenu=new HowToPlayMenu();
        Assertions.assertEquals(new TerminalSize(75,50),HowToPlayMenu.getTerminalSize());
        Assertions.assertEquals(15,HowToPlayMenu.getFontsize());
        

    }
}
