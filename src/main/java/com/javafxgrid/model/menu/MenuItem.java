package com.javafxgrid.model.menu;


public class MenuItem {
    private final int id;
    private final String description;

    public MenuItem(int id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public int getId() {
        return id;
    }

    
}
