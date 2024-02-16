package com.javafxgrid.viewmodel.api;

import java.util.Map;

import com.javafxgrid.viewmodel.ViewModel;
import com.javafxgrid.viewmodel.appmediators.BackerViewModel;

import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableStringValue;

public interface GridViewModel extends ViewModel, BackerViewModel {

    static final String ID_SEPARATOR = ":"; //TODO should put it on an interface

    Map<Long, TileObservers> getGridMap();

    void disableAndHit(ObservableStringValue buttonID);

    int gridSize();

    ObservableIntegerValue getThickObserver();

    void stopAllThreads();

    void handleLeftClick(ObservableStringValue stringObservable); 

    record TileObservers(ObservableStringValue idProp, ObservableBooleanValue disableProp){}

    String retriveTag(ObservableStringValue embeddedString);
    
}
