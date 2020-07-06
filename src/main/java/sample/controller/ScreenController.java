package sample.controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class ScreenController {
    private  HashMap<String, Pane> screenMap = new HashMap<String, Pane>();

    private Scene main;

    public ScreenController(Scene main) {
        this.main = main;
    }

    public ScreenController() {

    }

    public void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    public void removeScreen(String name){
        screenMap.remove(name);
    }

    public void activate(String name){
        main.setRoot(screenMap.get(name) );
    }

    public Scene getMain() {
        return main;
    }

    public String toStringMap(){
        return this.screenMap.toString();
    }
}

