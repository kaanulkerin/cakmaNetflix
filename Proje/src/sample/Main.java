package sample;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Scanner;

public class Main extends Application {
    public static String user;
    public static  String password;
    public static  Stage primaryStage = new Stage();
    @Override
    public void start(Stage primaryStag) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Girisekrani.fxml"));
        primaryStage.setTitle("NETFLİX-Giriş Ekranı");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        String url = "jdbc:sqlite:proje_database.db";
        try{
            Connection myConn = DriverManager.getConnection(url);
            Statement mystat = myConn.createStatement();
            ResultSet myRs = mystat.executeQuery("select * from usertablosu");
            while(myRs.next())
            { }
            myConn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
}
