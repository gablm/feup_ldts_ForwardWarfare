package com.ldts.ForwardWarfare.UI;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.ldts.ForwardWarfare.Map.MapParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class BattleUI extends UI {

    public BattleUI() {
        super(new TerminalSize(41,23),25);
    }

    @Override
    public UiStates build() throws IOException, MapParseException, URISyntaxException {

        return null;
    }

    @Override
    public void draw() throws IOException {

    }

    @Override
    public void addcomp() throws FileNotFoundException, MapParseException, URISyntaxException {

    }

    @Override
    public UiStates run() throws IOException {

        return null;
    }

    @Override
    public void processKey(KeyStroke key) {

    }
}
