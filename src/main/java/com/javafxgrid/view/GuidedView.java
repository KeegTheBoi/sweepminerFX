package com.javafxgrid.view;


import com.javafxgrid.viewmodel.GuideViewModel;
import com.javafxgrid.viewmodel.GuideViewModelImpl;

import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import javafx.util.Pair;

import java.util.*;

public class GuidedView {

    @FXML
    private Label lblDescriptor;

    @FXML
    private Pagination pageViewer;
    
    @FXML
    private Button btnBack;

    @FXML 
    private BorderPane guideDisplayer;
    
    private final GuideViewModel guidedVM = new GuideViewModelImpl(); 


    @FXML
    private void initialize() {
        System.out.println("INITIALIZED");
        pageViewer.setPageCount(this.guidedVM.mapObservers().size());

        pageViewer.setPageFactory(new Callback<Integer,Node>() {

            @Override
            public Node call(Integer index) {
                Node n = createImage(index);
                System.out.println(n.idProperty().get());
                return n;
            }
            
        });
    
    }


    protected Node createImage(Integer index) {
        ImageView image = new ImageView();
        image.idProperty().bind(this.guidedVM.mapObservers().get(index));
        lblDescriptor.setText(this.guidedVM.decoding(index, 1));
        image.setFitHeight(100);
        image.setFitWidth(100);
        image.getStyleClass().add(this.guidedVM.decoding(index, 0));
        
        return image;
    }


    @FXML
    private void goToMenu() {
        System.out.println("GOTOMENU");
    }
}
