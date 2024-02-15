package com.javafxgrid.model;

import com.javafxgrid.model.cells.Cell;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableIntegerValue;

public interface GameModel {

    void hit(Coord pos);

    BooleanBinding isOverBinding();

    ObservableIntegerValue tickerObservable();

    ObservableBooleanValue winningObservable();

    void flag(Coord pos);

    Cell getResult(Coord c);

    void close();

    
}
