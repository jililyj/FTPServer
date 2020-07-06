package sample.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class topController  {

    private Stage primaryStage;

    /* 自定义窗口 */
    public VBox window;
    public Button minWindow;
    public Button closeWindow;

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        primaryStage = JavafxSpringboot3Application.getStage(); //primaryStage为start方法头中的Stage
//        minWindow.setOnAction(event -> primaryStage.setIconified(true)); /* 最小化 */
//        closeWindow.setOnAction((event)->System.exit(0)); /* 关闭程序 */
//    }



}
