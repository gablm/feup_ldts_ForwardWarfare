package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

import java.util.List;

public class MoveAnimationState extends BaseState {
    private List<Position> moves;
    private Element element;
    public MoveAnimationState(Controller p1, Controller p2, Map map, List<Position> moves, Element element) {
        super(p1, p2, map);
        this.moves = moves;
        this.element = element;
    }

    @Override
    public State play(Action action) {
        if (moves.isEmpty())
            return new MoveEndState(p1, p2, map);
        element.setPosition(moves.get(moves.size() - 1));
        moves.remove(moves.size() - 1);
        try
        {
            Thread.sleep(200);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        return this;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(1, 11, "We movin!!!");
    }

    @Override
    public boolean requiresInput() {
        return false;
    }
}
