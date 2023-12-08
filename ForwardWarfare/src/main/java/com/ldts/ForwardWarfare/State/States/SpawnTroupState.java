package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

import java.util.Objects;

public class SpawnTroupState extends BaseState{
    private Position facilitypos;
    private Integer price;
    private Element Trouptype;
    private boolean validspace=false;
    private boolean Comprado=true;
    public SpawnTroupState(Controller p1, Controller p2, Map map, Position facility,Integer price,Element Trouptype) {
        super(p1, p2, map);
        this.facilitypos=facility;
        this.price=price;
        this.Trouptype=Trouptype;
    }

    @Override
    public State play(Action action) {
        return new NoSelectionState(p1,p2,map);
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');
        int p=0;
        Position temp=new Position( facilitypos.getX(),facilitypos.getY());
        while (!validspace) {
            switch (p)
            {
                case 0:
                    temp = new Position(facilitypos.getX()+1,facilitypos.getY());
                    break;
                case 1:
                    temp = new Position(facilitypos.getX()+1,facilitypos.getY()+1);
                    break;
                case 2:
                    temp = new Position(facilitypos.getX(),facilitypos.getY()+1);
                    break;
                case 3:
                    temp = new Position(facilitypos.getX()-1,facilitypos.getY()+1);
                    break;
                case 4:
                    temp = new Position(facilitypos.getX()-1,facilitypos.getY());
                    break;
                case 5:
                    temp = new Position(facilitypos.getX()-1,facilitypos.getY()-1);
                    break;
                case 6:
                    temp = new Position(facilitypos.getX(),facilitypos.getY()-1);
                    break;
                case 7:
                    temp = new Position(facilitypos.getX()+1,facilitypos.getY()-1);
                    break;
                case 8:
                    validspace=true;
                    Comprado=false;
                    break;
            }
            Position finalTemp = temp;
            if (!p1.getTroops().stream().anyMatch(x -> x.getPosition().equals(finalTemp)) && !p2.getTroops().stream().anyMatch(x -> x.getPosition().equals(finalTemp)) && map.at(temp).noCollision()) {
                if((Trouptype.getClass().getPackageName().equals("com.ldts.ForwardWarfare.Element.Playable.Ground") || Trouptype.getClass().getPackageName().equals("com.ldts.ForwardWarfare.Element.Playable.Air")) && Objects.equals(map.at(temp).getColor(), new TextColor.RGB(113, 199, 0))) {
                    Trouptype.setPosition(temp);
                    p1.buy(Trouptype, price);
                    validspace=true;
                } else if (Trouptype.getClass().getPackageName().equals("com.ldts.ForwardWarfare.Element.Playable.Water")  && Objects.equals(map.at(temp).getColor(), new TextColor.RGB(0,124,206))) {
                    Trouptype.setPosition(temp);
                    p1.buy(Trouptype, price);
                    validspace=true;
                }
            }
            p++;
        }
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        if (!Comprado)
        {
            graphics.putString(1, 13, "No Space ");
            graphics.putString(1, 14, "to spawn ");
        }
        else {
            graphics.putString(1, 13, "Continue?");
        }
    }

    @Override
    public boolean requiresInput() {
        return true;
    }
}
