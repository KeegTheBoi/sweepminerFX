package com.javafxgrid.view;

import java.util.Comparator;
import com.javafxgrid.model.Level;
import com.javafxgrid.viewmodel.GridViewModel;
import com.javafxgrid.viewmodel.GridViewModelImpl;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        this.gridVM.getThickProperty().addListener((o, old, newV) -> 
                this.gridVM.getAppManeger().performReactiveAction( () -> 
                    lblTime.setText(covertSecondsToFormat(newV.intValue()))
                )
            );
        this.size = gridVM.gridSize();
        this.setConstrains();
        System.out.println(board.heightProperty().divide(this.size).get() + " "+ board.widthProperty().divide(this.size).get());
        System.out.println(board.heightProperty().getValue() + " "+ board.widthProperty().getValue());
        gridVM.getGridMap()
            .entrySet()
            .stream()
            .sorted(
                Comparator.comparingLong(t -> hash(t.getValue().getKey().x(), t.getValue().getKey().y()))
            )
            .map(y -> 
                new Pair<>(
                    new Button(),
                    new Pair<>(
                        y.getKey(),
                        y.getValue().getValue()
                    )
                )
            )
            .peek(this::prepareGrid)
            .map(Pair::getKey)
            .forEach(board.getChildren()::add);
    }

    private String covertSecondsToFormat(int totalSeconds) {
        return String.format("%02d:%02d", totalSeconds / 60 % 60, totalSeconds % 60);
    }


    private void setConstrains() {
        
        board.getChildren().clear();
        board.setPrefRows(this.size);
        board.setPrefSize(tileSize * this.size, tileSize * this.size);
    }

    private long hash(int x, int y) {
        long comb = (long)y << 32 | (x & 0xFFFFFFFFL);
        return comb >= 0 ? comb : -comb;
    }

    @Override
    public int viewId() {
        return id;
    }

    private void prepareGrid(Pair<Button, Pair<StringProperty, BooleanProperty>> cell) {
        Button btn = cell.getKey();
        btn.prefHeightProperty().bind(rootNode.heightProperty().subtract(dashBoardPane.getHeight()).divide(this.size).subtract(1));
        btn.prefWidthProperty().bind(rootNode.widthProperty().divide(this.size).subtract(0.8));
        btn.idProperty().bind(cell.getValue().getKey());
        btn.idProperty().addListener((ob, oldValue, newValue) -> {
            System.out.println("ID old value: ".concat(oldValue).concat(" new value: ".concat(newValue)));
            var g = newValue.split(":")[0];
            cell.getKey().getStyleClass().set(1, g);
        });
        btn.disableProperty().addListener((ob, o, n) -> {
            System.out.println("DISABLE old value: ".concat(o.toString()).concat(" new value: ".concat(n.toString())));
        });
        btn.disableProperty().bind(cell.getValue().getValue());
        btn.getStyleClass().set(0, "cell-button"); 
        var g = cell.getValue().getKey().getValue().split(":")[0];
        btn.getStyleClass().add(g);
        btn.setOnAction(this::handleClick);
        btn.setOnMousePressed(e -> {
            System.out.println("SECOND MOUSE: " + e.isSecondaryButtonDown());
            if(e.isSecondaryButtonDown()) {
                this.gridVM.handleLeftClick(btn.idProperty());
            }
        });
    }

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

    private void handleClick(ActionEvent e) {
        Button btn = (Button)e.getSource();
        var h = btn.idProperty();
        gridVM.disableAndHit(h);
    }
    
}
