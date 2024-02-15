package com.javafxgrid.viewmodel;

import java.util.*;
import java.util.stream.Collectors;

import com.javafxgrid.model.menu.*;
import com.javafxgrid.view.DynamicView;
import com.javafxgrid.view.GameView;
import com.javafxgrid.viewmodel.appmediators.AbstractViewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Pair;

public class MenuViewModel extends AbstractViewModel{
    
    private MenuModel menu;
    private Map<String, StringProperty> listOfButtons = new HashMap<>();
    private static SettingsViewModel settings = new SettingViewModelImpl();
    
    public MenuViewModel() {
        this.menu = new MenuModelFactoryImpl().simpleMenu();//SHOULD GET INFO FROM DATABASE
        listOfButtons = this.menu.menuList().stream()
            .map(m -> new Pair<>(String.valueOf(m.getId()), m.toString()))
            .collect(Collectors.toMap(t -> t.getKey(), t -> new SimpleStringProperty(t.getValue())));
    }

    public Map<String, StringProperty> getListOfButtons() {
        return listOfButtons;
    }

    public void switchTo(StringProperty viewId) {
        var view = this.getAppManeger().switchView(Optional.of(viewId.get()).map(this::parse)
            .filter(v -> v >= 0)
            .orElseThrow(IllegalArgumentException::new));
        if(view instanceof GameView game) {
            game.start(settings.getLevel().getValue());
        }
    }

    private Integer parse(String string1) {
        try {
            return Integer.parseInt(string1);
        } catch (Exception e) {
            return -1;
        }
        
    }
    

}
