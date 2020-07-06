package sample.controller;

import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import sample.utils.PropertiesUtils;

import java.io.*;
import java.net.URL;
import java.util.Properties;

public class ConfigController {
    public Button serverBtn;
    public Button configBtn;
    public Button fixBtn;
    public Button save;
    public AnchorPane scene;
    public TextField ftp_port;
    public TextField ftp_dir;
    public TextField user_account;
    public TextField user_password;
    public Button browse;


    public void Init(){
        Properties pro = PropertiesUtils.getProperties();
        this.ftp_port.setText(pro.getProperty("server.port"));
        this.ftp_dir.setText(pro.getProperty("server.dir"));
        this.user_account.setText(pro.getProperty("user.account"));
        this.user_password.setText(pro.getProperty("user.password"));

    }

    public void changeMouse(MouseEvent mouseEvent) {
        serverBtn.setCursor(Cursor.HAND);
        configBtn.setCursor(Cursor.HAND);
        fixBtn.setCursor(Cursor.HAND);
        save.setCursor(Cursor.HAND);
    }

    public void clickFixBtn(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fix.fxml"));
        Stage stage = (Stage) scene.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void clickServerBtn(MouseEvent mouseEvent) throws IOException {
        URL location = getClass().getResource("../ftp.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) scene.getScene().getWindow();
        stage.setScene(new Scene(root));
        FtpController ftpController=fxmlLoader.getController();
        ftpController.Init();
        stage.show();
    }

    public void clickConfigBtn(MouseEvent mouseEvent) throws IOException {
        URL location = getClass().getResource("../config.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) scene.getScene().getWindow();
        stage.setScene(new Scene(root));
        ConfigController configController=fxmlLoader.getController();
        configController.Init();
        stage.show();
    }

    public void browseBtn(MouseEvent mouseEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) scene.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        String path = file.getPath();//选择的文件夹路径
        ftp_dir.setText(path);

    }

    public void saveBtn(MouseEvent mouseEvent) throws IOException {
        String ftpPort=ftp_port.getText();
        String ftpDir=ftp_dir.getText();
        String userAccount=user_account.getText();
        String userPassword=user_password.getText();
        if(StringUtils.isBlank(ftpPort)||StringUtils.isBlank(ftpDir))
        {
//            JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提醒");
            alert.setHeaderText(null);
            alert.setContentText("'FTP端口'和'存放目录'不能为空！");
            alert.showAndWait();

        }else {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("sample/server.properties");
            Properties pro = new Properties();
            pro.load(is);
            pro.setProperty("server.port",ftpPort);
            pro.setProperty("server.dir",ftpDir);
            pro.setProperty("user.account",userAccount);
            pro.setProperty("user.password",userPassword);
            FileOutputStream oFile=new FileOutputStream(Thread.currentThread().getContextClassLoader().getResource("sample/server.properties").getFile());
            try {
                pro.store(oFile,"comments");
                System.out.println(pro.toString());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("信息");
                alert.setHeaderText(null);
                alert.setContentText("保存成功！");
                alert.showAndWait();
//                FtpServerController.stop();
//                FtpServerController.start();
//                alert.setTitle("信息");
//                alert.setHeaderText(null);
//                alert.setContentText("FTP服务已重启！");
//                alert.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.toString());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText(null);
                alert.setContentText(e.toString());
                alert.showAndWait();
            }
        }
    }

    public void save2Btn(MouseEvent mouseEvent) {
    }
}
    
