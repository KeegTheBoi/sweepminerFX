package com.javafxgrid.viewmodel.appmediators;

import com.javafxgrid.view.View;

public interface ViewManager {

    /**
     * Get unique view id, it's better rather then passing the entire viewImpl
     * or the fxml file (this could be used to separate model from view too)
     * @param id
     */
    Object switchView(int id);

    void displayMessage(String title, String header, String content);

    void closeWithMessage(String header, String closingContent);

    void close();

    void exit();

    void performReactiveAction(Runnable runner);

}
