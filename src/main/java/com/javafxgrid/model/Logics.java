package com.javafxgrid.model;

import com.javafxgrid.model.cells.Cell;

public interface Logics {

    void hit(Coord pos);

    boolean isOver();

    boolean hasWon();

    void flag(Coord pos);

    Cell getResult(Coord c);

    
}
