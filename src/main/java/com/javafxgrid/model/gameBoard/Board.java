package com.javafxgrid.model.gameBoard;

import java.util.*;


public interface Board<P, C> {

    int size();

    int bound();

    C getCell(P coord);

    Optional<P> getCoord(C c);

    void setValue(P coord, C c);

    int mapSize();

}
