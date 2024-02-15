package com.javafxgrid.viewmodel;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.javafxgrid.model.Coord;
import com.javafxgrid.model.Level;
import com.javafxgrid.model.cells.Cell;
import com.javafxgrid.model.GameModel;
import com.javafxgrid.model.GameModelImpl;
import com.javafxgrid.viewmodel.appmediators.AbstractBackerViewModel;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.*;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableStringValue;
import javafx.util.Pair;

public class GridViewModelImpl extends AbstractBackerViewModel implements GridViewModel{

    private final TreeMap<Long, TileObservers> griMap;
    private final ObservableIntegerValue thickTime; 
    private final BooleanProperty overBinderProp = new SimpleBooleanProperty();

    private final GameModel gameLogic;
    private final int size;

    public GridViewModelImpl(Level lev) {
        this.gameLogic = new GameModelImpl(4, 0);
        this.griMap = fillGrid(lev.size());
        this.size = lev.size();
        
        thickTime = this.gameLogic.tickerObservable();
        this.overBinderProp.bind(gameLogic.isOverBinding());
        ObservableBooleanValue winObserver = gameLogic.winningObservable();
        winObserver.addListener((obs, old, newV) -> {
            this.getAppManeger().closeWithMessage("FANTASTIC, YOU WON", "JEZZ, YOU MADE IT ON THIS SILLY MODE MAN");
        });
        overBinderProp.addListener((obs, old, newV) -> 
            this.getAppManeger().closeWithMessage("GAME IS OVER", "NOT BAD, LUCK IS YOUR WORST ENEMY I BET")
        );
    }

    @Override
    public Map<Long, TileObservers> getGridMap() {
        return this.griMap;
    }

    private TreeMap<Long, TileObservers> fillGrid(int size) {
        return new TreeMap<>(IntStream.range(0, size).boxed().flatMap(
                i -> IntStream.range(0, size)
                    .mapToObj(j -> new Coord(j, i))
            )
            .map(c -> new Pair<>(c, gameLogic.getResult(c)))
            .collect(
                Collectors.toMap(c -> 
                    c.getKey().hashIncremental(),
                    this::maptoEmbbeddedTileId
                )
            ));
    }

    @Override
    public void disableAndHit(ObservableStringValue buttonID) {
        gameLogic.hit(Coord.fromString(buttonID.getValue()));
        // overBinding().ifPresent(y -> this.getAppManeger().closeWithMessage("Game is Over", "TODO SHOULD DECIDE WETHER YOU WON OR NOT"));
    }

    @Override
    public int gridSize() {
        return this.size;
    }

    @Override
    public ObservableIntegerValue getThickObserver() {
        return this.thickTime;
    }

    @Override
    public void stopAllThreads() {
        this.gameLogic.close();
    }

    @Override
    public void handleLeftClick(ObservableStringValue buttonID) {
        this.gameLogic.flag(Coord.fromString(buttonID.getValue()));
    }

    private StringExpression buildStringExpression(StringProperty base, String delimiter, String uniqueID) {
        return base.concat(delimiter).concat(uniqueID);
    }

    public String retriveTag(ObservableStringValue embeddedString) {
        return embeddedString.getValue().split(ID_SEPARATOR)[0];
    }

    private TileObservers maptoEmbbeddedTileId(Pair<Coord, Cell> pair) {
        ObservableBooleanValue disableObsCellVisibility = pair.getValue().visibilityObservable();
        ObservableStringValue idObserver = this.buildStringExpression(pair.getValue().tagProprety(), ID_SEPARATOR, pair.getKey().toString());                        
        return new TileObservers(idObserver, disableObsCellVisibility);
    }
    
}
