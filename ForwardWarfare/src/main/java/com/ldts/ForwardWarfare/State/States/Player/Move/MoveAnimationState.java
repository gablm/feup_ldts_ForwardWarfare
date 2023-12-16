package com.ldts.ForwardWarfare.State.States.Player.Move;

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
import com.ldts.ForwardWarfare.State.BaseState;

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
            return new MoveEndState(p1, p2, map, element);
        element.setPosition(moves.get(moves.size() - 1));
        moves.remove(moves.size() - 1);
        try
        {
            Thread.sleep(150);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        return this;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(p1.getControllerColor());
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(1, 15, "Moving...");
    }

    @Override
    public boolean requiresInput() {
        return false;
    }
}
