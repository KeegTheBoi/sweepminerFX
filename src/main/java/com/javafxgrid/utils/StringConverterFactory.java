package com.javafxgrid.utils;

import com.javafxgrid.model.SettingsLogic;

import javafx.util.StringConverter;

public interface StringConverterFactory {

    
    StringConverter<SettingsLogic.Difficulty> simpleDifficultyConverter();

}
