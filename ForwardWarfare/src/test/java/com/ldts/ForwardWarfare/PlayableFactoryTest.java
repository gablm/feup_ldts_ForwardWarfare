package com.ldts.ForwardWarfare;

import com.ldts.ForwardWarfare.Element.Playable.Air.BomberPlane;
import com.ldts.ForwardWarfare.Element.Playable.Air.FighterPlane;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Playable.PlayableFactory;
import com.ldts.ForwardWarfare.Element.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

public class PlayableFactoryTest {

    @Test
    public void CreateBomberPlane() {
        Playable playable = PlayableFactory.createBomberPlane(10, 20);

        Assertions.assertEquals(BomberPlane.class, playable.getClass());
        Assertions.assertEquals(10, playable.getPosition().getX());
        Assertions.assertEquals(20, playable.getPosition().getY());
    }

    @Test
    public void CreateFighterPlane() {
        Playable playable = PlayableFactory.createFighterPlane(15, 20);

        Assertions.assertEquals(FighterPlane.class, playable.getClass());
        Assertions.assertEquals(15, playable.getPosition().getX());
        Assertions.assertEquals(20, playable.getPosition().getY());
    }
}
