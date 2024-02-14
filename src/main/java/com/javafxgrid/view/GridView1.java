package com.javafxgrid.view;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

import com.javafxgrid.model.Coord;
import com.javafxgrid.model.Level;
import com.javafxgrid.viewmodel.GridViewModel;
import com.javafxgrid.viewmodel.GridViewModelImpl;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class GridView1 implements View {

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

    private GridViewModel gridVM;

    public GridView1() {}

    @FXML
    private void initialize() {}

    public void startGame(Level lev) {
        this.gridVM = new GridViewModelImpl(lev);
        this.setConstrains();
        System.out.println(board.heightProperty().divide(gridVM.size()).get() + " "+ board.widthProperty().divide(gridVM.size()).get());
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

    private void setConstrains() {
        
        board.getChildren().clear();
        board.setPrefRows(gridVM.size());
        board.setPrefSize(tileSize * gridVM.size(), tileSize * gridVM.size());
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
        btn.prefHeightProperty().bind(rootNode.heightProperty().subtract(dashBoardPane.getHeight()).divide(gridVM.size()).subtract(1));
        btn.prefWidthProperty().bind(rootNode.widthProperty().divide(gridVM.size()).subtract(1));
        btn.idProperty().bind(cell.getValue().getKey());
        btn.idProperty().addListener((ob, oldValue, newValue) -> {
            System.out.println("old value: ".concat(oldValue).concat(" new value: ".concat(newValue)));
            var g = newValue.split(":")[0];
            cell.getKey().getStyleClass().set(1, g);
        });
        btn.disableProperty().addListener((ob, o, n) -> {
            System.out.println("old value: ".concat(o.toString()).concat(" new value: ".concat(n.toString())));
        });
        btn.disableProperty().bind(cell.getValue().getValue());
        btn.getStyleClass().set(0, "cell-button"); 
        var g = cell.getValue().getKey().getValue().split(":")[0];
        btn.getStyleClass().add(g);
        btn.setOnAction(this::handleClick);
    }

    @FXML
    private void onReset() {

    }

    @FXML
    private void goToMenu() {
        this.gridVM.goToMenu();
    }

    private void handleClick(ActionEvent e) {
        Button btn = (Button)e.getSource();
        var h = btn.idProperty();
        gridVM.disableAndHit(h);
    }
    
}
