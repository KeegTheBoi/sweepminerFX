package com.javafxgrid.model.game;

import com.javafxgrid.model.Coord;
import javafx.beans.property.BooleanProperty;

public interface SweepMiner {

    void seedBombs(int diffuculty);

    void fillRemaining();

    void unveil(Coord pos);

    int bombsSize();

    public void recursiveDiscoveryOf(Coord c);

    int hitCount();

    BooleanProperty timerElapsed();

    void startTimer();

}
