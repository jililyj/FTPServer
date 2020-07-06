package sample.controller;

import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.utils.PropertiesUtils;

public class TitleController {
    double oldStageX;  //窗体离屏幕X轴的距离
    double oldStageY;  //窗体离屏幕Y轴的距离
    double oldScreenX; //当前鼠标位置离屏幕X轴距离
    double oldScreenY; //当前鼠标位置离屏幕Y轴距离

    public Button minWindow;
    public Button closeWindow;
    public Pane paneExit;
    public Pane paneZoom;
    public VBox scene;


    public void movePaneExit(MouseEvent mouseEvent) {
        paneExit.setCursor(Cursor.HAND);
        paneExit.setStyle("-fx-background-color: red");
    }

    public void movePaneZoom(MouseEvent mouseEvent) {
        paneZoom.setCursor(Cursor.HAND);
        paneZoom.setStyle("-fx-background-color: #C7CACF");
    }

    public void exitMouse(MouseEvent mouseEvent) {
        paneExit.setStyle("-fx-background-color: transparent");
        paneZoom.setStyle("-fx-background-color: transparent");
    }

    public void moveBtnZoom(MouseEvent mouseEvent) {
        minWindow.setCursor(Cursor.HAND);
        paneZoom.setStyle("-fx-background-color: #C7CACF");
    }

    public void moveBtnExit(MouseEvent mouseEvent) {
        closeWindow.setCursor(Cursor.HAND);
        paneExit.setStyle("-fx-background-color: red");
    }

    public void clickBtnExit(MouseEvent mouseEvent) {
        PropertiesUtils.setProperties("server.switch","false");
        System.exit(0);
    }

    public void clickBtnZoom(MouseEvent mouseEvent) {
        Stage stage = (Stage) scene.getScene().getWindow();
        stage.setIconified(true);

    }

    public void releaseBtnZoom(MouseEvent mouseEvent) {
        paneZoom.setStyle("-fx-background-color: transparent");
    }



    public void dragTitle(MouseEvent mouseEvent) {
//        System.out.println("drag the title!");
        Stage stage = (Stage) scene.getScene().getWindow();
//        System.out.println(mouseEvent.getScreenX());
        stage.setX(mouseEvent.getScreenX() - this.oldScreenX + this.oldStageX);
//        System.out.println(mouseEvent.getScreenX() - this.oldScreenX + this.oldStageX);
        stage.setY(mouseEvent.getScreenY() - this.oldScreenY + this.oldStageY);
//        System.out.println(mouseEvent.getScreenY() - this.oldScreenY + this.oldStageY);
    }

    public void pressTitle(MouseEvent e) {
        Stage stage = (Stage) scene.getScene().getWindow();
        this.oldStageX = stage.getX();
        this.oldStageY = stage.getY();
        this.oldScreenX = e.getScreenX();
        this.oldScreenY = e.getScreenY();

//        System.out.println("oldStageX:"+stage.getX());
//        System.out.println("oldStageY:"+stage.getY());
//        System.out.println("oldScreenX:"+e.getScreenX());
//        System.out.println("oldScreenY:"+e.getScreenY());
    }
}
