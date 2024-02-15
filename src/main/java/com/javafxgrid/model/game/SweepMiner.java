package com.javafxgrid.model.game;

import com.javafxgrid.model.Coord;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableIntegerValue;

/**
 * Advanced logic of the game, it has a timing countdown and difficulty based bombing map
 * It's the core logic of the game
 */
public interface SweepMiner {

    /**
     * Places bom,bs in a square grid based on the difficulty, In a basic scenario it should
     * be done randomly, better implement a related factory
     * @param difficulty could be optional
     */
    void seedBombs(int difficulty);

    /**
     * fills the remaing cell with ground mines, already calculating their value!,
     * Remeber, it should be called only after seeding bombs
     * TODO implements a condition to verify that bombs have been placed
     */
    void fillRemaining();

    /**
     * Unveils the cell at the related coordinate, it basically makes the cell visible
     * @param pos relative position on the grid
     */
    void unveil(Coord pos);

    /**
     * gets the bombSize
     * @return
     */
    int getBombSize();

    public void recursiveDiscoveryOf(Coord c);

    int hitCount();

    ObservableBooleanValue timerElapsedObservable();

    ObservableIntegerValue getTimerCountObservable();

    void startTimer();

    void stopTimer();

}
