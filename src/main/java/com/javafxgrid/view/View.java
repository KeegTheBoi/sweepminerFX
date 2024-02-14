package com.javafxgrid.view;


public interface View {

    /**
     * Basically signals the viewmodel that a input has been triggered
     * it should handle multiple signals in a Multi-Threading context
     * @param <E>
     * @return
     */

    // void signalEvent();
    
    /**
     * Initialization in overrided by FXML method (initialize)
     * TODO check visibilty 
     */
    int viewId();

}
