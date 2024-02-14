package com.javafxgrid.viewmodel.appmediators;

import com.javafxgrid.AppManager;

public abstract class AbstractViewModel{
    
    private static final ViewManager gui = new AppManager();

    public ViewManager getAppManeger() {
        return gui;
    }


}
