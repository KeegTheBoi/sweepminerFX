package com.javafxgrid.viewmodel;

import com.javafxgrid.viewmodel.appmediators.ViewManager;

public interface ViewModel {

    /**
     * Required for closing, alerting, changing view, or better interact with the Application
     * @return
     */
    ViewManager getAppManeger();

    
    
}
