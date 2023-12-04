package com.ldts.ForwardWarfare.UI;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.ldts.ForwardWarfare.Map.MapParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class HowToPlayMenu extends UI{
    public HowToPlayMenu() {
        super(new TerminalSize(41,23),25);
    }

    @Override
    public boolean build() throws IOException, MapParseException, URISyntaxException {
        return false;
    }

    @Override
    public void draw() throws IOException {

    }

    @Override
    public void addcomp() throws FileNotFoundException, MapParseException, URISyntaxException {

    }

    @Override
    public boolean run() throws IOException {
        return false;
    }

    @Override
    public void processKey(KeyStroke key) {

    }
}
