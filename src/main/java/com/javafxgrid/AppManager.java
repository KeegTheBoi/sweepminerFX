package com.javafxgrid;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.javafxgrid.viewmodel.appmediators.ViewManager;

public class AppManager extends Application implements ViewManager{

    private static Scene scene;
    private static Stage stage;
    private static List<Pair<Parent, Object>> roots;
    private final Alert alert = new Alert(AlertType.NONE);

    @SuppressWarnings("exports")
    @Override
    public void start(Stage primeStage) throws Exception {
        stage = primeStage;
        stage.setTitle("MineSweeper");
        stage.setOnCloseRequest(e -> System.exit(0));
        setScene("Mine");
    }

    public AppManager() {}

    public void setScene(String title) {
        roots = loadFXML();
        scene = new Scene(roots.get(Views.MAIN).getKey());
        //COULD BE DONE ON FXML [SEPARATE UI WITH CODE]
        scene.getStylesheets().add(AppManager.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        
        stage.show();
        
        // stage.maxWidthProperty().bind(Bindings.when(stage.widthProperty().greaterThan(stage.heightProperty()))
        //     .then(stage.heightProperty())
        //     .otherwise(stage.heightProperty()));

        // stage.maxWidthProperty().bind(stage.heightProperty());
        
        // stage.minWidthProperty().bind(stage.widthProperty());
        // stage.minHeightProperty().bind(stage.heightProperty());
        
        
    }

    @Override
    public Object switchView(int id){
        var entry = getParents(id);
        scene.setRoot(entry.getKey());
        stage.sizeToScene();
        return entry.getValue();
    }

    private Pair<Parent, Object> getParents(int id) {
        return Optional.of(id).filter(i -> i < roots.size() && i >= 0 && !roots.isEmpty())
            .map(roots::get)
            .orElseThrow(IllegalArgumentException::new);
    }

    private List<Pair<Parent, Object>> loadFXML() {
        return ResourcesFinder.FXMLfiles().stream()
            .map(FXMLLoader::new)
            .map(this::loadInput).toList();
    }

    private Pair<Parent, Object> loadInput(FXMLLoader loader) {
        try  {
            Parent t = loader.load();
            Object y = loader.getController();
            return new Pair<Parent, Object>(t, y);
        } catch (IOException e) {
            Alert al = new Alert(AlertType.ERROR, e.getMessage());
            al.showAndWait();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void displayMessage(String title, String header, String content) {
        this.setAndShowAlert(AlertType.INFORMATION, title, header, content);
        this.performReactiveAction(this.alert::showAndWait);
    }

    private void setAndShowAlert(AlertType alertType, String title, String header, String content) {
        this.alert.setAlertType(alertType);
        this.alert.setTitle(title);
        this.alert.setHeaderText(header);
        this.alert.setContentText(content);
    }

    @Override
    public void closeWithMessage(String header, String closingContent) {
        this.setAndShowAlert(AlertType.CONFIRMATION, "CLOSING", header, closingContent);
        this.performReactiveAction(() -> this.alert.showAndWait().ifPresent(b -> {
            if(b.equals(ButtonType.OK)) { //needed to prevent heavy dialog dispaly
                this.close();
            } else {
                this.close();
            }
        }
        ));
    }

    @Override
    public void close() {
        this.performReactiveAction(stage::close);
        this.exit();
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    @Override
    public void performReactiveAction(Runnable runner) {
        Platform.runLater(runner);
    }

    

    
    
    

}
