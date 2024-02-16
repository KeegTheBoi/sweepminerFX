package com.javafxgrid.view;

import com.javafxgrid.viewmodel.GuideViewModelImpl;
import com.javafxgrid.viewmodel.api.GuideViewModel;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

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
        pageViewer.setPageFactory(this::createImage);
    
    }

    protected Node createImage(Integer index) {
        ImageView image = new ImageView();
        image.setFitHeight(100);
        image.setFitWidth(100);
        image.idProperty().bind(this.guidedVM.mapObservers().get(index));
        lblDescriptor.setText(this.guidedVM.decoding(index, 1));
        image.getStyleClass().add(this.guidedVM.decoding(index, 0));
        return image;
    }


    @FXML
    private void goToMenu() {
        this.guidedVM.goToMenu();
    }
}
