package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.State;

public class Drawer {
    private Controller p1;
    private Controller p2;
    private Map map;

    private int turnCount = 0;

    public Drawer(Controller p1, Controller p2, Map map) {
        this.p1 = p1;
        this.p2 = p2;
        this.map = map;
    }

    public void draw(TextGraphics graphics, State state) {
        map.draw(graphics);
        p1.draw(graphics, map);
        p2.draw(graphics, map);
        state.draw(graphics);
        p1.drawBorder(graphics);
        p2.drawBorder(graphics);
        drawSide(graphics, p1, p2);
    }

    public void increaseTurnCount() {
        turnCount++;
    }

    public int getTurnCount() {
        return turnCount;
    }

    private void drawSide(TextGraphics graphics, Controller p1, Controller p2) {
        graphics.setBackgroundColor(TextColor.ANSI.YELLOW);
        graphics.fillRectangle(new TerminalPosition(15,0), new TerminalSize(10,19), ' ');

        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(16, 2, "TURN");
        graphics.putString(22, 2, turnCount % 3 == 0 ? p1.getName() : p2.getName());
        graphics.putString(16, 4, "ROUND");
        String rounds = new StringBuilder().append(turnCount / 3 + 1).toString();
        graphics.putString(22, 4, rounds);

        graphics.putString(16, 8, "P1");
        String coins = new StringBuilder().append(p1.getCoins()).append("!").toString();
        graphics.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        graphics.putString(24 - coins.length(), 8, coins);
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(16 + 2, 10, "BASE");
        graphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);

        int p1Lives = p1.getBaseLives();
        graphics.setForegroundColor(p1Lives != 2 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.GREEN_BRIGHT);
        graphics.putString(17, 11, String.format("%d " + (p1Lives == 1 ? "Life" : "Lives"), p1Lives));

        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(16, 13, "P2");
        coins = new StringBuilder().append(p2.getCoins()).append("!").toString();
        graphics.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        graphics.putString(24 - coins.length(), 13, coins);
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(18, 15, "BASE");

        int p2Lives = p2.getBaseLives();
        graphics.setForegroundColor(p2Lives != 2 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.GREEN_BRIGHT);
        graphics.putString(17, 16, String.format("%d " + (p2Lives == 1 ? "Life" : "Lives"), p2Lives));
    }
}
