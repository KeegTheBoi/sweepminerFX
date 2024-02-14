package com.javafxgrid.model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SettingsModel implements SettingsLogic{

    private static final int VIETNAM_SIZE = 2;
    private List<Difficulty> listDifficulty;
    private Difficulty current;

    public SettingsModel() {
        current = Difficulty.EASY;
        //makes it unmutable
        this.listDifficulty = Arrays.stream(Difficulty.values()).toList();
    }

    private int getSize(Difficulty diff) {
        return (diff.ordinal() + 1) * (diff == Difficulty.VIETNAM ? VIETNAM_SIZE : 4);
    }

    private double getDiff(Difficulty diff) {
        return (diff.ordinal() + 1) * (10.0 / Difficulty.values().length);
    }

    public Difficulty getCurrent() {
        return current;
    }

    public List<Difficulty> getListDifficulty() {
        return listDifficulty;
    }

    public void setDifficulty(Difficulty value) {
        this.current = value;
    }

    
    public Function<Difficulty, Level> result() {
        return x -> new Level(getSize(x), (int)Math.ceil(getDiff(x)));
    }

    @Override
    public Optional<Object> getResult(Object input) {
        return Optional.ofNullable(current).map(result());
        
    }
    

}
