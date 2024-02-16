package com.javafxgrid.view;

import com.javafxgrid.model.Level;
import com.javafxgrid.utils.StringManipulators;
import com.javafxgrid.viewmodel.GridViewModelImpl;
import com.javafxgrid.viewmodel.api.GridViewModel;
import com.javafxgrid.viewmodel.api.GridViewModel.TileObservers;

import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class GameView implements DynamicView {


    private static final int id = 3;

    private static final double tileSize = 40;

    @FXML
    TilePane board;

    @FXML
    VBox rootNode;

    @FXML
    BorderPane dashBoardPane;

    @FXML
    Button btnBack, btnReset;

    @FXML
    Label lblTime;

    private GridViewModel gridVM;

    private int size;

    private static Level level;

    public GameView() {}

    @FXML
    private void initialize() {}

    @Override
    public <I> void start(I param) {
        level = (Level)param;
        this.gridVM = new GridViewModelImpl(level);
        this.addTimeLabelListener();
        this.size = gridVM.gridSize();
        this.setConstrains();
        
        System.out.println(board.heightProperty().divide(this.size).get() + " "+ board.widthProperty().divide(this.size).get());
        System.out.println(board.heightProperty().getValue() + " "+ board.widthProperty().getValue());
        
        gridVM.getGridMap() 
            .entrySet().stream()
            .map(y -> new Pair<>(new Button(),y.getValue()))
            .peek(this::prepareGrid)
            .map(Pair::getKey)
            .forEach(board.getChildren()::add);
    }

    private void addTimeLabelListener() {
        this.gridVM.getThickObserver().addListener((o, old, newV) -> 
                this.gridVM.getAppManeger().performReactiveAction( () -> 
                    lblTime.setText(StringManipulators.fromSecondsToFormat(newV.intValue()))
                )
            );
    }

    @Override
    public int viewId() {
        return id;
    }

    //#region BUTTON SETTERS
    private void prepareGrid(Pair<Button, TileObservers> entry) {
        Button btn = entry.getKey();
        this.bindBtnSizePropreties(btn);
        this.bindPropreties(btn, entry.getValue());
        this.addButtonListeners(btn);
        this.setBtnImages(btn, entry.getValue().idProp());
        this.setBtnActions(btn);
    }

    private void bindBtnSizePropreties(Button btn) {
        btn.prefHeightProperty().bind(rootNode.heightProperty().subtract(dashBoardPane.getHeight()).divide(this.size).subtract(1));
        btn.prefWidthProperty().bind(rootNode.widthProperty().divide(this.size).subtract(0.8));
    }

    private void bindPropreties(Button btn, TileObservers tile) {
        btn.idProperty().bind(tile.idProp());
        btn.disableProperty().bind(tile.disableProp());
    }

    private void addButtonListeners(Button btn) {
        btn.idProperty().addListener((ob, oldValue, newValue) -> {
            System.out.println("ID old value: ".concat(oldValue).concat(" new value: ".concat(newValue)));
            btn.getStyleClass().set(1, extractTagFromButtonKey(newValue));
        });
        btn.disableProperty().addListener((ob, o, n) -> {
            System.out.println("DISABLE old value: ".concat(o.toString()).concat(" new value: ".concat(n.toString())));
        });
    }

    private String extractTagFromButtonKey(String newValue) {
        return newValue.split(GridViewModel.ID_SEPARATOR)[0];
    }

    private void setBtnImages(Button btn, ObservableStringValue key) {
        btn.getStyleClass().set(0, "cell-button"); 
        var g = gridVM.retriveTag(key); // SHOULD GET METHODS FROM VIEWMODEL
        btn.getStyleClass().add(g);
    }

    private void setBtnActions(Button btn) {
        btn.setOnAction(e -> gridVM.disableAndHit(btn.idProperty()));
        btn.setOnMousePressed(e -> {
            if(e.isSecondaryButtonDown()) {
                this.gridVM.handleLeftClick(btn.idProperty());
            }
        });
        btn.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.F)) {
                this.gridVM.handleLeftClick(btn.idProperty());
            }
        });
    }
    //#endregion

    @FXML
    private void onReset() {
        this.gridVM.stopAllThreads();
        this.start(level);
    }

    @FXML
    private void goToMenu() {
        this.gridVM.stopAllThreads();
        this.gridVM.goToMenu();
    }

    private void setConstrains() {
        board.getChildren().clear();
        board.setPrefRows(this.size);
        board.setPrefSize(tileSize * this.size, tileSize * this.size);
    }
    
}
