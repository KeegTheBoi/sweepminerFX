package com.javafxgrid.view;


import com.javafxgrid.viewmodel.api.MenuViewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.util.Pair;
import javafx.scene.control.*;

public class MenuView implements View{

    public static final int id = 0;

    @FXML
    VBox menuBox;

    private MenuViewModel menuViewModel;

    public MenuView() {}

    @FXML
    private void initialize() {
        menuViewModel = new MenuViewModel();
        menuViewModel.getListOfButtons().entrySet().stream().map(u -> new Pair<>(u, new Button()))
            .peek(pair -> pair.getValue().textProperty().bind(pair.getKey().getValue()))
            .peek(pair -> pair.getValue().idProperty().bind(new SimpleStringProperty(pair.getKey().getKey())))
            .peek(b -> b.getValue().setOnAction(this::onClick))
            .map(Pair::getValue)
            .forEach(menuBox.getChildren()::add);
        
    }

    private void onClick(ActionEvent event) { 
        Button btn = (Button)event.getSource();
        menuViewModel.switchTo(btn.idProperty());
    }

    @Override
    public int viewId() {
        return id;
    }

}
