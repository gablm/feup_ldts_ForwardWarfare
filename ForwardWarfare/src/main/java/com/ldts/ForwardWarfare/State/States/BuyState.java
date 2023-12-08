package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Airport;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Facility.Factory;
import com.ldts.ForwardWarfare.Element.Facility.Port;
import com.ldts.ForwardWarfare.Element.Playable.Air.BomberPlane;
import com.ldts.ForwardWarfare.Element.Playable.Air.FighterPlane;
import com.ldts.ForwardWarfare.Element.Playable.Air.LightHelicopter;
import com.ldts.ForwardWarfare.Element.Playable.Ground.*;
import com.ldts.ForwardWarfare.Element.Playable.Water.FighterSubmarine;
import com.ldts.ForwardWarfare.Element.Playable.Water.LightBoat;
import com.ldts.ForwardWarfare.Element.Playable.Water.MortarBoat;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import java.util.concurrent.TimeUnit;

import java.util.List;
import java.util.Vector;

import static com.ldts.ForwardWarfare.State.Action.DOWN;
import static com.ldts.ForwardWarfare.State.Action.UP;

public class BuyState extends BaseState {
    private boolean nocash=false;
    private Facility facilitySelected;
    private int Facility;
    private Position pos;
    private int Highlighted=0;
    private List<List<Integer>> values=List.of(List.of(2,5,10,7,15,10),List.of(7,20,10),List.of(10,25,15));
    private List<List<Element>> troup=List.of(
            List.of(new LightPerson(new Position(0,0)),new HeavyPerson(new Position(0,0)),new MortarPerson(new Position(0,0)),new LightTank(new Position(0,0)),new HeavyTank(new Position(0,0)),new AntiAirTank(new Position(0,0)))
            ,List.of(new LightBoat(new Position(0,0)),new MortarBoat(new Position(0,0)),new FighterSubmarine(new Position(0,0)))
            ,List.of(new FighterPlane(new Position(0,0)),new BomberPlane(new Position(0,0)),new LightHelicopter(new Position(0,0))));

    private Vector<Integer> indexf= new Vector<>(List.of(1,0,0,0,0,0)) ;
    private Vector<Integer> indexa= new Vector<>(List.of(1,0,0)) ;
    private Vector<Integer> indexp= new Vector<>(List.of(1,0,0)) ;
    private List<TextColor> cores= List.of(TextColor.ANSI.WHITE, TextColor.ANSI.GREEN);

    public BuyState(Controller p1, Controller p2, Map map, Facility facility,Position pos) {
        super(p1, p2, map);
        facilitySelected = facility;
        this.pos=pos;
        if (facilitySelected.getClass() == Factory.class)
            Facility = 0;
        else if (facilitySelected.getClass() == Airport.class)
            Facility = 2;
        else if (facilitySelected.getClass() == Port.class)
            Facility = 1;

    }

    @Override
    public State play(Action action) {
        if (nocash)
        {
            nocash=false;
        }
        else {
            switch (action) {
                case ENTER:
                    if (p1.getCoins() - values.get(Facility).get(Highlighted) >= 0) {
                        return new SpawnTroupState(p1, p2, map, pos, values.get(Facility).get(Highlighted),troup.get(Facility).get(Highlighted));
                    } else
                        nocash = true;
                    break;
                case UP:
                    switch (Facility) {
                        case 0:
                            indexf.set(Highlighted, 0);
                            if (Highlighted - 1 < 0) {
                                Highlighted = 5;
                            } else Highlighted--;
                            indexf.set(Highlighted, 1);
                            break;
                        case 1:
                            indexp.set(Highlighted, 0);
                            if (Highlighted - 1 < 0) {
                                Highlighted = 2;
                            } else Highlighted--;
                            indexp.set(Highlighted, 1);
                            break;
                        case 2:
                            indexa.set(Highlighted, 0);
                            if (Highlighted - 1 < 0) {
                                Highlighted = 2;
                            } else Highlighted--;
                            indexa.set(Highlighted, 1);
                            break;
                    }
                    break;
                case DOWN:
                    switch (Facility) {
                        case 0:
                            indexf.set(Highlighted, 0);
                            if (Highlighted + 1 > 5) {
                                Highlighted = 0;
                            } else Highlighted++;
                            indexf.set(Highlighted, 1);
                            break;
                        case 1:
                            indexp.set(Highlighted, 0);
                            if (Highlighted + 1 > 2) {
                                Highlighted = 0;
                            } else Highlighted++;
                            indexp.set(Highlighted, 1);
                            break;
                        case 2:
                            indexa.set(Highlighted, 0);
                            if (Highlighted + 1 > 2) {
                                Highlighted = 0;
                            } else Highlighted++;
                            indexa.set(Highlighted, 1);
                            break;
                    }
                    break;
                case LEFT:
                    switch (Facility) {
                        case 0:
                            indexf.set(Highlighted, 0);
                            if (Highlighted - 3 < 0) {
                                Highlighted = 5 - (2 - Highlighted);
                            } else Highlighted -= 3;
                            indexf.set(Highlighted, 1);
                            break;
                        case 1:
                            indexp.set(Highlighted, 0);
                            if (Highlighted == 2) {
                                Highlighted = 0;
                            } else Highlighted = 2;
                            indexp.set(Highlighted, 1);
                            break;
                        case 2:
                            indexa.set(Highlighted, 0);
                            if (Highlighted == 2) {
                                Highlighted = 0;
                            } else Highlighted = 2;
                            indexa.set(Highlighted, 1);
                            break;
                    }
                    break;
                case RIGHT:
                    switch (Facility) {
                        case 0:
                            indexf.set(Highlighted, 0);
                            if (Highlighted + 3 > 5) {
                                Highlighted = Highlighted + 3 - 6;
                            } else Highlighted += 3;
                            indexf.set(Highlighted, 1);
                            break;
                        case 1:
                            indexp.set(Highlighted, 0);
                            if (Highlighted == 2) {
                                Highlighted = 0;
                            } else Highlighted = 2;
                            indexp.set(Highlighted, 1);
                            break;
                        case 2:
                            indexa.set(Highlighted, 0);
                            if (Highlighted == 2) {
                                Highlighted = 0;
                            } else Highlighted = 2;
                            indexa.set(Highlighted, 1);
                            break;
                    }
                    break;
                case ESCAPE:
                    return new NoSelectionState(p1, p2, map);
            }
        }
        return this;
    }

    @Override
    public void draw(TextGraphics graphics) {
        if (facilitySelected.getClass() == Factory.class)
            drawFactoryShop(graphics);
        else if (facilitySelected.getClass() == Airport.class)
            drawAirportShop(graphics);
        else if (facilitySelected.getClass() == Port.class)
            drawPortShop(graphics);
        else
            System.out.println("Invalid facility");
        if (nocash)
        {
            try {
                draw_nocahs(graphics);
            }catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean requiresInput() {
        return true;
    }

    private void drawFactoryShop(TextGraphics graphics)
    {
        graphics.setBackgroundColor(new TextColor.RGB(80,80,80));
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');
        String coins = new StringBuilder().append(p1.getCoins()).append("!").toString();
        graphics.setForegroundColor(cores.get(indexf.get(0)));
        graphics.putString(2, 13, "(  2");
        graphics.setForegroundColor(cores.get(indexf.get(1)));
        graphics.putString(2, 14, "_  5");
        graphics.setForegroundColor(cores.get(indexf.get(2)));
        graphics.putString(2, 15, "@ 10");
        graphics.setForegroundColor(cores.get(indexf.get(3)));
        graphics.putString(9, 13, "#  7");
        graphics.setForegroundColor(cores.get(indexf.get(4)));
        graphics.putString(9, 14, "$ 15");
        graphics.setForegroundColor(cores.get(indexf.get(5)));
        graphics.putString(9, 15, "+ 10");
        graphics.enableModifiers(SGR.BOLD);
        graphics.setForegroundColor(new TextColor.RGB(255,255,255));
        graphics.putString(1, 11, " Factory Shop");
        graphics.setForegroundColor(new TextColor.RGB(255,223,0));
        graphics.putString(6, 17, coins);
        graphics.putString(6, 13, "!");
        graphics.putString(6, 14, "!");
        graphics.putString(6, 15, "!");
        graphics.putString(13, 13, "!");
        graphics.putString(13, 14, "!");
        graphics.putString(13, 15, "!");
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
    }
    
    private void drawPortShop(TextGraphics graphics)
    {
        graphics.setForegroundColor(new TextColor.RGB(255,255,255));
        graphics.setBackgroundColor(new  TextColor.RGB(0,124,206));
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');
        String coins = new StringBuilder().append(p1.getCoins()).append("!").toString();
        graphics.setForegroundColor(cores.get(indexp.get(0)));
        graphics.putString(2, 13, "<  7");
        graphics.setForegroundColor(cores.get(indexp.get(1)));
        graphics.putString(2, 14, "' 20");
        graphics.setForegroundColor(cores.get(indexp.get(2)));
        graphics.putString(9, 13, "= 10");
        graphics.enableModifiers(SGR.BOLD);
        graphics.setForegroundColor(new TextColor.RGB(255,255,255));
        graphics.putString(3, 11, "Port Shop");
        graphics.setForegroundColor(new TextColor.RGB(255,223,0));
        graphics.putString(6, 17, coins);
        graphics.putString(6, 13, "!");
        graphics.putString(6, 14, "!");
        graphics.putString(13, 13, "!");
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
    }

    private void drawAirportShop(TextGraphics graphics)
    {
        graphics.setForegroundColor(new TextColor.RGB(255,255,255));
        graphics.setBackgroundColor(new TextColor.RGB(153,76,0));
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');
        String coins = new StringBuilder().append(p1.getCoins()).append("!").toString();
        graphics.setForegroundColor(cores.get(indexa.get(0)));
        graphics.putString(2, 13, "% 10");
        graphics.setForegroundColor(cores.get(indexa.get(1)));
        graphics.putString(2, 14, "& 25");
        graphics.setForegroundColor(cores.get(indexa.get(2)));
        graphics.putString(9, 13, "[ 15");
        graphics.enableModifiers(SGR.BOLD);
        graphics.setForegroundColor(new TextColor.RGB(255,255,255));
        graphics.putString(3, 11, "Port Shop");
        graphics.setForegroundColor(new TextColor.RGB(255,223,0));
        graphics.putString(6, 17, coins);
        graphics.putString(6, 13, "!");
        graphics.putString(6, 14, "!");
        graphics.putString(13, 13, "!");
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
    }
    private void draw_nocahs(TextGraphics graphics) throws InterruptedException {
        graphics.setForegroundColor(new TextColor.RGB(255,223,0));
        switch (Facility) {
            case 0:
                graphics.setBackgroundColor(new TextColor.RGB(80,80,80));
                break;
            case 1:
                graphics.setBackgroundColor(new TextColor.RGB(0,124,206));
                break;
            case 2:
                graphics.setBackgroundColor(new TextColor.RGB(153,76,0));
                break;
        }
        graphics.putString(0, 17, "Not Enough Coins");
    }
}
