package com.javafxgrid.model.cells;

public interface CellFactory extends UnmodifiableCellFactory{

    /**
     * Specific mine cell with all cell propreties
     * @return
     */
    Cell mine();

    /**
     * Ground cell means it surely has a value, and displays adjax mines
     * @param value
     * @return
     */
    Cell ground(int value);
}
