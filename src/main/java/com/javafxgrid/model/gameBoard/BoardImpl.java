package com.javafxgrid.model.gameBoard;

import java.util.*;

public class BoardImpl<P, C> implements Board<P, C> {

    private final int size;
    private final Map<P, C> boardMap;

    public BoardImpl(int size) {
        this.size = size;
        boardMap = new HashMap<>();
    }

    @Override
    public int size() {
        return size * size;
    }

    @Override
    public Optional<P> getCoord(C c) {
        return boardMap.entrySet().stream()
            .filter(e -> e.getValue().equals(c))
            .map(Map.Entry::getKey)
            .findFirst();
    }

    @Override
    public C getCell(P c) {
        return boardMap.get(c); 
    }

    @Override
    public void setValue(P coord, C cell) {
        this.boardMap.put(coord, cell);
    }

    @Override
    public int bound() {
        return size;
    }

    @Override
    public int mapSize() {
        return this.boardMap.size();
    }

}
