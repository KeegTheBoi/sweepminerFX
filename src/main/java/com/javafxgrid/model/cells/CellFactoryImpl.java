package com.javafxgrid.model.cells;

import java.util.*;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CellFactoryImpl implements CellFactory {

    public static final String EMPTY_TAG = "empty";
    public static final String FLAG_TAG = "flag"; 
    public static final String MINE_TAG = "mine";
    public static final String GROUND_PREFIX = "_";



    @Override
    public Cell mine() {
        return new CellImpl(
            Type.MINE,
            Optional.empty(),
            MINE_TAG, "A mine explodes and thus you die"
         );
    }

    @Override
    public Cell ground(int value) {
        return new CellImpl(
            Type.GROUND, 
            Optional.of(value),
            CellsUtils.groundMapper().apply(value),
            "You can discover adjax bombs"
        );
    }

    private class CellImpl extends AbstractCell{

        private Type type;
        private Optional<Integer> count;
        private StringProperty tagName;
        private String description;
        private String fixedTag;

        private CellImpl(Type t, Optional<Integer> count, String tag, String description) {
            super(new SimpleBooleanProperty(false), new SimpleBooleanProperty(false));
            this.type = t;
            this.count = count;
            this.description = description;
            this.tagName = new SimpleStringProperty();
            this.fixedTag = tag;
            this.tagName.bind(Bindings
                .when(this.visibilityObservable())
                .then(tag)
                .otherwise(Bindings
                    .when(this.flaggedObservable())
                    .then(FLAG_TAG)
                    .otherwise(EMPTY_TAG)
                )
            );
        }
        @Override
        public Type getType() {
            return this.type;
        }

        @Override
        public Optional<Integer> getCount() {
            return this.count;
        }

        public StringProperty tagProprety() {
            return this.tagName;
        }

        @Override
        public String toString() {
            return this.description;
        }
        @Override
        public String fixedTag() {
            return this.fixedTag;
        }

    }

    @Override
    public Cell unmodifableGround(int value) {
        return new UnmodifiableCell(CellsUtils.groundMapper().apply(value)
                , value == 0 ? "Triggers all nearby cells, preciously use this feature" : 
                "Ensures " + value + 
                " adjax bombs are nearby, be aware!"
            );
    }

    @Override
    public Cell unmodifableFlagger() {
        return new UnmodifiableCell(FLAG_TAG, "You can flag targeted cell meaning that you keep a remainder on which cell to hit");
    }

    @Override
    public Cell unmodifableMine() {
        return new UnmodifiableCell(MINE_TAG, "A mine explodes and thus you die");
    }

    @Override
    public Cell unmodifableEmpty() {
        return new UnmodifiableCell(EMPTY_TAG, "An empty cell masks the cell visibility, hence it's hittable. Restoring is not permitted");
    }

        
}
