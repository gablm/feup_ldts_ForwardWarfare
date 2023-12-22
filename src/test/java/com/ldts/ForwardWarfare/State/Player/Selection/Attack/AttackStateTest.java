package com.ldts.ForwardWarfare.State.Player.Selection.Attack;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyPerson;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveEndState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Attack.AttackState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class AttackStateTest {

    private Controller p1;
    private Controller p2;
    private Map map;
    private TextGraphics graphics;
    @BeforeEach
    public void ResetMocks() {
        map = Mockito.mock(Map.class);
        p1 = Mockito.mock(Controller.class);
        List<Element> p1T = new ArrayList<>();
        Mockito.when(p1.getTroops()).thenReturn(p1T);
        p2 = Mockito.mock(Controller.class);
        List<Element> p2T = new ArrayList<>();
        Mockito.when(p2.getTroops()).thenReturn(p2T);
        graphics = Mockito.mock(TextGraphics.class);
    }

    @Test
    public void BaseTest() {
        Playable vitim = new HeavyTank(null);
        Playable attacker = new HeavyPerson(null);
        State state = new AttackState(p1, p2, map, attacker, vitim);

        state.draw(graphics);

        Assertions.assertTrue(state.requiresInput());
        State result = state.play(null);
        Assertions.assertEquals(MoveEndState.class, result.getClass());
    }

    @Test
    public void NoDeathTest() {
        Playable vitim = new HeavyTank(null);
        Playable attacker = new HeavyPerson(null);
        State state = new AttackState(p1, p2, map, attacker, vitim);

        state.draw(graphics);

        Assertions.assertEquals(100, vitim.getHp());
        Mockito.verify(graphics).putString(1, 14, "100 HP LEFT");
    }

    @Test
    public void DeathTest() {
        Playable vitim = new HeavyTank(null);
        vitim.setHP(0);
        p2.getTroops().add(vitim);
        Playable attacker = new HeavyPerson(null);
        State state = new AttackState(p1, p2, map, attacker, vitim);

        state.draw(graphics);

        Assertions.assertTrue(vitim.getHp() < 0);
        Mockito.verify(graphics).putString(1, 14, "TARGET DEAD");
    }

    @Test
    public void Death0Test() {
        Playable vitim = new HeavyTank(null);
        vitim.setHP(75);
        p2.getTroops().add(vitim);
        Playable attacker = new HeavyPerson(null);
        State state = new AttackState(p1, p2, map, attacker, vitim);

        state.draw(graphics);

        Assertions.assertTrue(vitim.getHp() <= 0);
        Mockito.verify(graphics).putString(1, 14, "TARGET DEAD");
    }
}
