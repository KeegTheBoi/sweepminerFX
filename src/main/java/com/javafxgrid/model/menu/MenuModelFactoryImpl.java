package com.javafxgrid.model.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.javafxgrid.Views;

public class MenuModelFactoryImpl implements MenuModelFactory {

    @Override
    public MenuModel simpleMenu() {
        return new SimpleMenuModel();
    }

    @Override
    public MenuModel configMenu() {
        return new SettingsMenuModel(new SimpleMenuModel());
    }

    @Override
    public MenuModel guidedMenu() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guidedMenu'");
    }

    private class SimpleMenuModel implements MenuModel {

        private List<MenuItem> avaible;

        public SimpleMenuModel() {
            avaible = new ArrayList<>();
            avaible.add(new MenuItem(Views.GRIDTEST, "PLAY"));
        }

        @Override
        public Optional<Object> getResult(Object input) {
            //caller must check if it is an istance of a model
            return Optional.empty();
        }

        @Override
        public List<MenuItem> menuList() {
            return avaible;
        }

    }

    private abstract class DecoratedMenu implements MenuModel{

        protected MenuModel decorated;

        public DecoratedMenu(MenuModel decorated) {
            this.decorated = decorated;
        }

        @Override
        public Optional<Object> getResult(Object input) {
            return decorated.getResult(input);
        }

        @Override
        public abstract List<MenuItem> menuList();
    }

    private class SettingsMenuModel extends DecoratedMenu {

        public SettingsMenuModel(MenuModel decorated) {
            super(decorated);
            this.decorated.menuList().add(new MenuItem(Views.SETTINGS, "SETTINGS"));
        }

        @Override
        public List<MenuItem> menuList() {
            return this.decorated.menuList();
        }
        
    }
    
}
