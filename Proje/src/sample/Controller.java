package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import javafx.scene.image.Image;
import java.io.IOException;
import java.sql.*;
import javafx.scene.image.ImageView;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;

public class Controller {
    Main mainobject= new Main();

    String url = "jdbc:sqlite:proje_database.db";
    String user= mainobject.user;
    String password= mainobject.password;
    @FXML
    private ChoiceBox<?> choicebox1;
    @FXML
    private ChoiceBox<?> choicebox2;
    @FXML
    private ChoiceBox<?> choicebox3;
    @FXML
    private Button oturumtext;
    @FXML
    private TextField Emailtext;
    @FXML
    private TextField Surnametext;
    @FXML
    private TextField Nametext;
    @FXML
    private PasswordField passwordtext;
    @FXML
    private PasswordField password2text;
    @FXML
    private Button Kayıttext;
    @FXML
    private Button signupbtn;
    @FXML
    private Text errortext;
    @FXML
    private Text loginerrortext;
    @FXML
    private PasswordField logpasswordtext;
    @FXML
    private TextField logemailtext;
    @FXML
    private DatePicker date;
    @FXML
    private ListView<String> mostlikelist;
    @FXML
    private ImageView LikedImage;
    private static final String SELECT_QUERY = "SELECT * FROM usertablosu WHERE userMail = ? and userŞifre = ?";
    FXMLLoader Kayitekrani= new FXMLLoader();
    Stage stagesign = new Stage();
    @FXML
    void Kaydol(ActionEvent actionEvent) throws IOException {
        Kayitekrani.setLocation(getClass().getResource("Kayitekrani.fxml"));
        Scene scenesign = new Scene(Kayitekrani.load(), 700, 500);
        stagesign.setTitle("NETFLİX-Kayıt ekranı");
        stagesign.setResizable(false);
        stagesign.setScene(scenesign);
        stagesign.show();
    }
    private static final String SELECT_QUERYtür1 = "select programtablosu.programAdı, avg(Puan) as 'avgPuan' from kullanıcıprogramtablosu inner join programtürtablosu\n" +
            "  on kullanıcıprogramtablosu.programID=programtürtablosu.programID inner join türtablosu\n" +
            "  on programtürtablosu.türID=türtablosu.türID  inner join programtablosu\n" +
            "  on kullanıcıprogramtablosu.programID=programtablosu.programID where türtablosu.türAdı=? and Puan IS NOT NULL \n" +
            "  group by programAdı order by avgPuan desc;";
    @FXML
    void signup(ActionEvent event) {
        if(passwordtext.getText().compareTo(password2text.getText())==0) {
            if (choicebox1.getValue().toString().compareTo(choicebox2.getValue().toString())!=0 &&choicebox1.getValue().toString().compareTo(choicebox3.getValue().toString())!=0 &&choicebox2.getValue().toString().compareTo(choicebox3.getValue().toString())!=0) {//|| date.getValue().toString().compareTo("1")==0
                if (Nametext.getText().isEmpty() || Surnametext.getText().isEmpty() || Emailtext.getText().isEmpty() || passwordtext.getText().isEmpty() || password2text.getText().isEmpty() || choicebox1.getValue().toString().compareTo("1.'yi Seçiniz")==0 || choicebox2.getValue().toString().compareTo("2.'yi Seçiniz")==0 || choicebox3.getValue().toString().compareTo("3.'yü Seçiniz")==0 ) {
                    errortext.setText("Lütfen bütün boşlukları doldurunuz");
                } else {
                    try {
                        Connection conn = DriverManager.getConnection(url);
                        String query = " insert into usertablosu (userAd, userSoyad, userMail, userŞifre, UserDoğum)"
                                + " values (?, ?, ?, ?, ?)";
                        PreparedStatement preparedStmt = conn.prepareStatement(query);
                        preparedStmt.setString(1, Nametext.getText());
                        preparedStmt.setString(2, Surnametext.getText());
                        preparedStmt.setString(3, Emailtext.getText());
                        preparedStmt.setString(4, passwordtext.getText());
                        preparedStmt.setString(5, date.getValue().toString());
                        preparedStmt.execute();
                        preparedStmt.close();
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    errortext.setText("Başarıyla Hesap oluşturuldu");
                    signupbtn.setVisible(false);
                }
            } else {
               errortext.setText("Sevdiğiniz Türleri birbirinden Farklı seçiniz!!");
            }
            mostlikelist.setVisible(true);
            LikedImage.setVisible(true);



            mostlikelist.getItems().add(choicebox1.getValue().toString()+":");
            try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYtür1);
                preparedStatement.setString(1, choicebox1.getValue().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                int i=0;
                while (resultSet.next()) {
                    if(i<2)
                   mostlikelist.getItems().add(resultSet.getString("programAdı"));
                   i++;
                }
                conn.close();
            } catch (SQLException e) {
            }
            mostlikelist.getItems().add("");

            mostlikelist.getItems().add(choicebox2.getValue().toString()+":");

            try {

                Connection conn = DriverManager.getConnection(url);
                PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYtür1);
                preparedStatement.setString(1, choicebox2.getValue().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                int i=0;
                while (resultSet.next()) {
                    if(i<2)
                        mostlikelist.getItems().add(resultSet.getString("programAdı"));
                    i++;
                }
                conn.close();
            } catch (SQLException e) {
            }
            mostlikelist.getItems().add("");


            mostlikelist.getItems().add(choicebox3.getValue().toString()+":");
            try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERYtür1);
                preparedStatement.setString(1, choicebox3.getValue().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                int i=0;
                while (resultSet.next()) {
                    if(i<2)
                        mostlikelist.getItems().add(resultSet.getString("programAdı"));
                    i++;
                }
                conn.close();
            } catch (SQLException e) {
            }



        }
        else
        {
            errortext.setText("Şifreler Eşit Değil!");
        }
        }
    public static String nickname = "yghfgdhgfhkfghjfgkhkfghfkghkjfghkjfghjkf";
   public static String Passwordnow="hfgıdfghdkjfghdfghdfkjghjkdfghkdhgkdfhgjfhgjfhgkjdfhgkjdfg";
    @FXML
    void oturumac(ActionEvent actionEvent) throws IOException {

        if(logemailtext.getText().isEmpty()||logpasswordtext.getText().isEmpty()){
            loginerrortext.setText("Lütfen bütün bilgilerinizi doldurunuz!");
        }
        else{
            try{
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
                    preparedStatement.setString(1, logemailtext.getText());
                    preparedStatement.setString(2, logpasswordtext.getText());
                    ResultSet resultSet = preparedStatement.executeQuery();
                loginerrortext.setText("");
                    while (resultSet.next()) {
                        Passwordnow= resultSet.getString("userŞifre");
                        nickname=resultSet.getString("userMail");
                }
                conn.close();
            } catch (SQLException e) {
            }
            if(nickname.compareTo(logemailtext.getText())==0 && Passwordnow.compareTo(logpasswordtext.getText())==0)
            {
                 FXMLLoader UygulamaEkrani = new FXMLLoader();
                 stagesign.close();
                UygulamaEkrani.setLocation(getClass().getResource("UygulamaEkrani.fxml"));
                Scene scene = new Scene(UygulamaEkrani.load(), 1910, 990);
                Stage stage = new Stage();
                stage.setTitle("NETFLİX-Uygulama ekranı");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();

                mainobject.primaryStage.close();

                loginerrortext.setText("Başarıyla giriş yaptınız");
            }
            else{
                loginerrortext.setText("Email adresiniz veya şifreniz hatalı!");
            }
        }
    }
}
