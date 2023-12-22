package com.ldts.ForwardWarfare.UI.Component;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.ldts.ForwardWarfare.Element.Position;

import java.util.ArrayList;
import java.util.List;

public class ColorGrid extends Component {
    private TextColor BorderColor;
    private int Selected = 50;
    private int highlight = 25;
    private int normalborder = 10;
    private List<Button> colorList = new ArrayList<>();
    private TextColor player1Color;
    private TextColor player2Color;
    private int c = 0;
    private int s = -1;
    private List<Button> butonused = new ArrayList<>();
    private boolean Gamemode;
    public ColorGrid(TextColor backColor, TextColor forgColor, Position position, int BorderFadeIntencity,boolean Gamemode) {
        super(backColor, forgColor, position, new TerminalSize(29,19), BorderFadeIntencity);
        this.Gamemode = Gamemode;
        BorderColor = backColor;
        colorList.add(new Button(TextColor.ANSI.RED, new TextColor.RGB(0, 0, 0), new Position(position.getX() + 1, position.getY() + 5), new TerminalSize(6, 6), " ", normalborder));
        colorList.add(new Button(TextColor.ANSI.BLUE, new TextColor.RGB(0, 0, 0), new Position(position.getX() + 8, position.getY() + 5), new TerminalSize(6, 6), " ", normalborder));
        colorList.add(new Button(new TextColor.RGB(204, 0, 204), new TextColor.RGB(0, 0, 0), new Position(position.getX() + 15, position.getY() + 5), new TerminalSize(6, 6), " ", normalborder));
        colorList.add(new Button(new TextColor.RGB(51,25, 0), new TextColor.RGB(0, 0, 0), new Position(position.getX() + 22, position.getY() + 5), new TerminalSize(6, 6), " ", normalborder));
        colorList.add(new Button(new TextColor.RGB(127, 0, 255), new TextColor.RGB(0, 0, 0), new Position(position.getX() + 1, position.getY() + 12), new TerminalSize(6, 6), " ", normalborder));
        colorList.add(new Button(new TextColor.RGB(0, 153, 153), new TextColor.RGB(0, 0, 0), new Position(position.getX() + 8, position.getY() + 12), new TerminalSize(6, 6), " ", normalborder));
        colorList.add(new Button(new TextColor.RGB(0, 51, 0), new TextColor.RGB(0, 0, 0), new Position(position.getX() + 15, position.getY() + 12), new TerminalSize(6, 6), " ", normalborder));
        colorList.add(new Button(new TextColor.RGB(101, 73, 73), new TextColor.RGB(0, 0, 0), new Position(position.getX() + 22, position.getY() + 12), new TerminalSize(6, 6), " ", normalborder));

        if (!Gamemode)
        {
            colorList.get(0).setBorderFadeIntensity(Selected);
            colorList.get(0).setFixBorder(true);
            butonused.add(colorList.get(0));
            player2Color = butonused.get(0).getBackColor();
            c = 1;
        }
    }
    private void Buttonhighligted(boolean next)
    {
        boolean notfound=true;
        while (notfound) {
            if (next) {
                colorList.get(c).setBorderFadeIntensity(normalborder);
                if (c + 1 > colorList.size() - 1)
                    c = 0;
                else
                    c++;
                if (!butonused.contains(colorList.get(c))) {
                    colorList.get(c).setBorderFadeIntensity(highlight);
                    notfound = false;
                }
            } else {
                colorList.get(c).setBorderFadeIntensity(normalborder);
                if (c - 1 < 0) {
                    c = colorList.size() - 1;
                } else
                    c--;
                if (!butonused.contains(colorList.get(c))) {
                    colorList.get(c).setBorderFadeIntensity(highlight);
                    notfound = false;
                }
            }
        }
    }
    private void setupbord() {
        int r = backColor.getRed() - borderFadeIntensity;
        if (r <= 0)
            r = backColor.getRed();

        int g = backColor.getGreen() - borderFadeIntensity;
        if (g <= 0)
            g = backColor.getGreen();

        int b = backColor.getBlue() - borderFadeIntensity;
        if (b <= 0)
            b = backColor.getBlue();
        BorderColor = new TextColor.RGB(r, g, b);
    }

    public boolean processKey( KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowRight)
        {
            Buttonhighligted(true);
            return true;
        } else if (key.getKeyType() == KeyType.ArrowLeft) {
            Buttonhighligted(false);
            return true;
        }
        else if(key.getKeyType()== KeyType.Enter)
        {
            SetColor();
            return !(player1Color!=null && player2Color!=null);
        }else
        {
            return true;
        }
    }
    private void SetColor()
    {
        if(c!=s )
        {
            if(s>=0 && !butonused.contains(colorList.get(s))) {
                   colorList.get(s).setFixBorder(false);
                   colorList.get(s).setBorderFadeIntensity(normalborder);
            }
            s=c;
            colorList.get(c).setBorderFadeIntensity(Selected);
            colorList.get(s).setFixBorder(true);
        }
        Buttonhighligted(true);
        if(player1Color==null)
        {
            player1Color= colorList.get(s).getBackColor();
            butonused.add(colorList.get(s));
        }
        else if(player2Color==null)
        {
            player2Color= colorList.get(s).getBackColor();
            butonused.add(colorList.get(s));
        }
    }
    @Override
    public void draw(TextGraphics graphics) {
        setupbord();
        new Button(backColor,forgColor,new Position(position.getX(), position.getY()),new TerminalSize(29,5),"COLOR GRID",25).draw(graphics);
        for(Button b: colorList)
        {
            b.draw(graphics);
        }
        for (Button b:butonused)
        {
            b.draw(graphics);
        }
        graphics.enableModifiers(SGR.BOLD);
        graphics.setBackgroundColor(BorderColor);
        graphics.drawRectangle(position.toTPos(),size,' ');
    }
    public void start()
    {
        colorList.get(c).setBorderFadeIntensity(highlight);
    }

    public TextColor getPlayer1Color() {
        return player1Color;
    }

    public TextColor getPlayer2Color() {
        return player2Color;
    }
    public void restart()
    {

        int redindex = 0;
        for(Button b : butonused)
        {
            colorList.add(b);
            if (b.getBackColor() == TextColor.ANSI.RED)
            {
                redindex = colorList.indexOf(b);
            }
        }
        butonused.clear();
        player1Color = null;
        if (!Gamemode)
            butonused.add(colorList.get(redindex));
        else
            player2Color = null;

        for (Button b : colorList)
        {
            if(b.getBackColor()!=TextColor.ANSI.RED)
                b.setFixBorder(false);
            b.setBorderFadeIntensity(normalborder);
        }
    }

    public void setPlayer1Color(TextColor player1Color) {
        this.player1Color = player1Color;
    }

    public void setPlayer2Color(TextColor player2Color) {
        this.player2Color = player2Color;
    }

    public void setColorList(List<Button> colorList) {
        this.colorList = colorList;
    }

    public boolean isGamemode() {
        return Gamemode;
    }

    public void setButonused(List<Button> butonused) {
        this.butonused = butonused;
    }

    public List<Button> getColorList() {
        return colorList;
    }

    public int getC() {
        return c;
    }

    public TextColor getBorderColor() {
        return BorderColor;
    }

    public void setC(int c) {
        this.c = c;
    }

    public void setS(int s) {
        this.s = s;
    }

    public List<Button> getButonused() {
        return butonused;
    }
}
