package sample.controller;

import com.jfoenix.controls.JFXToggleButton;


;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.apache.ftpserver.ftplet.FtpException;
import sample.utils.PropertiesUtils;

import java.io.*;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class FtpController {
    public Button serverBtn;
    public Button configBtn;
    public Button fixBtn;
    public JFXToggleButton swicth;
    public Label FTPlabel;
    public Line FTPline;
    public AnchorPane scene;
    public TextField ftp_port;
    public TextField ftp_dir;
    public TextField user_account;
    public TextField user_password;


    public void Init() {
        boolean flag;
        Properties pro = PropertiesUtils.getProperties();
        flag = Boolean.parseBoolean(pro.getProperty("server.switch"));
        System.out.println(flag);
        if (flag == false) {
            swicth.setSelected(false);
        } else if (flag == true) {
            swicth.setSelected(true);
            FTPlabel.setText("FTP服务已开启");
            FTPlabel.setTextFill(Color.valueOf("#337DFB"));
            FTPline.setStroke(Color.valueOf("#337DFB"));
            FTPline.getStrokeDashArray().clear();
        }


    }


    public void changeMouse(MouseEvent mouseEvent) {
        serverBtn.setCursor(Cursor.HAND);
        configBtn.setCursor(Cursor.HAND);
        fixBtn.setCursor(Cursor.HAND);
    }

    public void changeSwicth(MouseEvent mouseEvent) {
        System.out.println(swicth.isSelected());
        if (swicth.isSelected()) {
            FtpServerController.start();
            PropertiesUtils.setProperties("server.switch", "true");
            FTPlabel.setText("FTP服务已开启");
            FTPlabel.setTextFill(Color.valueOf("#337DFB"));
            FTPline.setStroke(Color.valueOf("#337DFB"));
            FTPline.getStrokeDashArray().clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("信息");
            alert.setHeaderText(null);
            alert.setContentText("FTP服务已开启！");
            alert.showAndWait();
            System.out.println();
        } else {
            FtpServerController.stop();
            PropertiesUtils.setProperties("server.switch", "false");
            FTPlabel.setText("FTP服务已关闭");
            FTPlabel.setTextFill(Color.valueOf("#fb3333"));
            FTPline.setStroke(Color.valueOf("#fb3333"));
            FTPline.getStrokeDashArray().clear();
            FTPline.getStrokeDashArray().addAll(10d, 10d, 10d, 10d);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("信息");
            alert.setHeaderText(null);
            alert.setContentText("FTP服务已关闭！");
            alert.showAndWait();
        }
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
        FtpController ftpController = fxmlLoader.getController();
        ftpController.Init();
        stage.show();


//        Parent root = FXMLLoader.load(getClass().getResource("../ftp.fxml"));
//        Stage stage = (Stage) scene.getScene().getWindow();
//        stage.setScene(new Scene(root));
    }

    public void clickConfigBtn(MouseEvent mouseEvent) throws IOException {
        URL location = getClass().getResource("../config.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) scene.getScene().getWindow();
        stage.setScene(new Scene(root));
        ConfigController configController = fxmlLoader.getController();
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
        String ftpPort = ftp_port.getText();
        String ftpDir = ftp_dir.getText();
        String userAccount = user_account.getText();
        String userPassword = user_password.getText();
        if (StringUtils.isBlank(ftpPort) || StringUtils.isBlank(ftpDir)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("提醒");
            alert.setHeaderText(null);
            alert.setContentText("'FTP端口'和'存放目录'不能为空！");
            alert.showAndWait();
        } else {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("sample/server.properties");
            System.out.println("this is 2");
            Properties pro = new Properties();
            pro.load(is);

            pro.setProperty("server.port", ftpPort);
            pro.setProperty("server.dir", ftpDir);
            pro.setProperty("user.account", userAccount);
            pro.setProperty("user.password", userPassword);


            FileOutputStream oFile = new FileOutputStream(Thread.currentThread().getContextClassLoader().getResource("sample/server.properties").getFile());
            try {
                pro.store(oFile, "comments");
                System.out.println(pro.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        Set<String> keys = pro.stringPropertyNames();
//        // 创建迭代器
//        Iterator<String> ite = keys.iterator();
//        while (ite.hasNext()) {
//            // 得到所有的键
//            String key = ite.next();
//            // 根据键来获取值
//            String value = pro.getProperty(key);
//            System.out.println(key + "=" + value);
//        }

    }


    public void refreshBtn(MouseEvent mouseEvent) {

    }

    public void save2Btn(MouseEvent mouseEvent) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("sample/server.properties");
        System.out.println(classloader.getResource("sample/server.properties").getPath());
        Properties pro = new Properties();

        pro.load(is);
        ftp_port.setText(pro.getProperty("server.port"));
        ftp_dir.setText(pro.getProperty("server.dir"));
        user_account.setText(pro.getProperty("user.account"));
        user_password.setText(pro.getProperty("user.password"));


    }
}
