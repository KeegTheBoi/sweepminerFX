package com.javafxgrid.model.cells;

import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public interface Cell {

    Type getType();

    BooleanProperty visibilityProprety();

    Optional<Integer> getCount();

    BooleanProperty isFlagged();

    void reveal();

    void changeFlag();

    StringProperty tagProprety();
}
