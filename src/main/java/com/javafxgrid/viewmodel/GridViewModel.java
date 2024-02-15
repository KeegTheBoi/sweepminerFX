package com.javafxgrid.viewmodel;

import java.util.Map;

import com.javafxgrid.model.Coord;
import com.javafxgrid.viewmodel.appmediators.BackerViewModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Pair;

public interface GridViewModel extends ViewModel, BackerViewModel {

    Map<StringProperty, Pair<Coord, BooleanProperty>> getGridMap();

    void disableAndHit(StringProperty buttonID);

    int gridSize();

    IntegerProperty getThickProperty();

    void stopAllThreads();

    void handleLeftClick(StringProperty stringProperty); 
    
}
