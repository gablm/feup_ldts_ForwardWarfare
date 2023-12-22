package com.ldts.ForwardWarfare.State.States.Player.Selection.Attack;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.BaseState;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveEndState;
import com.ldts.ForwardWarfare.State.States.QuitState;

import java.util.ArrayList;
import java.util.List;

public class AttackNoSelectionState extends BaseState {
    private List<Playable> selectables = new ArrayList<>();
    private int index = 0;
    private Element element;
    public AttackNoSelectionState(Controller p1, Controller p2, Map map, Element element) {
        super(p1, p2, map);
        this.element = element;
        p1.setSelection1(null);
        if (!(element instanceof Playable playable))
            return;
        for (Element i : p2.getTroops()) {
            Playable elem = (Playable) i;
            if (playable.canAttack(elem) && withinRadius(i.getPosition(), playable.getAttackRadius())) {
                selectables.add(elem);
            }
        }
        if (selectables.isEmpty())
            return;
        p1.setSelection1(new Border(new Position(0, 0)));
        moveTo(selectables.get(0).getPosition());
    }

    private boolean withinRadius(Position point2, int attackRadius) {
        Position point1 = element.getPosition();
        if (point2.getX() > point1.getX() + attackRadius)
            return false;
        if (point2.getX() < point1.getX() - attackRadius)
            return false;
        if (point2.getY() > point1.getY() + attackRadius)
            return false;
        if (point2.getY() < point1.getY() - attackRadius)
            return false;
        return true;
    }

    @Override
    public State play(Action action) {
        if (selectables.isEmpty()) {
            p1.setSelection1(new Border(element.getPosition()));
            return new MoveEndState(p1, p2, map, element);
        }
        switch (action) {
            case UP:
                break;
            case DOWN:
                break;
            case LEFT:
                index = index > 0 ? index - 1 : 0;
                break;
            case RIGHT:
                index = index < selectables.size() - 1 ? index + 1 : selectables.size() - 1;
                break;
            case ENTER:
                return new AttackState(p1, p2, map, (Playable) element, selectables.get(index));
            case ESCAPE:
                p1.setSelection1(new Border(element.getPosition()));
                return new MoveEndState(p1, p2, map, element);
            case QUIT:
                return new QuitState(p1, p2, map, this);
        }
        moveTo(selectables.get(index).getPosition());
        return this;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(p1.getControllerColor());
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');

        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);

        if (!selectables.isEmpty()) {
            graphics.putString(1, 11, "Select troop");
            TextCharacter character = graphics.getCharacter(p1.getSelection1().getPosition().toTPos());
            graphics.setCharacter(1, 13, character);
            graphics.putString(3, 13, String.format("HP %d", selectables.get(index).getHp()));
            graphics.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            graphics.putString(1, 15, "LEFT");
            graphics.putString(6, 15, "RIGHT");
        } else {
            graphics.putString(1, 12, "Nothing to");
            graphics.putString(1, 13, "attack");
        }
        graphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        graphics.putString(1, 17, "ENTER");
    }

    @Override
    public boolean requiresInput() {
        return true;
    }

    private void moveTo(Position pos) {
        if (!map.inside(pos))
            return;
        p1.getSelection1().setPosition(pos);
        TextColor color = map.at(pos).getColor();
        if (color != null)
            p1.getSelection1().setBackgroundColor(color);
    }
}
