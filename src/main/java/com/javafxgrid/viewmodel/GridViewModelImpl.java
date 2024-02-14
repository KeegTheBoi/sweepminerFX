package com.javafxgrid.viewmodel;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.javafxgrid.model.Coord;
import com.javafxgrid.model.Level;
import com.javafxgrid.model.Logics;
import com.javafxgrid.model.LogicsImpl;
import com.javafxgrid.viewmodel.appmediators.AbstractBackerViewModel;

import javafx.beans.property.*;
import javafx.util.Pair;

public class GridViewModelImpl extends AbstractBackerViewModel implements GridViewModel{

    private Map<StringProperty, Pair<Coord,BooleanProperty>> griMap;
    private BooleanProperty disablePropCellVisibility = new SimpleBooleanProperty(); 

    private final Logics log;
    private int size;

    public GridViewModelImpl(Level lev) {
        this.size = lev.size();
        this.log = new LogicsImpl(lev.size(), lev.diffuculty());
        this.griMap = fillGrid(lev.size());
    }

    @Override
    public Map<StringProperty, Pair<Coord, BooleanProperty>> getGridMap() {
        return this.griMap;
    }

    @Override
    public Optional<Boolean> isOver() {
        return Optional.of(log.isOver()).filter(t -> t).or(() -> Optional.of(log.hasWon()).filter(t -> t));
    }

    private Map<StringProperty, Pair<Coord, BooleanProperty>> fillGrid(int size) {
        return IntStream.range(0, size).boxed().flatMap(
            i -> IntStream.range(0, size).mapToObj(j -> new Coord(j, i))
            ).collect(
                Collectors.toMap(
                    c -> {
                        StringProperty prop = new SimpleStringProperty();
                        prop.bind(log.getResult(c).tagProprety().concat(":").concat(c.toString()));
                        return prop;
                    },
                    c -> {
                        disablePropCellVisibility = log.getResult(c).visibilityProprety();
                        return new Pair<>(c, disablePropCellVisibility);
                    }
                    
                )
            );
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void disableAndHit(StringProperty buttonID) {
        // this.getMatchingProp(buttonID).getValue().setValue(true);
        var f = Coord.fromString(buttonID.getValue());
        log.hit(f);
        log.hasWon();
        isOver().ifPresent(y -> this.getAppManeger().closeWithMessage("Game is Over", "TODO SHOULD DECIDE WETHER YOU WON OR NOT"));
    }

    @Override
    public BooleanProperty getDisableBooleanProperty(StringProperty buttonID) {
        return this.getMatchingProp(buttonID).getValue();
    }

    private Pair<Coord, BooleanProperty> getMatchingProp(StringProperty str) {
        return this.griMap.entrySet().stream()
            .filter(y -> y.getKey().get().equals(str.getValue()))
            .map(Map.Entry::getValue).findAny()
            .orElse(new Pair<Coord,BooleanProperty>(null, null));
    }
    
}
