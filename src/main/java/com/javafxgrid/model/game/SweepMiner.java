package com.javafxgrid.model.game;

import com.javafxgrid.model.Coord;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public interface SweepMiner {

    void seedBombs(int diffuculty);

    void fillRemaining();

    void unveil(Coord pos);

    int bombsSize();

    public void recursiveDiscoveryOf(Coord c);

    int hitCount();

    BooleanProperty timerElapsed();

    SimpleIntegerProperty getCountProprety();

    void startTimer();

    void stopTimer();

}
