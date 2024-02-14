package com.javafxgrid.viewmodel;

import java.util.stream.Collectors;

import com.javafxgrid.model.Level;
import com.javafxgrid.model.SettingsModel;
import com.javafxgrid.model.SettingsLogic.Difficulty;
import com.javafxgrid.utils.StringConverterFactory;
import com.javafxgrid.utils.StringConverterFactoryImpl;
import com.javafxgrid.viewmodel.appmediators.AbstractBackerViewModel;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.util.StringConverter;

public class SettingViewModelImpl extends AbstractBackerViewModel implements SettingsViewModel{
    
    private Property<Difficulty> difficultProp;
    private SettingsModel settingModel;
    private StringConverterFactory converterFactory;
    private static ObjectProperty<Level> lev;
    

    public SettingViewModelImpl() {
        this.settingModel = new SettingsModel();//TODO Implement factory pattern
        converterFactory = new StringConverterFactoryImpl();
        this.difficultProp = new SimpleObjectProperty<Difficulty>(settingModel.getCurrent());
        lev = new SimpleObjectProperty<Level>((Level)settingModel.getResult(null).get());
        difficultProp.addListener((u, i, d) -> {
            settingModel.setDifficulty(d);
            lev.setValue((Level)settingModel.getResult(null).get());
            System.out.println(d.toString());
        });
    }

    @Override
    public ObservableList<Difficulty> getListDifficulty() {
        return settingModel.getListDifficulty().stream()
            .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    @Override
    public Property<Difficulty> selectedProprety() {
        return difficultProp;
    }

    @Override
    public StringConverter<Difficulty> getDiffConverter() {
        return converterFactory.simpleDifficultyConverter();
    }

    @Override
    public ObjectProperty<Level> getLevel() {
        return lev;
    }

}
