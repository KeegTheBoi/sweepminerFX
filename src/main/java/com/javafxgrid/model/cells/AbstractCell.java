package com.javafxgrid.model.cells;
import java.util.*;

import javafx.beans.property.BooleanProperty;

public abstract class AbstractCell implements Cell{

    private BooleanProperty visibility;
    protected boolean flag;

    public AbstractCell(BooleanProperty status) {
        this.visibility = status;
    }

    public abstract Type getType();

    public BooleanProperty visibilityProprety() {
        return visibility;
    }

    public boolean isFlagged() {
        return this.flag;
    }

    public void reveal() {
        this.visibilityProprety().setValue(true);
    }

    public void changeFlag() {
        this.flag = !this.flag;
    }

    public abstract Optional<Integer> getCount();

}
