package com.javafxgrid.model;
import com.javafxgrid.model.cells.Cell;
import com.javafxgrid.model.cells.CellsUtils;
import com.javafxgrid.model.game.*;
import com.javafxgrid.model.gameBoard.*;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableIntegerValue;


public class GameModelImpl implements GameModel {

    private Coord current;
    private final Board<Coord, Cell> board;
    private final SweepMiner game;
    private static final BooleanProperty overPropety = new SimpleBooleanProperty();
    private final ObservableIntegerValue timerCountObserver;
    private final BooleanProperty winningProperty = new SimpleBooleanProperty();

    public GameModelImpl(int size, int bombNumber) {
        this.board = new BoardImpl<Coord, Cell>(size);
        this.game = new SweepMinerImpl(board);
        this.game.seedBombs(bombNumber);
        this.game.fillRemaining();
        this.game.startTimer();
        this.timerCountObserver = this.game.getTimerCountObservable();
        overPropety.addListener((obs, oldValue, newVal) -> {
            this.game.stopTimer();
        });
    }

    @Override
    public void hit(Coord pos) {
        this.current = pos;
        overPropety.set(CellsUtils.isBomb(board.getCell(pos)));
        game.recursiveDiscoveryOf(current);   
        winningProperty.set(board.size() - game.hitCount() == game.bombsSize());
    }

    @Override
    public BooleanBinding isOverBinding() {
        return overPropety.or(game.timerElapsedObservable());
    }

    @Override
    public ObservableBooleanValue winningObservable() {
        System.out.println(game.hitCount());
        System.out.println(board.size());
        System.out.println(game.bombsSize());
        return winningProperty;
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
    public ObservableIntegerValue tickerObservable() {
        return this.timerCountObserver;
    }

    @Override
    public void close() {
        this.game.stopTimer();
    }

}
