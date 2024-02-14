package com.javafxgrid.viewmodel.appmediators;

import com.javafxgrid.Views;

public abstract class AbstractBackerViewModel extends AbstractViewModel implements BackerViewModel{

    @Override
    public void goToMenu() {
        this.getAppManeger().switchView(Views.MAIN);
    }
    
}
