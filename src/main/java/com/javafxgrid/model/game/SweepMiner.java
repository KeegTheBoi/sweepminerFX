package com.javafxgrid.model.game;

import com.javafxgrid.model.Coord;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableIntegerValue;

public interface SweepMiner {

    void seedBombs(int diffuculty);

    void fillRemaining();

    void unveil(Coord pos);

    int bombsSize();

    public void recursiveDiscoveryOf(Coord c);

    int hitCount();

    ObservableBooleanValue timerElapsedObservable();

    ObservableIntegerValue getTimerCountObservable();

    void startTimer();

    void stopTimer();

}
