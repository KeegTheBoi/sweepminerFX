package com.javafxgrid.utils;

import com.javafxgrid.model.SettingsLogic;

import javafx.util.StringConverter;

public class StringConverterFactoryImpl implements StringConverterFactory {

    protected static final String SEPARATOR = ":";

    @Override
    public StringConverter<SettingsLogic.Difficulty> simpleDifficultyConverter() {
        return new StringConverter<SettingsLogic.Difficulty>() {

            @Override
            public SettingsLogic.Difficulty fromString(String arg0) {
                return SettingsLogic.Difficulty.valueOf(arg0);
            }

            @Override
            public String toString(SettingsLogic.Difficulty arg0) {
                return "Select Difficulty: " +arg0.name();
            }
            
        };
    }


    
}
