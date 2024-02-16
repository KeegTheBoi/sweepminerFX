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

import java.util.Optional;
import java.util.Objects;

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
        winningProperty.set(board.size() - game.hitCount() == game.getBombSize());
    }

    @Override
    public BooleanBinding isOverBinding() {
        return overPropety.or(game.timerElapsedObservable());
    }

    @Override
    public ObservableBooleanValue winningObservable() {
        return winningProperty;
    }

    @Override
    public void flag(Coord pos) {
        board.getCell(pos).changeFlag();
    }

    @Override
    public Cell getResult(Coord c) {
        return Optional.of(c)   
            .map(board::getCell)
            .filter(Objects::nonNull)
            .orElseThrow(
                () -> new IllegalStateException(
                    "Couldn't find any cell in the board, maybe cell value it's too big"
                )
            );
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
