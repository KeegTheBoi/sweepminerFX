package com.javafxgrid.viewmodel;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.javafxgrid.model.Coord;
import com.javafxgrid.model.Level;
import com.javafxgrid.model.GameModel;
import com.javafxgrid.model.GameModelImpl;
import com.javafxgrid.viewmodel.appmediators.AbstractBackerViewModel;

import javafx.beans.property.*;
import javafx.util.Pair;

public class GridViewModelImpl extends AbstractBackerViewModel implements GridViewModel{

    private Map<StringProperty, Pair<Coord,BooleanProperty>> griMap;
    private BooleanProperty disablePropCellVisibility;
    private IntegerProperty thickTime; 
    private BooleanProperty prop = new SimpleBooleanProperty();

    private final GameModel gameLogic;
    private int size;

    public GridViewModelImpl(Level lev) {
        this.gameLogic = new GameModelImpl(lev.size(), lev.diffuculty());
        this.griMap = fillGrid(lev.size());
        this.size = lev.size();
        
        thickTime = this.gameLogic.secondsTickerProprety();
        prop.bind(gameLogic.overBinding());
        prop.addListener((o, ol, ne) -> 
            this.getAppManeger().closeWithMessage("GAME IS OVER", "EITHER YOU WON OR LOSE")
        );
    }

    @Override
    public Map<StringProperty, Pair<Coord, BooleanProperty>> getGridMap() {
        return this.griMap;
    }

    private Map<StringProperty, Pair<Coord, BooleanProperty>> fillGrid(int size) {
        return IntStream.range(0, size).boxed().flatMap(
            i -> IntStream.range(0, size).mapToObj(j -> new Coord(j, i))
            ).collect(
                Collectors.toMap(
                    c -> {
                        StringProperty idProprety = new SimpleStringProperty();
                        idProprety.bind(gameLogic.getResult(c).tagProprety().concat(":").concat(c.toString()));
                        return idProprety;
                    },
                    c -> {
                        disablePropCellVisibility = gameLogic.getResult(c).visibilityProprety();
                        return new Pair<>(c, disablePropCellVisibility);
                    }
                    
                )
            );
    }

    @Override
    public void disableAndHit(StringProperty buttonID) {
        gameLogic.hit(Coord.fromString(buttonID.getValue()));
        gameLogic.hasWon();
        // overBinding().ifPresent(y -> this.getAppManeger().closeWithMessage("Game is Over", "TODO SHOULD DECIDE WETHER YOU WON OR NOT"));
    }

    @Override
    public int gridSize() {
        return this.size;
    }

    @Override
    public IntegerProperty getThickProperty() {
        return this.thickTime;
    }

    @Override
    public void stopAllThreads() {
        this.gameLogic.close();
    }

    @Override
    public void handleLeftClick(StringProperty buttonID) {
        this.gameLogic.flag(Coord.fromString(buttonID.getValue()));
    }
    
}
