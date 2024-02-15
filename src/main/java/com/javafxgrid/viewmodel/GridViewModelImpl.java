package com.javafxgrid.viewmodel;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.javafxgrid.model.Coord;
import com.javafxgrid.model.Level;
import com.javafxgrid.model.Logics;
import com.javafxgrid.model.LogicsImpl;
import com.javafxgrid.viewmodel.appmediators.AbstractBackerViewModel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.util.Pair;

public class GridViewModelImpl extends AbstractBackerViewModel implements GridViewModel{

    private Map<StringProperty, Pair<Coord,BooleanProperty>> griMap;
    private BooleanProperty disablePropCellVisibility = new SimpleBooleanProperty(); 

    private final Logics log;
    private int size;
    private BooleanProperty bindedOverProprety = new SimpleBooleanProperty();

    public GridViewModelImpl(Level lev) {
        this.log = new LogicsImpl(lev.size(), lev.diffuculty());
        this.griMap = fillGrid(lev.size());
        this.size = lev.size();
        
        log.overBinding().addListener((o, ol, ne) -> {
            System.out.println("OVER");
        });
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
    public void disableAndHit(StringProperty buttonID) {
        // this.getMatchingProp(buttonID).getValue().setValue(true);
        var f = Coord.fromString(buttonID.getValue());
        log.hit(f);
        log.hasWon();
        // overBinding().ifPresent(y -> this.getAppManeger().closeWithMessage("Game is Over", "TODO SHOULD DECIDE WETHER YOU WON OR NOT"));
    }

    @Override
    public int gridSize() {
        return this.size;
    }
    
}
