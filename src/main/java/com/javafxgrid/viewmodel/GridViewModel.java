package com.javafxgrid.viewmodel;

import java.util.Map;


import com.javafxgrid.viewmodel.appmediators.BackerViewModel;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableStringValue;

public interface GridViewModel extends ViewModel, BackerViewModel {

    static final String ID_SEPARATOR = ":";

    Map<Long, TileObservers> getGridMap();

    void disableAndHit(ObservableStringValue buttonID);

    int gridSize();

    ObservableIntegerValue getThickObserver();

    void stopAllThreads();

    void handleLeftClick(ObservableStringValue stringObservable); 

    record TileObservers(ObservableStringValue idProp, ObservableBooleanValue disableProp){}

    String retriveTag(ObservableStringValue embeddedString);
    
}
