package com.javafxgrid.model.cells;
import java.util.*;

import javafx.beans.property.BooleanProperty;

public abstract class AbstractCell implements Cell{

    private BooleanProperty visibilityProp;
    protected BooleanProperty flagProp;

    public AbstractCell(BooleanProperty status, BooleanProperty flagged) {
        this.visibilityProp = status;
        this.flagProp = flagged;
    }

    public abstract Type getType();

    public BooleanProperty visibilityProprety() {
        return visibilityProp;
    }

    public BooleanProperty isFlagged() {
        return this.flagProp;
    }

    public void reveal() {
        this.visibilityProprety().setValue(true);
    }

    public void changeFlag() {
        this.flagProp.set(!this.flagProp.getValue());
    }

    public abstract Optional<Integer> getCount();

}
