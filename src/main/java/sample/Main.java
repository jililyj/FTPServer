package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import sample.controller.FtpController;
import sample.controller.FtpServerController;
import sample.controller.ScreenController;
import sample.scene.SceneController;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = getClass().getResource("ftp.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent main = fxmlLoader.load();

//如果使用 Parent root = FXMLLoader.load(...) 静态读取方法，无法获取到Controller的实例对象
//Scene main=new Scene((Parent) FXMLLoader.load(getClass().getResource("ftp.fxml")));
        primaryStage.setTitle("东久科技FTP服务器");
        primaryStage.setScene(new Scene(main));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT); /* 透明标题栏 */
        primaryStage.getIcons().add(new Image(
                Main.class.getResourceAsStream("/img/FTPlogo.png")));

        FtpController controller = fxmlLoader.getController();   //获取Controller的实例对象
        //Controller中写的初始化方法
        controller.Init();
        primaryStage.show();



    }

    public static void main(String[] args) {
        launch(args);
    }
}
