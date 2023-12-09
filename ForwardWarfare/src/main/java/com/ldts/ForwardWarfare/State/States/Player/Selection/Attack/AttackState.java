package com.ldts.ForwardWarfare.State.States.Player.Selection.Attack;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.BaseState;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveEndState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.NoSelectionState;

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

    }

    @Override
    public boolean requiresInput() {
        return false;
    }
}
