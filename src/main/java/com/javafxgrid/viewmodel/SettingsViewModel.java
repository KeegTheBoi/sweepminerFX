package com.javafxgrid.viewmodel;


import com.javafxgrid.model.Level;
import com.javafxgrid.model.SettingsLogic.Difficulty;
import com.javafxgrid.viewmodel.appmediators.BackerViewModel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

public interface SettingsViewModel extends ViewModel, BackerViewModel{

    public ObservableList<Difficulty> getListDifficulty();

    public Property<Difficulty> selectedProprety();

    public StringConverter<Difficulty> getDiffConverter();

    ObjectProperty<Level> getLevel();

}
