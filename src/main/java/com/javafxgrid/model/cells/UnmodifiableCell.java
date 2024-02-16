package com.javafxgrid.model.cells;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import java.util.Optional;

public class UnmodifiableCell implements Cell{


        private String fixedTag;
        private String description;

        public UnmodifiableCell(String tag, String description) {
            this.fixedTag = tag;
            this.description = description;
        }

        @Override
        public Type getType() {
            throw new IllegalStateException();
        }

        @Override
        public ObservableBooleanValue visibilityObservable() {
            throw new IllegalStateException();
        }

        @Override
        public Optional<Integer> getCount() {
            throw new IllegalStateException();
        }

        @Override
        public ObservableBooleanValue flaggedObservable() {
            throw new IllegalStateException();
        }

        @Override
        public void reveal() {
            throw new IllegalStateException();
        }

        @Override
        public void changeFlag() {
            throw new IllegalStateException();
        }

        @Override
        public StringProperty tagProprety() {
            throw new IllegalStateException();
        }

        @Override
        public String fixedTag() {
            return this.fixedTag;
        }

        public String toString() {
            return this.description;
        }
        
    
    


}
