package com.ldts.ForwardWarfare.UI;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.UI.Component.Button;
import com.ldts.ForwardWarfare.UI.Component.ColorGrid;
import com.ldts.ForwardWarfare.UI.Component.Component;
import com.ldts.ForwardWarfare.UI.Component.MapBox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class StartGameMenu extends UI {
    private boolean selectingColor =false;
    private boolean selectingMap =false;
    private UiStates startgame;
    private int highlight=30;
    private int normalborder=10;
    private int Selected = 40;
    private int bc=0;
    private int ms=1;
    private int selected=-1;
    private boolean gamemode;
    private Map selectedMap;
    private ColorGrid grid;
    private List<Component> ButtonsList = new ArrayList<>();

    public StartGameMenu(boolean gamemode) {
        super(new TerminalSize(74,36),15);
        this.gamemode = gamemode;
        grid=new ColorGrid(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(44,1),25, this.gamemode);
    }

    @Override
    public UiStates build() throws IOException, MapParseException, URISyntaxException {
        screen = UITerminal.createScreen();
        addcomp();
        return run();
    }

    @Override
    public void draw() throws IOException {
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(new TextColor.RGB(117,200,4));
        graphics.enableModifiers(SGR.BOLD);
        graphics.fillRectangle(new TerminalPosition(0,0),new TerminalSize(74,36),' ');
        for (Component component : listComponents)
        {
            component.draw(graphics);
        }
        for (Component butao:ButtonsList)
        {
            butao.draw(graphics);
        }
        Component Color1=new Button(grid.getPlayer1Color()==null ? new TextColor.RGB(247,193,64):grid.getPlayer1Color(),new TextColor.RGB(255,255,255),new Position(2,9),new TerminalSize(13,3),"P1 Color",0);
        Component Color2=new Button(grid.getPlayer2Color()==null ? new TextColor.RGB(247,193,64):grid.getPlayer2Color(),new TextColor.RGB(255,255,255),new Position(29,9),new TerminalSize(13,3), gamemode ? "P2 Color" : "AI Color",0);
        Color1.draw(graphics);
        Color2.draw(graphics);

        screen.refresh();
    }

    @Override
    public void addcomp() throws FileNotFoundException, MapParseException, URISyntaxException {
        listComponents.add(grid);
        ButtonsList.add(new Button(TextColor.ANSI.BLUE_BRIGHT,new TextColor.RGB(255,255,255),new Position(1,1),new TerminalSize(42,5),"START",highlight));
        ButtonsList.add(new Button(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(1,8),new TerminalSize(42,5),"COLOR SELECT ",normalborder));
        ButtonsList.add(new Button(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(1,15),new TerminalSize(42,5),"MAP SELECT",normalborder));
        listComponents.add(new MapBox(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(1,23),new TerminalSize(17,12),normalborder,"1.fw"));
        listComponents.add(new MapBox(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(19,23),new TerminalSize(17,12),normalborder,"2.fw"));
        listComponents.add(new MapBox(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(37,23),new TerminalSize(17,12),normalborder, "3.fw"));
        listComponents.add(new MapBox(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(55,23),new TerminalSize(17,12),normalborder, "4.fw"));
    }

    @Override
    public UiStates run() throws IOException {
        while (!endscreen)
        {
            draw();
            KeyStroke key = screen.readInput();
            processKey(key);
        }
        screen.close();
        return startgame;
    }
    private void processButton()
    {
        switch (bc)
        {
            case 0:
                if(grid.getPlayer1Color()!=null && grid.getPlayer2Color()!=null && selectedMap !=null)
                    endscreen=true;
                startgame=UiStates.BattleUI;
                break;
            case 1:
                selectingColor = true;
                grid.restart();
                grid.start();
                break;
            case 2:
                selectingMap = true;
                if(ms==1) {
                    listComponents.get(1).setBorderFadeIntensity(highlight);
                }
                break;
        }
    }
    private void Buttonhighlighted(boolean next)
    {
        if(next) {
            ButtonsList.get(bc).setBorderFadeIntensity(normalborder);
            if (bc+1 > ButtonsList.size() - 1) {
                bc = 0;
            } else
                bc++;
            ButtonsList.get(bc).setBorderFadeIntensity(highlight);
        }
        else
        {
            ButtonsList.get(bc).setBorderFadeIntensity(normalborder);
            if (bc-1 < 0) {
                bc = ButtonsList.size()-1;
            } else
                bc--;
            ButtonsList.get(bc).setBorderFadeIntensity(highlight);
        }
    }
    private void SetMap()
    {
        if(ms!=selected ) {
            if (selected > 0) {
                listComponents.get(selected).setFixBorder(false);
                listComponents.get(selected).setBorderFadeIntensity(normalborder);
            }
            selected = ms;
            listComponents.get(ms).setBorderFadeIntensity(Selected);
            listComponents.get(selected).setFixBorder(true);
            MapBox mapBox = (MapBox) listComponents.get(ms);
            selectedMap = mapBox.getMap();
        }
    }
    private void mapselected(boolean next)
    {
        if(next) {
            listComponents.get(ms).setBorderFadeIntensity(normalborder);
            if (ms+1 > listComponents.size() - 1) {
                ms = 1;
            } else
                ms++;
            listComponents.get(ms).setBorderFadeIntensity(highlight);
        }
        else
        {
            listComponents.get(ms).setBorderFadeIntensity(normalborder);
            if (ms-1 < 1) {
                ms = listComponents.size()-1;
            } else
                ms--;
            listComponents.get(ms).setBorderFadeIntensity(highlight);
        }
    }
    @Override
    public void processKey(KeyStroke key) {
        if (selectingColor) {
            selectingColor =grid.processKey(key);
        }else if(selectingMap) {
            if (key.getKeyType() == KeyType.ArrowUp)
            {
                mapselected(false);
            } else if (key.getKeyType() == KeyType.ArrowRight)
            {
                mapselected(true);
            } else if (key.getKeyType() == KeyType.ArrowLeft) {
                mapselected(false);
            }
            else if (key.getKeyType() == KeyType.ArrowDown) {
                mapselected(true);
            }else if(key.getKeyType()== KeyType.Enter)
            {
                SetMap();
                selectingMap =false;
            }
        }
        else{
            if (key.getKeyType() == KeyType.ArrowUp)
            {
                Buttonhighlighted(false);
            } else if (key.getKeyType() == KeyType.ArrowRight)
            {
                Buttonhighlighted(true);
            } else if (key.getKeyType() == KeyType.ArrowLeft) {
                Buttonhighlighted(false);
            }
            else if (key.getKeyType() == KeyType.ArrowDown) {
                Buttonhighlighted(true);
            }else if(key.getKeyType()== KeyType.Enter)
            {
                processButton();
            } else if (key.getKeyType() == KeyType.Escape) {
                endscreen=true;
                startgame=UiStates.MainMenu;
            }
        }
    }
    public UiStates getStartgame() {
        return startgame;
    }

    public List<Component> getButtonsList() {
        return ButtonsList;
    }

    public void setSelectingColor(boolean selectingColor) {
        this.selectingColor = selectingColor;
    }

    public void setSelectingMap(boolean selectingMap) {
        this.selectingMap = selectingMap;
    }

    public void setGrid(ColorGrid grid) {
        this.grid = grid;
    }

    public boolean getSelectingColor() {
        return selectingColor;
    }

    public ColorGrid getGrid() {
        return grid;
    }

    public boolean getSelectingMap() {
        return selectingMap;
    }

    public void setBc(int bc) {
        this.bc = bc;
    }

    public void setMs(int ms) {
        this.ms = ms;
    }

    public Map getSelectedMap() {
        return selectedMap;
    }

    public TextColor selectColor1() {
        return grid.getPlayer1Color();
    }

    public TextColor selectColor2() {
        return grid.getPlayer2Color();
    }

    public void setSelectedMap(Map selectedMap) {
        this.selectedMap = selectedMap;
    }

    public int getBc() {
        return bc;
    }

    public int getMs() {
        return ms;
    }
}
