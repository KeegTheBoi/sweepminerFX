package com.javafxgrid.model;
import com.javafxgrid.model.cells.Cell;
import com.javafxgrid.model.cells.CellsUtils;
import com.javafxgrid.model.game.*;
import com.javafxgrid.model.gameBoard.*;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class GameModelImpl implements GameModel {

    private Coord current;
    private final Board<Coord, Cell> board;
    private final SweepMiner game;
    private static final BooleanProperty overPropety = new SimpleBooleanProperty();
    private final IntegerProperty timerCountProprety;

    public GameModelImpl(int size, int bombNumber) {
        this.board = new BoardImpl<Coord, Cell>(size);
        this.game = new SweepMinerImpl(board);
        this.game.seedBombs(bombNumber);
        this.game.fillRemaining();
        this.game.startTimer();
        this.timerCountProprety = this.game.getCountProprety();
        overPropety.addListener((obs, oldValue, newVal) -> {
            this.game.stopTimer();
        });
    }

    @Override
    public void hit(Coord pos) {
        this.current = pos;
        overPropety.set(CellsUtils.isBomb(board.getCell(pos)));
        
        game.recursiveDiscoveryOf(current);   
    }

    @Override
    public BooleanBinding overBinding() {
        return overPropety.or(game.timerElapsed());
    }

    @Override
    public boolean hasWon() {
        System.out.println(game.hitCount());
        System.out.println(board.size());
        System.out.println(game.bombsSize());
        return board.size() - game.hitCount() == game.bombsSize();
    }

    @Override
    public void flag(Coord pos) {
        board.getCell(pos).changeFlag();
    }

    @Override
    public Cell getResult(Coord c) {
        return board.getCell(c);
    }

    @Override
    public IntegerProperty secondsTickerProprety() {
        return this.timerCountProprety;
    }

    @Override
    public void close() {
        this.game.stopTimer();
    }

}
