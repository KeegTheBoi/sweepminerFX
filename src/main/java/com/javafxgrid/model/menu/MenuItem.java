package com.javafxgrid.model.menu;


public class MenuItem {
    private int id;
    private String description;

    

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
