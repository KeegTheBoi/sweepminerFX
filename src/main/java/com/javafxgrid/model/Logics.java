package com.javafxgrid.model;

import com.javafxgrid.model.cells.Cell;

import javafx.beans.binding.BooleanBinding;

public interface Logics {

    void hit(Coord pos);

    BooleanBinding overBinding();

    boolean hasWon();

    void flag(Coord pos);

    Cell getResult(Coord c);

    
}
