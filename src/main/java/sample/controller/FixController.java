package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FixController {
    public Button serverBtn;
    public Button configBtn;
    public Button fixBtn;
    public AnchorPane fixScene;

    public void changeMouse(MouseEvent mouseEvent) {
        serverBtn.setCursor(Cursor.HAND);
        configBtn.setCursor(Cursor.HAND);
        fixBtn.setCursor(Cursor.HAND);
    }

    public void clickserverBtn(MouseEvent mouseEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("../ftp.fxml"));
        Stage stage = (Stage) fixScene.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
