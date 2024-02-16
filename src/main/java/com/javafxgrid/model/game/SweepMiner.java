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
     * gets the bombsSize, useful for displaying the possible number of bomb in a development 
     * enviroment
     * @return
     */
    int getBombSize();

    /**
     * Recursevly unveils ground cell valued 0, it should stop when all the left adjax cells have values
     * different to 0
     * @param pos
     */
    void recursiveDiscoveryOf(Coord pos);

    /**
     * counts how many hit player has done, useful to detirmin victory
     * @return
     */
    int hitCount();

    /**
     * Check whether the timer has finished
     * @return
     */
    ObservableBooleanValue timerElapsedObservable();

    /**
     * Useful for diplaying current seconds, not sure if a observer is needed
     * @return
     */
    ObservableIntegerValue getTimerCountObservable();

    /**
     * start the timer as soon as the grid has been filled
     * TODO make it syncronus to the grid filling
     */
    void startTimer();

    /**
     * stops the timer in case an interrupt as occured
     */
    void stopTimer();

}
