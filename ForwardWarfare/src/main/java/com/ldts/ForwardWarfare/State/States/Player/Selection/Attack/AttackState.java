package com.ldts.ForwardWarfare.State.States.Player.Selection.Attack;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.BaseState;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveEndState;

public class AttackState extends BaseState {
    private Playable attacker;
    private Playable vitim;
    public AttackState(Controller p1, Controller p2, Map map, Playable attacker, Playable vitim) {
        super(p1, p2, map);
        this.attacker = attacker;
        this.vitim = vitim;
    }

    @Override
    public State play(Action action) {
        return new MoveEndState(p1, p2, map, null);
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(p1.getControllerColor());
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');

        int finalHp = vitim.getHp() - attacker.getDamage();
        vitim.setHP(finalHp);
        graphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        graphics.putString(1, 12, "SUCCESS");
        if (finalHp <= 0) {
            graphics.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
            graphics.putString(1, 14, "TARGET DEAD");
            p2.getTroops().remove(vitim);
        } else {
            graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            graphics.putString(1, 14, String.format("%d HP LEFT", finalHp));
        }
        graphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        graphics.putString(1, 17, "ENTER");
    }

    @Override
    public boolean requiresInput() {
        return true;
    }
}
