package com.javafxgrid.model.cells;

import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableStringValue;

public class CellFactoryImpl implements CellFactory {

    public static final String EMPTY_TAG = "empty";
    public static final String FLAG_TAG = "flag"; 

    @Override
    public Cell mine() {
        return new CellImpl(Type.MINE, Optional.empty(), "mine", "A mine explodes and thus you die");
    }

    @Override
    public Cell ground(int value) {
        return new CellImpl(Type.GROUND, Optional.of(value), "_".concat(String.valueOf(value)), "You can discover adjax bombs");
    }

    private class CellImpl extends AbstractCell{

        private Type type;
        private Optional<Integer> count;
        private StringProperty tagName;
        private String description;

        private CellImpl(Type t, Optional<Integer> count, String tag, String description) {
            super(new SimpleBooleanProperty(false), new SimpleBooleanProperty(false));
            this.type = t;
            this.count = count;
            this.description = description;
            this.tagName = new SimpleStringProperty();
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

    }
    
}
