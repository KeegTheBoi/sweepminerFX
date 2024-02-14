package com.javafxgrid.view;

import javafx.fxml.FXML;

public class GridView implements View{

    private static final int MAX_TIME = 400;
    // private Lock mutex = new ReentrantLock();

    private static final int id = 2;
    private int i = 0;
    

    public GridView()  {}

    @FXML
    public void initialize()  {

        Thread t = new Thread(() ->{

            for(i = MAX_TIME; i >= 0;) {
                tick();
                var minutes = (i % 3600) / 60;
                var seconds = i % 60;
                // App.reactiveAction(() -> lblTime.setText(String.format("%02d:%02d", minutes, seconds)));   
                i--;
            }
            // App.reactiveAction(() -> App.closeWithMessage("Time elapsed, closing window"));
        });
        t.start();
        
    }

    private void tick() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    

    @FXML
    private void restart()  {
        System.out.println("RESET");
    }

    @FXML
    private void switchMain()  {
        new MenuView();
    }


    @Override
    public int viewId() {
        return id;
    }
}