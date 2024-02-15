package com.javafxgrid.model;

import java.util.List;

//TODO implement a factory of settings
public interface SettingsLogic extends Model {

    public enum Difficulty {
        EASY, INTERMIDIATE, HARD, VIETNAM, RUSSIA
    }
    
    List<Difficulty> getListDifficulty();

    void setDifficulty(Difficulty diff);

    Difficulty getCurrent();
}
