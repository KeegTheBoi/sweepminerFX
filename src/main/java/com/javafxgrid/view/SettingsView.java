package com.javafxgrid.view;

import com.javafxgrid.model.SettingsLogic.Difficulty;
import com.javafxgrid.viewmodel.SettingViewModelImpl;
import com.javafxgrid.viewmodel.api.SettingsViewModel;

import java.util.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class SettingsView implements View{
    
    private static final int id = 1;

    @FXML
    VBox mainPane;

    @FXML
    ComboBox<Difficulty> cmbDifficulty;

    @FXML
    Label lblDifficulty;

    @FXML
    Button btnBack;

    private static SettingsViewModel settingsViewModel;

    public SettingsView()  {}

    @FXML
    private void initialize()  {
        Platform.runLater(btnBack::requestFocus);
        if(Objects.isNull(settingsViewModel)) { //this way it does the initializazion only once
            settingsViewModel = new SettingViewModelImpl();
            this.bindMenu();
        }
        
    }

    private void bindMenu() {
        cmbDifficulty.setItems(settingsViewModel.getListDifficulty());
        cmbDifficulty.valueProperty().bindBidirectional(settingsViewModel.selectedProprety());
        lblDifficulty.textProperty().bindBidirectional(settingsViewModel.selectedProprety(), settingsViewModel.getDiffConverter());      
    }

    public void goToMainMenu() {
        settingsViewModel.goToMenu();
    }


    @Override
    public int viewId() {
        return id;
    }

}
