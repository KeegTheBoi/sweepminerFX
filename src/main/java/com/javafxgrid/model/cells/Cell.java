package com.javafxgrid.model.cells;

import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableStringValue;

public interface Cell {

    Type getType();

    ObservableBooleanValue visibilityObservable();

    Optional<Integer> getCount();

    ObservableBooleanValue flaggedObservable();

    void reveal();

    void changeFlag();

    StringProperty tagProprety();
}
