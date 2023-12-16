package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Facility.Base;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.BaseState;
import com.ldts.ForwardWarfare.State.State;

public class StartRoundState extends BaseState {
    public StartRoundState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);

        if(((Tile) p1.getBase()).getFacility().getUsed())
            ((Tile) p1.getBase()).getFacility().getUsed();
        if(((Tile) p2.getBase()).getFacility().getUsed())
            ((Tile) p2.getBase()).getFacility().getUsed();
        if(!((Base)((Tile)p2.getBase()).getFacility()).getAtackedlastturn()) {
            ((Base) ((Tile) p2.getBase()).getFacility()).setLives(2);
            ((Base) ((Tile) p2.getBase()).getFacility()).setAtackedlastturn(false);
        }
        else if(((Base)((Tile)p2.getBase()).getFacility()).getAtackedlastturn())
        {
            ((Base) ((Tile) p2.getBase()).getFacility()).setAtackedlastturn(false);
        }
    }

    @Override
    public State play(Action action) {
        if (p1.canPlay())
            return p1.getInitialState(p2, map);
        if (p2.canPlay())
            return p2.getInitialState(p1, map);
        p1.resetRound();
        p2.resetRound();
        return new StartRoundState(p2, p1, map);
    }

    @Override
    public void draw(TextGraphics graphics) {

    }

    @Override
    public boolean requiresInput() {
        return false;
    }
}
