package com.javafxgrid.model;

import com.javafxgrid.model.cells.Cell;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;

public interface GameModel {

    void hit(Coord pos);

    BooleanBinding overBinding();

    IntegerProperty secondsTickerProprety();

    boolean hasWon();

    void flag(Coord pos);

    Cell getResult(Coord c);

    void close();

    
}
