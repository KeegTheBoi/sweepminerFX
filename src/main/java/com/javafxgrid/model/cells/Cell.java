package com.javafxgrid.model.cells;

import java.util.Optional;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;

public interface Cell {

    /**
     * Represents the type of the cell, could be ground or mine for now
     * @return the relative type
     */
    Type getType();

    /**
     * It observers whether a cell has been revealed or not
     * @return
     */
    ObservableBooleanValue visibilityObservable();
    
    /**
     * Counts the number of adjax cell (by adjax I mean neighbours lenghten 1), some cells
     * could not have the count, i.e mine
     */
    Optional<Integer> getCount();

    /**
     * It observers wether a cell has been flagged, it jast flags it without revealing it
     * Could be activated by user by clicking the right button or in a console context 
     * calling the relative cell by adding a identifier e.g 'f'
     * @return
     */
    ObservableBooleanValue flaggedObservable();

    /**
     * Reveals the cell, it sets the visibilty to true so its values can be shown
     */
    void reveal();


    /**
     * it unflags when a flag has been set, otherwise it sets it
     */
    void changeFlag();

    /**
     * It allows to get the identifier of the cell, sort of string representation. It could 
     * be useful to set an image, or a detirmined color based on the tag
     * @return proprety computes the tag based on the current state of the game
     * Initially it's set to empty
     */
    StringProperty tagProprety();

    String fixedTag();
}
