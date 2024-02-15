package com.javafxgrid.model.cells;
import java.util.*;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableBooleanValue;

public abstract class AbstractCell implements Cell{

    private BooleanProperty visibilityProp;
    protected BooleanProperty flagProp;

    public AbstractCell(BooleanProperty statusProp, BooleanProperty flaggedProp) {
        this.visibilityProp = statusProp;
        this.flagProp = flaggedProp;
    }

    public abstract Type getType();

    public ObservableBooleanValue visibilityObservable() {
        return visibilityProp;
    }

    public ObservableBooleanValue flaggedObservable() {
        return this.flagProp;
    }

    public void reveal() {
        this.visibilityProp.setValue(true);
    }

    public void changeFlag() {
        this.flagProp.set(!this.flagProp.getValue());
    }

    public abstract Optional<Integer> getCount();

}
