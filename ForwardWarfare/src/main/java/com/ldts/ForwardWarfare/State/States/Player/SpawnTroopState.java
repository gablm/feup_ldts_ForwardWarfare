package com.ldts.ForwardWarfare.State.States.Player;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.BaseState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.NoSelectionState;

import java.util.Objects;

public class SpawnTroopState extends BaseState {
    private Position facilityPos;
    private Integer price;
    private Element troopType;
    private boolean validSpace = false;
    private boolean bought = true;
    public SpawnTroopState(Controller p1, Controller p2, Map map, Position facility, Integer price, Element troopType) {
        super(p1, p2, map);
        this.facilityPos = facility;
        this.price = price;
        this.troopType = troopType;
    }

    @Override
    public State play(Action action) {
        return new NoSelectionState(p1,p2,map);
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');
        int p = 0;
        Position temp = new Position( facilityPos.getX(), facilityPos.getY());
        while (!validSpace) {
            switch (p)
            {
                case 0:
                    temp = new Position(facilityPos.getX() + 1, facilityPos.getY());
                    break;
                case 1:
                    temp = new Position(facilityPos.getX() + 1, facilityPos.getY() + 1);
                    break;
                case 2:
                    temp = new Position(facilityPos.getX(), facilityPos.getY() + 1);
                    break;
                case 3:
                    temp = new Position(facilityPos.getX() - 1, facilityPos.getY() + 1);
                    break;
                case 4:
                    temp = new Position(facilityPos.getX() - 1, facilityPos.getY());
                    break;
                case 5:
                    temp = new Position(facilityPos.getX() - 1, facilityPos.getY() - 1);
                    break;
                case 6:
                    temp = new Position(facilityPos.getX(), facilityPos.getY() - 1);
                    break;
                case 7:
                    temp = new Position(facilityPos.getX() + 1, facilityPos.getY() - 1);
                    break;
                case 8:
                    validSpace =true;
                    bought =false;
                    break;
            }
            Position finalTemp = temp;
            if (!p1.getTroops().stream().anyMatch(x -> x.getPosition().equals(finalTemp))
                    && !p2.getTroops().stream().anyMatch(x -> x.getPosition().equals(finalTemp))
                    && map.at(temp).noCollision()) {
                Playable troop = (Playable) troopType;
                if((troop.getType().equals("Ground") || troop.getType().equals("Air"))
                        && Objects.equals(map.at(temp).getColor(), new TextColor.RGB(113, 199, 0))) {
                    troopType.setPosition(temp);
                    map.at(facilityPos).getFacility().execute();
                    p1.buy(troopType, price);
                    validSpace = true;
                } else if (troop.getType().equals("Water") && Objects.equals(map.at(temp).getColor(), new TextColor.RGB(0,124,206))) {
                    troopType.setPosition(temp);
                    map.at(facilityPos).getFacility().execute();
                    p1.buy(troopType, price);
                    validSpace = true;
                }
            }
            p++;
        }
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        if (!bought)
        {
            graphics.putString(1, 13, "No Space ");
            graphics.putString(1, 14, "to spawn ");
        }
        else
            graphics.putString(1, 13, "Continue?");
    }

    @Override
    public boolean requiresInput() {
        return false;
    }
}
