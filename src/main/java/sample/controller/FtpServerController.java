package sample.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import sample.utils.PropertiesUtils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class FtpServerController {
    private static FtpServer server;
    private static FtpServerFactory serverFactory;
    private static ListenerFactory listenerFactory;
    private static BaseUser user;

    public static void start() {
        serverFactory = new FtpServerFactory();
        listenerFactory = new ListenerFactory();
        listenerFactory.setPort(Integer.parseInt(PropertiesUtils.getProperties().getProperty("server.port")));
        serverFactory.addListener("default", listenerFactory.createListener());

        user = new BaseUser();
        user.setName(PropertiesUtils.getProperties().getProperty("user.account"));
        user.setPassword(PropertiesUtils.getProperties().getProperty("user.password"));
        user.setHomeDirectory(PropertiesUtils.getProperties().getProperty("server.dir"));
        List<Authority> authorities = new ArrayList<Authority>();
        //增加写权限
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);

        try {
            serverFactory.getUserManager().save(user);
            server = serverFactory.createServer();
            server.start();
            PropertiesUtils.setProperties("server.switch","true");
            System.out.println("启动FTP服务器！");

//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("信息");
//            alert.setHeaderText(null);
//            alert.setContentText("成功启动FTP服务器！");
//            alert.showAndWait();
        } catch (FtpException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception Dialog");
            alert.setHeaderText("Look, an Exception Dialog");
            alert.setContentText("Could not start FTP Server!");
            Exception ex = new FileNotFoundException("Could not start FTP Server");
// Create expandable Exception.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label("The exception stacktrace was:");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);

            alert.showAndWait();
            e.printStackTrace();
        }
    }


    public static void stop() {
       try{
           server.stop();
           System.out.println("关闭FTP服务器！");
       } catch (Exception e) {
           e.printStackTrace();
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("错误");
           alert.setHeaderText(null);
           alert.setContentText(e.toString());
           alert.showAndWait();
       }
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("信息");
//        alert.setHeaderText(null);
//        alert.setContentText("关闭FTP服务器！");
//        alert.showAndWait();
    }
}
