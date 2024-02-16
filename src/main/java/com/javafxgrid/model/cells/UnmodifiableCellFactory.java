package com.javafxgrid.model.cells;

public interface UnmodifiableCellFactory {
    
    Cell unmodifableGround(int value);

    Cell unmodifableFlagger();

    Cell unmodifableMine();

    Cell unmodifableEmpty();
}
